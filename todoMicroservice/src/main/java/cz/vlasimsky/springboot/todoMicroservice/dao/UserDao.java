package cz.vlasimsky.springboot.todoMicroservice.dao;

import cz.vlasimsky.springboot.todoMicroservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDao extends JpaRepository<User, String> {
    Optional<User> findUsersByEmail(String email);

//    @Query(value = "SELECT * FROM users WHERE email =:email ", nativeQuery = true);

//    User findOne(String email);


}
