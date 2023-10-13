package com.monarca.backendmonarca.controller.category;


import com.monarca.backendmonarca.domain.category.CategoryRepository;
import com.monarca.backendmonarca.domain.category.DataRegisterCategory;
import com.monarca.backendmonarca.domain.category.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@CrossOrigin
public class CategoryController {

    @Autowired
    private CategoryRepository CategoryRepository;

    @PostMapping
    public ResponseEntity<Category> crearCategoria(@RequestBody DataRegisterCategory dataRegisterCategory) {
        Category newCategory = CategoryRepository.save(new Category(dataRegisterCategory));
        return ResponseEntity.ok(newCategory);
    }

}
