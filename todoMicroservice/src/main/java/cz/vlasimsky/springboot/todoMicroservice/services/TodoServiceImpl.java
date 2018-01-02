package cz.vlasimsky.springboot.todoMicroservice.services;

import cz.vlasimsky.springboot.todoMicroservice.dao.TodoDao;
import cz.vlasimsky.springboot.todoMicroservice.entities.Todo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TodoServiceImpl implements TodoService {

    @Autowired
    TodoDao todoDao;

    @Override
    public List<Todo> getTodos(String email) {
        return todoDao.findByFkUser(email);
    }

    @Override
    public Todo addTodo(Todo todo) {
        return todoDao.save(todo);
    }

    @Override
    public void deleteTodo(Integer id) {
        todoDao.delete(id);
    }
}
