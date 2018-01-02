package cz.vlasimsky.springboot.todoMicroservice.services;

import cz.vlasimsky.springboot.todoMicroservice.entities.User;
import cz.vlasimsky.springboot.todoMicroservice.utilities.Exceptions.UserNotFoundException;
import cz.vlasimsky.springboot.todoMicroservice.utilities.Exceptions.UserNotLoggedInException;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

public interface LoginService {
    Optional<User> getUserFromDb(String email, String pwd) throws UserNotFoundException;

    String createJwt(String email, String name, Date date) throws UnsupportedEncodingException;

    Map<String, Object> verifyJwtAndGetData(HttpServletRequest request) throws UnsupportedEncodingException, UserNotLoggedInException;
}
