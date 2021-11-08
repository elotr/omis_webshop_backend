package com.elo.webshopbackend.controller;
import com.elo.webshopbackend.model.Category;
import com.elo.webshopbackend.model.CategoryType;
import com.elo.webshopbackend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@CrossOrigin(origins = "http://localhost:4200") // n'itab/m''rab kust saadakse controlleri p'ringutele ligi
public class CategoryController {

    @Autowired
    CategoryRepository categoryRepository;  //otseyhendus ItemRepositoriga, et tekitata uut m'lukohta.

    @GetMapping("category-type")
    public CategoryType[] getCategoryType() {
        return CategoryType.values();
    }

    @PostMapping("new-category")
    public void newCategory(@RequestBody Category category) {
        categoryRepository.save(category);
    }

    @DeleteMapping("delete-category/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryRepository.deleteById(id);
    }

    @GetMapping("categories")
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

}
