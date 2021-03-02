package com.example.demo;

import com.example.demo.controllers.CartController;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
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
@Transactional
public class CartControllerTests {

    private static CartController cartController;
    private static final ItemRepository itemRepository = mock(ItemRepository.class);
    private static final CartRepository cartRepository = mock(CartRepository.class);
    private static final UserRepository userRepository = mock(UserRepository.class);

    @BeforeAll
    static void beforeAll() {
        cartController = new CartController(userRepository, cartRepository, itemRepository);
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

        when(userRepository.findByUsername("user")).thenReturn(user);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(createItem(1L)));
        when(cartRepository.findByUser(any())).thenReturn(cart);
    }

    @Test
    public void addToCart() {
        ModifyCartRequest request = createModifyCartRequest();
        request.setQuantity(3);

        ResponseEntity<Cart> response = cartController.addTocart(request);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

        Cart cart = response.getBody();
        assertNotNull(cart);
        BigDecimal expectedTotal = new BigDecimal((numberOfItems + request.getQuantity()) * pricePerItem);
        assertEquals(expectedTotal, cart.getTotal());
    }

    @Test
    public void removeFromCart() {
        ModifyCartRequest request = createModifyCartRequest();
        request.setQuantity(3);

        ResponseEntity<Cart> response = cartController.removeFromcart(request);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

        Cart cart = response.getBody();
        assertNotNull(cart);
        BigDecimal expectedTotal = new BigDecimal((numberOfItems - request.getQuantity()) * pricePerItem);
        assertEquals(expectedTotal, cart.getTotal());
    }
}
