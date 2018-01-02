package cz.vlasimsky.springboot.todoMicroservice.utilities.Exceptions;

public class UserNotFoundException extends Exception {

    public UserNotFoundException(String exception) {
        super(exception);
    }
}
