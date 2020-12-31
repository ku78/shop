package com.geekbrains.shop.dao;

import com.geekbrains.shop.entities.Role;
import com.geekbrains.shop.entities.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@SqlGroup({@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:initUsers.sql")})
public class UserRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    void checkFindOneById() {
        User user = new User();
        user.setName("TestUser");
        user.setPassword("password");
        user.setEmail("tkumit@mail.ru");
        entityManager.persist(user);
        User actualUser = userRepository.findOneById(7L);
        Assertions.assertNotNull(actualUser);
        Assertions.assertEquals(user.getName(), actualUser.getName());
        Assertions.assertEquals(user.getPassword(), actualUser.getPassword());
        Assertions.assertEquals(user.getEmail(), actualUser.getEmail());
    }

    @Test
    void checkFindOneByIdAfterSql() {
        User actualUser = userRepository.findOneById(8L);
        Assertions.assertNotNull(actualUser);
        Assertions.assertEquals(8L, actualUser.getId());
        Assertions.assertEquals("kuzya", actualUser.getName());
        Assertions.assertEquals("password", actualUser.getPassword());
        Assertions.assertEquals("kuzya@mail.ru", actualUser.getEmail());
        Assertions.assertEquals(Role.ADMIN, actualUser.getRole());
    }

}