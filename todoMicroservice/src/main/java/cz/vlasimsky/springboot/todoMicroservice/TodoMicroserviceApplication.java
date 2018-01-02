package cz.vlasimsky.springboot.todoMicroservice;

import cz.vlasimsky.springboot.todoMicroservice.dao.TodoDao;
import cz.vlasimsky.springboot.todoMicroservice.dao.UserDao;
import cz.vlasimsky.springboot.todoMicroservice.entities.Todo;
import cz.vlasimsky.springboot.todoMicroservice.entities.User;
import cz.vlasimsky.springboot.todoMicroservice.utilities.EncryptionUtils;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@Log
@SpringBootApplication
public class TodoMicroserviceApplication implements CommandLineRunner {

    @Autowired
    UserDao userDao;

    @Autowired
    TodoDao todoDao;

    @Autowired
    EncryptionUtils encryptionUtils;

//	private static final Logger LOGGER = LoggerFactory.getLogger(TodoMicroserviceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(TodoMicroserviceApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        // ...
        log.info("let's fill the H2 in memory database");

        String encryptedPwd = encryptionUtils.encrypt("hello");
        userDao.save(new User("testing@email.com", "Name Surname", encryptedPwd));
        encryptedPwd = encryptionUtils.encrypt("hello2");
        userDao.save(new User("testing2@email2.com", "Name2 Surname2", encryptedPwd));
        encryptedPwd = encryptionUtils.encrypt("hello3");
        userDao.save(new User("testing3@email3.com", "Name3 Surname3", encryptedPwd));

        todoDao.save(new Todo(1, "Learn microservices", new Date(), "high", "testing@email.com"));
        todoDao.save(new Todo(null, "Learn spring boot", null, "low", "testing@email.com"));

        todoDao.save(new Todo(3, "Feed animals", new Date(), "high", "testing2@email2.com"));
        todoDao.save(new Todo(null, "Water PLants", null, "low", "testing2@email2.com"));

        todoDao.save(new Todo(5, "Clean your", new Date(), "high", "testing3@email3.com"));
        todoDao.save(new Todo(null, "Forget everything", null, "low", "testing3@email3.com"));

        log.info("db loading finished");
    }
}
