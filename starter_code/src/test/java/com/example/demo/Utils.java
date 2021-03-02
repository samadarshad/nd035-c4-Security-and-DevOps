package com.example.demo;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.requests.ModifyCartRequest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static final int numberOfItems = 5;
    public static final int pricePerItem = 1;

    public static User createUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("user");
        user.setPassword("pass");
        return user;
    }

    public static Cart createCart() {
        Cart cart = new Cart();
        cart.setId(1L);
        return cart;
    }

    public static Item createItem(Long id) {
        Item item = new Item();
        item.setId(id);
        item.setDescription("desc" + id.toString());
        item.setName("name" + id.toString());
        item.setPrice(new BigDecimal(pricePerItem));
        return item;
    }

    public static List<Item> createItemList() {
        List<Item> items = new ArrayList<>();
        for (long i = 1L; i <= numberOfItems; i++) {
            items.add(createItem(i));
        }
        return items;
    }

    public static ModifyCartRequest createModifyCartRequest() {
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setUsername("user");
        modifyCartRequest.setItemId(1L);
        modifyCartRequest.setQuantity(2);
        return modifyCartRequest;
    }
}
