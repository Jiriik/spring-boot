package cz.vlasimsky.springboot.todoMicroservice.utilities;

import cz.vlasimsky.springboot.todoMicroservice.entities.Todo;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ToDoValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Todo.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Todo todo = (Todo) o;
        String priority = todo.getPriority();
        if (!"high".equals(priority) && !"low".equals(priority)) {
            errors.rejectValue("priority", "Priority must be 'high' or 'low'");
        }

    }
}
