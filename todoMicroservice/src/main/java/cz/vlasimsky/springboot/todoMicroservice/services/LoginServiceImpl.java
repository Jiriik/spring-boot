package cz.vlasimsky.springboot.todoMicroservice.services;

import cz.vlasimsky.springboot.todoMicroservice.dao.UserDao;
import cz.vlasimsky.springboot.todoMicroservice.entities.User;
import cz.vlasimsky.springboot.todoMicroservice.utilities.EncryptionUtils;
import cz.vlasimsky.springboot.todoMicroservice.utilities.Exceptions.UserNotFoundException;
import cz.vlasimsky.springboot.todoMicroservice.utilities.Exceptions.UserNotLoggedInException;
import cz.vlasimsky.springboot.todoMicroservice.utilities.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    UserDao userDao;

    @Autowired
    EncryptionUtils encryptionUtils;

    @Autowired
    JwtUtils jwtUtils;

    @Override
    public Optional<User> getUserFromDb(String email, String pwd) throws UserNotFoundException {
        Optional<User> user = userDao.findUsersByEmail(email);
        if (user.isPresent()) {
            User user1 = user.get();
            if (!encryptionUtils.decrypt(user1.getPassword()).equals(pwd)) {
                throw new UserNotFoundException("Invalid username/password");
            }
        }
        return user;
    }

    @Override
    public String createJwt(String email, String name, Date date) throws UnsupportedEncodingException {
        date.setTime(date.getTime() + (3000 * 10000));
        return jwtUtils.generateJwt(email, name, date);
    }

    @Override
    public Map<String, Object> verifyJwtAndGetData(HttpServletRequest request) throws UnsupportedEncodingException, UserNotLoggedInException {
        String jwt = jwtUtils.getJwtFromHttpRequest(request);
        if (jwt == null) {
            throw  new UserNotLoggedInException("Not logged in. Login first");
        }
        return jwtUtils.jwt2Map(jwt);
    }
}
