package cz.vlasimsky.springboot.todoMicroservice.dao;

import cz.vlasimsky.springboot.todoMicroservice.entities.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoDao extends JpaRepository<Todo, Integer> {
    // name strategy
    List<Todo> findByFkUser(String fkUser);
}
