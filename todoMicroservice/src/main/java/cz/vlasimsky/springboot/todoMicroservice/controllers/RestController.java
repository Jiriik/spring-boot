package cz.vlasimsky.springboot.todoMicroservice.controllers;

import cz.vlasimsky.springboot.todoMicroservice.entities.Todo;
import cz.vlasimsky.springboot.todoMicroservice.entities.User;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
