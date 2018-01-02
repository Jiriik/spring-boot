package cz.vlasimsky.springboot.todoMicroservice.services;

import cz.vlasimsky.springboot.todoMicroservice.entities.Todo;

import java.util.List;

public interface TodoService {
    List<Todo> getTodos(String email);

    Todo addTodo(Todo todo);

    void deleteTodo(Integer id);
}
