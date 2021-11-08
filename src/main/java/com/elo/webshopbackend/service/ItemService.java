package com.elo.webshopbackend.service;

import com.elo.webshopbackend.cache.ItemCache;
import com.elo.webshopbackend.exception.ItemNotFoundException;
import com.elo.webshopbackend.model.Item;
import com.elo.webshopbackend.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;


// vahet pole kumb, suures osas sama praeguseks projektiks.
//@Component
@Service
public class ItemService {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemCache itemCache;

    public List<Item> getAllItems() throws ExecutionException {
        return itemCache.getAllItemsFromCache();
    }

    public Item getItem(Long id) throws ExecutionException {
        return itemCache.getItemFromCache(id);
    }

    public Item getItemFromDatabse(Long id) throws ItemNotFoundException {
        //  cache v]tab andembaasist, kui on
        if (itemRepository.findById(id).isPresent()) {
            return itemRepository.findById(id).get();
        }
        throw  new ItemNotFoundException();
    }

    public List<Item> getAllItemsFromDatabase() {
        return itemRepository.findAll();
    }

    public void updateItem(Item item) {
        itemRepository.save(item);
        itemCache.updateCache(item);
    }

    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
        itemCache.deleteFromCache(id);
    }

}
