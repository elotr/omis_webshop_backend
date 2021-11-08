package com.elo.webshopbackend.repository;

import com.elo.webshopbackend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository <Category, Long> {
}
