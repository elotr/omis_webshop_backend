package com.elo.webshopbackend.cache;

import com.elo.webshopbackend.exception.ItemNotFoundException;
import com.elo.webshopbackend.model.Item;
import com.elo.webshopbackend.repository.ItemRepository;
import com.elo.webshopbackend.service.ItemService;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.checkerframework.checker.compilermsgs.qual.CompilerMessageKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


@Component
public class ItemCache {

    @Autowired
    ItemService itemService;

    private final LoadingCache<Long, Item> itemCache = CacheBuilder.newBuilder()
            .maximumSize(1000) // 1000 eset caches
            .expireAfterAccess(30, TimeUnit.MINUTES)
            .build(new CacheLoader<Long, Item>() {
                @Override
                public Item load(Long itemId) throws ItemNotFoundException {
                    return itemService.getItemFromDatabse(itemId);
                }
            });

    public Item getItemFromCache(Long id) throws ExecutionException {
        updateCacheIfEmpty();
        return this.itemCache.get(id);
    }

    public List<Item> getAllItemsFromCache() throws ExecutionException {
        updateCacheIfEmpty();
        return new ArrayList<>(this.itemCache.asMap().values());
    }

    public void updateCache(Item item) {
        itemCache.put(item.getId(), item);
    }

    public void deleteFromCache(Long id) {
        itemCache.invalidate(id);
    }

    private void updateCacheIfEmpty() {
        if (itemCache.asMap().values().isEmpty()){
            itemService.getAllItemsFromDatabase().forEach(item -> itemCache.put(item.getId(), item));
        }
    }
}
