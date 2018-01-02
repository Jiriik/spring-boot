package cz.vlasimsky.springboot.todoMicroservice.controllers;

import cz.vlasimsky.springboot.todoMicroservice.entities.Todo;
import cz.vlasimsky.springboot.todoMicroservice.entities.User;
import cz.vlasimsky.springboot.todoMicroservice.services.LoginService;
import cz.vlasimsky.springboot.todoMicroservice.services.TodoService;
import cz.vlasimsky.springboot.todoMicroservice.utilities.Exceptions.UserNotFoundException;
import cz.vlasimsky.springboot.todoMicroservice.utilities.Exceptions.UserNotLoggedInException;
import cz.vlasimsky.springboot.todoMicroservice.utilities.JsonResponseBody;
import cz.vlasimsky.springboot.todoMicroservice.utilities.ToDoValidator;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    LoginService loginService;

    @Autowired
    TodoService todoService;

    @RequestMapping("/hello")
    public String sayHello() {
        return "helloeeee";
    }


    @RequestMapping("/userInOutput")
    public User loadUser() {
        return new User("user@mailbox.de", "User", "EncryptedPwd");
    }

    @RequestMapping("/todoInInput1")
    public String todoInInput1(Todo todo) {
        return "Todo description = " + todo.getDescription() + ", priority = " + todo.getPriority();
    }

    @RequestMapping("/todoInInput2")
    public String todoInInput2(@Valid Todo todo) {
        return "Todo description = " + todo.getDescription() + ", priority = " + todo.getPriority();
    }

    @RequestMapping("/todoInInput3")
    public String todoInInput3(@Valid Todo todo, BindingResult result) {
        if (result.hasErrors()) {
            return "my custom formatted error messages";
        }
        return "Todo description = " + todo.getDescription() + ", priority = " + todo.getPriority();
    }

    @RequestMapping("/todoInInput4")
    public String todoInInput4( Todo todo, BindingResult result) {
        ToDoValidator validator = new ToDoValidator();
        validator.validate(todo, result);
        if (result.hasErrors()) {
            return "my custom formatted error messages" + result.toString();
        }
        return "Todo description = " + todo.getDescription() + ", priority = " + todo.getPriority();
    }

    @RequestMapping("/todoInInput5")
    public String todoInInput5(@Valid Todo todo, BindingResult result) {
        ToDoValidator validator = new ToDoValidator();
        validator.validate(todo, result);
        if (result.hasErrors()) {
            return "my custom formatted error messages" + result.toString();
        }
        return "Todo description = " + todo.getDescription() + ", priority = " + todo.getPriority();
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<JsonResponseBody> login(@RequestParam(value = "email") String email, @RequestParam(value = "password") String password) {
        try {
            Optional<User> userOptional = loginService.getUserFromDb(email, password);
            User user = userOptional.get();
            String jwt = loginService.createJwt(email, user.getName(), new Date());
            return ResponseEntity.status(HttpStatus.OK).header("jwt", jwt)
                    .body(new JsonResponseBody(HttpStatus.OK.value(), "Success, user logged in"));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new JsonResponseBody(HttpStatus.FORBIDDEN.value(), "Forbidden" + e.toString()));
        } catch (UnsupportedEncodingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonResponseBody(HttpStatus.BAD_REQUEST.value(), "Bad request" + e.toString()));
        }
    }

    @RequestMapping(value = "/showTodos")
    public ResponseEntity<JsonResponseBody> showTodos(HttpServletRequest request) {
        try {
            Map<String, Object> userData = loginService.verifyJwtAndGetData(request);
            return ResponseEntity.status(HttpStatus.OK).body(new JsonResponseBody(HttpStatus.OK.value(), todoService.getTodos((String) userData.get("email"))));
        } catch (UnsupportedEncodingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonResponseBody(HttpStatus.BAD_REQUEST.value(), "Bad request" + e.toString()));
        } catch (UserNotLoggedInException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new JsonResponseBody(HttpStatus.FORBIDDEN.value(), "Forbidden, user not logged in" + e.toString()));
        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(new JsonResponseBody(HttpStatus.GATEWAY_TIMEOUT.value(), "Session Expired" + e.toString()));
        }
    }

    @RequestMapping(value = "/newTodo", method = RequestMethod.POST)
    public ResponseEntity<JsonResponseBody> newTodo(HttpServletRequest request, @Valid Todo todo, BindingResult result) {
        ToDoValidator validator = new ToDoValidator();
        validator.validate(todo, result);
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonResponseBody(HttpStatus.BAD_REQUEST.value(), "Data not valid" + result.toString()));
        }
        try {
            loginService.verifyJwtAndGetData(request);
            return ResponseEntity.status(HttpStatus.OK).body(new JsonResponseBody(HttpStatus.OK.value(), todoService.addTodo(todo)));
        } catch (UnsupportedEncodingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonResponseBody(HttpStatus.BAD_REQUEST.value(), "Bad request" + e.toString()));
        } catch (UserNotLoggedInException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new JsonResponseBody(HttpStatus.FORBIDDEN.value(), "Forbidden, user not logged in" + e.toString()));
        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(new JsonResponseBody(HttpStatus.GATEWAY_TIMEOUT.value(), "Session Expired" + e.toString()));
        }
    }

    @RequestMapping("/deleteTodo/{id}")
    public ResponseEntity<JsonResponseBody> deleteTodo(HttpServletRequest request, @PathVariable(name = "id") Integer todoId) {
        try {
            loginService.verifyJwtAndGetData(request);
            todoService.deleteTodo(todoId);
            return ResponseEntity.status(HttpStatus.OK).body(new JsonResponseBody(HttpStatus.OK.value(), "Todo deleted successfully"));
        } catch (UnsupportedEncodingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonResponseBody(HttpStatus.BAD_REQUEST.value(), "Bad request" + e.toString()));
        } catch (UserNotLoggedInException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new JsonResponseBody(HttpStatus.FORBIDDEN.value(), "Forbidden, user not logged in" + e.toString()));
        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(new JsonResponseBody(HttpStatus.GATEWAY_TIMEOUT.value(), "Session Expired" + e.toString()));
        }

    }
}
