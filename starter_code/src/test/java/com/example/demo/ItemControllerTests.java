package com.example.demo;

import com.example.demo.controllers.CartController;
import com.example.demo.controllers.ItemController;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.mock;

@SpringBootTest
public class ItemControllerTests {

    private static ItemController itemController;
    private static final ItemRepository itemRepository = mock(ItemRepository.class);

    @BeforeAll
    static void beforeAll() {
        itemController = new ItemController(itemRepository);
    }
}
