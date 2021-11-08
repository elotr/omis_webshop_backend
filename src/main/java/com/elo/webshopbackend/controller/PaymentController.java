package com.elo.webshopbackend.controller;

import com.elo.webshopbackend.exception.ItemNotFoundException;
import com.elo.webshopbackend.model.Item;
import com.elo.webshopbackend.model.input.OrderInput;
import com.elo.webshopbackend.model.input.OrderSum;
import com.elo.webshopbackend.model.output.EveryPayLink;
import com.elo.webshopbackend.service.OrderService;
import com.elo.webshopbackend.service.PaymentService;
import com.elo.webshopbackend.util.OrderUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@Log4j2

@CrossOrigin(origins = "http://localhost:4200")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderUtil orderUtil;

    @PostMapping("payment")
    public ResponseEntity<EveryPayLink> makePayment(@RequestBody OrderInput order) throws ItemNotFoundException {

        List<Item> itemsFromDatabase = orderUtil.getDatabaseItems(order.getItems());
        Long orderId = orderService.saveOrderToDatabase(itemsFromDatabase, order.getPersonCode());
        EveryPayLink everypayLink = paymentService.makePayment(itemsFromDatabase, orderId);
        if(everypayLink == null) {
            log.info("Sending empty result to frontend"); // saadab error s6numi front-endi (vt cart.component)
            return ResponseEntity.badRequest().body(null); // -"-  front-end v6tab http staatused vastu
        }
        return ResponseEntity.ok(everypayLink);
    }
}