package com.elo.webshopbackend.cache;

import com.elo.webshopbackend.exception.ItemNotFoundException;
import com.elo.webshopbackend.model.Person;
import com.elo.webshopbackend.service.PersonService;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Component
public class PersonCache {

    @Autowired
    PersonService personService;

    private final LoadingCache<String, Person> personCache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterAccess(30, TimeUnit.MINUTES)
            .build(new CacheLoader<String, Person>() {
                @Override
                public Person load(String personCode) throws ItemNotFoundException {  // Veateade tee uus, vt seda klassi
                    return personService.getPersonFromDatabase(personCode);
                }
            });

    public Person getPersonFromCache(String personCode) throws ExecutionException {
        updateCacheIfEmpty();
        return this.personCache.get(personCode);
    }

    public List<Person> getAllPeopleFromCache() throws ExecutionException {
        updateCacheIfEmpty();
        return new ArrayList<>(this.personCache.asMap().values());
    }

    public void updateCache(Person person) {
        personCache.put(person.getPersonCode(), person);
    }

    public void deleteFromCache(String personCode) {
        personCache.invalidate(personCode);
    }

    private void updateCacheIfEmpty() {
        if(personCache.asMap().values().isEmpty()) {
            personService.getAllPeopleFromDatabase().forEach(person -> personCache.put(person.getPersonCode(), person));
        }
    }

}
