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

    @PostMapping("/create")
    public ResponseEntity<Category> crearCategoria(@RequestBody DataRegisterCategory dataRegisterCategory) {
        Category newCategory = CategoryRepository.save(new Category(dataRegisterCategory));
        return ResponseEntity.ok(newCategory);
    }

    @GetMapping("/list")
    public ResponseEntity<Iterable<Category>> listarCategorias() {
        Iterable<Category> categories = CategoryRepository.findAll();
        return ResponseEntity.ok(categories);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Long id) {
        CategoryRepository.deleteById(id);
        return ResponseEntity.ok(null);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Category> actualizarCategoria(@PathVariable Long id, @RequestBody DataRegisterCategory dataRegisterCategory) {
        Category category = CategoryRepository.findById(id).orElseThrow();
        category.setName(dataRegisterCategory.name());
        Category categoryUpdate = CategoryRepository.save(category);
        return ResponseEntity.ok(categoryUpdate);
    }

}
