package com.elo.webshopbackend.repository;

import com.elo.webshopbackend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository  extends JpaRepository <Order, Long>{
}
