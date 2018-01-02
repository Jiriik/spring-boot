package cz.vlasimsky.springboot.todoMicroservice.controllers;

import cz.vlasimsky.springboot.todoMicroservice.entities.Todo;
import cz.vlasimsky.springboot.todoMicroservice.entities.User;
import cz.vlasimsky.springboot.todoMicroservice.utilities.ToDoValidator;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@org.springframework.web.bind.annotation.RestController
public class RestController {

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


}
