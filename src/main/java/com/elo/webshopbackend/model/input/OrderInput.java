package com.elo.webshopbackend.model.input;

import com.elo.webshopbackend.model.Item;
import lombok.Data;

import java.util.List;

@Data
public class OrderInput {

    private String personCode;
    private List<Item> items;

}
