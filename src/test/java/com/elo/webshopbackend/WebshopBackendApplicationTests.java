package com.elo.webshopbackend;

import com.elo.webshopbackend.model.Item;
import com.elo.webshopbackend.service.ItemService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

@SpringBootTest
class WebshopBackendApplicationTests {

    @Autowired
    ItemService itemService;

    @Test
    void contextLoads() {

    }

    @Test
    void assertThatAddedItemExistsInRepository_ifAdded() throws ExecutionException {

        itemService.updateItem(new Item(1L, "Kell1111", BigDecimal.TEN, "www.google.com", "k2ekell", true));
        Item item = itemService.getItem(1L);
        Assertions.assertEquals("Kell", item.getTitle());

    }

    @Test
    void assertThatAddedItemIsNoInRepository_ifDeleted() throws ExecutionException {
        EmptyResultDataAccessException ex = Assertions.assertThrows(EmptyResultDataAccessException.class,
        this::deleteItemFromService,
        "Expected deleteItemsFromService to throw, but it didnt"
        );
        Assertions.assertEquals("No class com.elo.webshopbackend.model.Item entity with id 27 exists!", ex.getMessage());

    }

    private void deleteItemFromService() throws ExecutionException{
        itemService.updateItem(new Item(27L, "Kell1111", BigDecimal.TEN, "www.google.com", "k2ekell", true));
        itemService.deleteItem(27L);
    }
}
