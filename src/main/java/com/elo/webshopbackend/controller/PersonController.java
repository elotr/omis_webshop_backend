package com.elo.webshopbackend.controller;

import com.elo.webshopbackend.annotations.EntitiesAnnotation;
import com.elo.webshopbackend.exception.UserAlreadyExistsException;
import com.elo.webshopbackend.model.Person;
import com.elo.webshopbackend.repository.PersonRepository;
import com.elo.webshopbackend.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@RestController

//@CrossOrigin (origins = "http://localhost:4200")
public class PersonController {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    PersonService personService;

    @GetMapping ("all-people")
    public List<Person> getAllPeople() throws ExecutionException {
        //return personRepository.findAll();
        return personService.getAllPeople();
    }

    @PostMapping ("add-person")
    public void addNewPerson(@RequestBody Person person) throws UserAlreadyExistsException {
        if (personRepository.findById(person.getPersonCode()).isPresent()) {
            throw new UserAlreadyExistsException();
        }
        //personRepository.save(person);
        personService.updatePerson(person);
    }

    @PostMapping ("edit-person")
    public void editPerson(@RequestBody Person person) {
        //personRepository.save(person);
        personService.updatePerson(person);
    }

    @GetMapping ("person/{personCode}")
    public Person getPerson(@PathVariable String personCode) throws ExecutionException {
        //return personRepository.findById(personCode).get();
        return personService.getPerson(personCode);
    }

//    @GetMapping ("person/{personCode}")
//    public Optional<Person> getPerson(@PathVariable String personCode) {
//        return personRepository.findById(personCode);
//    }

    @DeleteMapping ("delete-person/{personCode}")
    public void deletePerson(@PathVariable String personCode) {
        //personRepository.deleteById(personCode);
        personService.deletePerson(personCode);
    }

}
