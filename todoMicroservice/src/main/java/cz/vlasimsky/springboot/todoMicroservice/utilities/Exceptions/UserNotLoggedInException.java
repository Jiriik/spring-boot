package cz.vlasimsky.springboot.todoMicroservice.utilities.Exceptions;

public class UserNotLoggedInException extends Exception {

    public UserNotLoggedInException(String message) {
        super(message);
    }

}
