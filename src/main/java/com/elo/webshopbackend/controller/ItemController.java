package com.elo.webshopbackend.controller;

import com.elo.webshopbackend.annotations.EntitiesAnnotation;
import com.elo.webshopbackend.exception.ItemNotFoundException;
import com.elo.webshopbackend.model.Item;
import com.elo.webshopbackend.repository.ItemRepository;
import com.elo.webshopbackend.service.ItemService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
// controller suhtleb repositoriga

@RestController
@EntitiesAnnotation

//@CrossOrigin(origins = "http://localhost:4200") // n'itab/m''rab kust saadakse controlleri p'ringutele ligi
public class ItemController {


    @Autowired //otseyhendus ItemRepositoriga, et tekitata uut m'lukohta.
    ItemRepository itemRepository;

    @Autowired
    ItemService itemService;

    // get - v]tab
    // delete

    // n]utakse p'ringule sisu ehk Body
    // post - lisab uut
    // put - asendab, kui asendan k]ik varasemasd uuega
    // patch - muutmiseks

    //localhost:8080 on siin juba ees -> localhost:8080/items
    @ApiOperation(value="Gives all items from webshop.")
    @GetMapping("items")
    public List<Item> getItems() throws ExecutionException {
        //return itemRepository.findAll();
        return itemService.getAllItems();
    }

    @DeleteMapping("delete-item/{id}")
    // saab url parameetri k'tte, mida yritab teha selliseks tyybiks nagu meil m''ratud
    public void deleteItem(@PathVariable Long id) { //

        //itemRepository.deleteById(id);
        itemService.deleteItem(id);
    }

    @PostMapping("add-item")
    //n]uab p'ringuga ka mingisugust sisu, (body), mida v]imaluse korral teha Item kujule
    public void addItem(@RequestBody Item item) {
        //itemRepository.save(item); //EDIT, annad id ka kaasa
        itemService.updateItem(item);
    }

    @ApiOperation("Gives an item from webshop. Need ID")
    @GetMapping("view-item/{id}")
    public Item viewItem(@PathVariable Long id) throws ExecutionException {
        //return itemService.getItem(id);
        return itemService.getItem(id);
    }


    @ApiOperation(value="Edits item from webshop.Need ID inside body.")
    @PostMapping("edit-item")
    public void editItem(@RequestBody Item item) {
        //itemRepository.save(item);
        itemService.updateItem(item);
    }




}