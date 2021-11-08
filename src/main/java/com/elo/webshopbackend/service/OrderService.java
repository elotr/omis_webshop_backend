package com.elo.webshopbackend.service;

import com.elo.webshopbackend.exception.ItemNotFoundException;
import com.elo.webshopbackend.model.Item;
import com.elo.webshopbackend.model.Order;
import com.elo.webshopbackend.model.Person;
import com.elo.webshopbackend.model.input.OrderInput;
import com.elo.webshopbackend.repository.ItemRepository;
import com.elo.webshopbackend.repository.OrderRepository;
import com.elo.webshopbackend.repository.PersonRepository;
import com.elo.webshopbackend.util.OrderUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    ItemRepository itemRepository;

    public Long saveOrderToDatabase (List<Item> orderItems, String personCode) throws ItemNotFoundException {  //v6tab frontendist tellitud esemed ostukorvist...
        Order order = new Order();

        order.setItems(orderItems);
        BigDecimal totalSum = OrderUtil.getSumOfOrder(orderItems);
        order.setTotalSum(totalSum);
        Person person = personRepository.getById(personCode);
        order.setPerson(person);
        log.info("order created: " + order);
        order.setIsPaid(false);
        Order orderFromDb = orderRepository.save(order);
        return orderFromDb.getId();
    }
}