package com.elo.webshopbackend.repository;

import com.elo.webshopbackend.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository <Person, String> {
}
