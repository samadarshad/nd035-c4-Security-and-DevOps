package com.example.demo;

import com.example.demo.controllers.ItemController;
import com.example.demo.controllers.OrderController;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.example.demo.Utils.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class OrderControllerTests {
    private static OrderController orderController;
    private static final OrderRepository orderRepository = mock(OrderRepository.class);
    private static final UserRepository userRepository = mock(UserRepository.class);

    @BeforeAll
    static void beforeAll() {
        orderController = new OrderController(userRepository, orderRepository);
    }

    @BeforeEach
    void beforeEach() {
        User user = createUser();
        Cart cart = createCart();
        for (long i = 1L; i <= numberOfItems; i++) {
            Item item = createItem(i);
            cart.addItem(item);
        }
        user.setCart(cart);
        cart.setUser(user);
        List<UserOrder> userOrders = createUserOrderList();

        when(userRepository.findByUsername("user")).thenReturn(user);
        when(orderRepository.findByUser(any())).thenReturn(userOrders);
    }

    @Test
    void submit() {
        ResponseEntity<UserOrder> response = orderController.submit("user");

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

        UserOrder order = response.getBody();
        assertNotNull(order);
        assertEquals("user", order.getUser().getUsername());
        assertEquals(total, order.getTotal());
    }

    @Test
    void getOrdersForUser() {
        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("user");

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

        List<UserOrder> orderList = response.getBody();
        assertNotNull(orderList);
        for (int i = 0; i < numberOfOrders; i++) {
            assertEquals("user", orderList.get(i).getUser().getUsername());
            assertEquals(total, orderList.get(i).getTotal());
        }

    }
}
