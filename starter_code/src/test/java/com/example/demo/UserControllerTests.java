package com.example.demo;

import com.example.demo.controllers.OrderController;
import com.example.demo.controllers.UserController;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static com.example.demo.Utils.*;
import static com.example.demo.Utils.total;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserControllerTests {
    private static UserController userController;
    private static final UserRepository userRepository = mock(UserRepository.class);
    private static final CartRepository cartRepository = mock(CartRepository.class);
    private static final BCryptPasswordEncoder bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class);

    @BeforeAll
    static void beforeAll() {
        userController = new UserController(userRepository, cartRepository, bCryptPasswordEncoder);
    }

    @BeforeEach
    void beforeEach() {
        User user = Utils.createUser();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.findByUsername("user")).thenReturn(user);
        when(userRepository.findByUsername("no_user")).thenReturn(null);
        when(bCryptPasswordEncoder.encode(any())).thenReturn("encoded");
    }

    @Test
    void createUser_positive() {
        CreateUserRequest request = createCreateUserRequest();
        request.setPassword("valid_password");
        request.setConfirmPassword("valid_password");
        ResponseEntity<User> response = userController.createUser(request);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

        User user = response.getBody();
        assertNotNull(user);
        assertEquals("user", user.getUsername());
        assertEquals("encoded", user.getPassword());
    }

    @Test
    void createUser_negative() {
        CreateUserRequest request = createCreateUserRequest();
        request.setPassword("a");
        request.setConfirmPassword("a"); //password too short
        ResponseEntity<User> response = userController.createUser(request);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));

        User user = response.getBody();
        assertNull(user);
    }

    @Test
    void findById() {
        ResponseEntity<User> response = userController.findById(1L);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

        User user = response.getBody();
        assertNotNull(user);
        assertEquals("user", user.getUsername());
        assertEquals("pass", user.getPassword());
    }

    @Test
    void findByUserName_positive() {
        ResponseEntity<User> response = userController.findByUserName("user");

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

        User user = response.getBody();
        assertNotNull(user);
        assertEquals("user", user.getUsername());
        assertEquals("pass", user.getPassword());
    }

    @Test
    void findByUserName_negative() {
        ResponseEntity<User> response = userController.findByUserName("no_user");

        assertThat(response.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));

        User user = response.getBody();
        assertNull(user);
    }

}
