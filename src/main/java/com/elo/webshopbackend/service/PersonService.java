package com.elo.webshopbackend.service;

import com.elo.webshopbackend.cache.PersonCache;
import com.elo.webshopbackend.exception.ItemNotFoundException;
import com.elo.webshopbackend.model.Person;
import com.elo.webshopbackend.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    PersonCache personCache;


    public List<Person> getAllPeople() throws ExecutionException {
        return personCache.getAllPeopleFromCache();
    }

    public Person getPerson(String personCode) throws ExecutionException {
        return personCache.getPersonFromCache(personCode);
    }

    public Person getPersonFromDatabase(String personCode) throws ItemNotFoundException {
        if(personRepository.findById(personCode).isPresent()) {
            return personRepository.findById(personCode).get();
        }
        throw new ItemNotFoundException(); // uus exception teha Personile vb, vaata yle hiljem
    }

    public List<Person> getAllPeopleFromDatabase() {
        return personRepository.findAll();
    }

    public void updatePerson(Person person) {
        personRepository.save(person);
        personCache.updateCache(person);
    }

    public void deletePerson(String personCode) {
        personRepository.deleteById(personCode);
        personCache.deleteFromCache(personCode);
    }
}
