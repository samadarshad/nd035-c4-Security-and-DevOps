package com.example.demo;

import com.example.demo.controllers.CartController;
import com.example.demo.controllers.ItemController;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.demo.Utils.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ItemControllerTests {

    private static ItemController itemController;
    private static final ItemRepository itemRepository = mock(ItemRepository.class);

    @BeforeAll
    static void beforeAll() {
        itemController = new ItemController(itemRepository);
    }

    @BeforeEach
    void beforeEach() {
        when(itemRepository.findById(1L)).thenReturn(Optional.of(createItem(1L)));
        when(itemRepository.findAll()).thenReturn(createItemList());
        when(itemRepository.findByName(any())).thenReturn(createItemList());
    }

    @Test
    void getItems() {
        ResponseEntity<List<Item>> response = itemController.getItems();

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

        List<Item> items = response.getBody();
        assertNotNull(items);
        List<Item> expectedItems = createItemList();
        for (int i = 0; i < numberOfItems; i++) {
            assertEquals(expectedItems.get(i).getId(), items.get(i).getId());
            assertEquals(expectedItems.get(i).getDescription(), items.get(i).getDescription());
            assertEquals(expectedItems.get(i).getName(), items.get(i).getName());
            assertEquals(expectedItems.get(i).getPrice(), items.get(i).getPrice());
        }
    }

}
