package com.elo.webshopbackend.repository;

import com.elo.webshopbackend.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository <Item, Long>{ // JpaR paneb andmed ajutiselt Spring vahem'llu

}
