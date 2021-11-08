package com.elo.webshopbackend.controller;

import com.elo.webshopbackend.model.input.OrderInput;
import com.elo.webshopbackend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("add-order")
    public void createOrder(@RequestBody OrderInput order) {

    }

}
