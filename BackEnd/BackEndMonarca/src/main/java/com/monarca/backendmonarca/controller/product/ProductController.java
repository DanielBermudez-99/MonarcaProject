package com.monarca.backendmonarca.controller.product;

import com.monarca.backendmonarca.domain.category.Category;
import com.monarca.backendmonarca.domain.product.DataRegisterProduct;
import com.monarca.backendmonarca.domain.category.CategoryRepository;
import com.monarca.backendmonarca.domain.product.DataUpdateProduct;
import com.monarca.backendmonarca.domain.product.Product;
import com.monarca.backendmonarca.domain.product.ProductRepository;
import com.monarca.backendmonarca.infra.services.AzureStorageService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/product")
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductRepository productRepository;


    @Autowired
    @Lazy
    private CategoryRepository categoryRepository;

    @Autowired
    private AzureStorageService azureStorageService;


    @PostMapping("/register")
    public ResponseEntity<Product> crearProducto(@RequestBody DataRegisterProduct dataRegisterProduct) {
        Product newProduct = productRepository.save(new Product(dataRegisterProduct));
        return ResponseEntity.ok(newProduct);
    }

    @GetMapping("/list")
    public ResponseEntity<?> obtenerProductos(){
        return ResponseEntity.ok(productRepository.findAll());
    }

    @PutMapping("/update/{id}")
    @Transactional
    public ResponseEntity<?> actualizarProducto(@PathVariable Long id, @RequestBody DataUpdateProduct dataUpdateProduct){
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        product.dataUpdate(dataUpdateProduct);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable Long id){
        productRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/register/{productId}/categories/{categoryId}")
    public ResponseEntity<?> addCategoryToProduct(@PathVariable Long productId, @PathVariable Long categoryId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        product.getCategories().add(category);
        productRepository.save(product);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{productId}/categories/{categoryId}")
    public ResponseEntity<?> removeCategoryFromProduct(@PathVariable Long productId, @PathVariable Long categoryId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        product.getCategories().remove(category);
        productRepository.save(product);

        return ResponseEntity.ok().build();
    }


    @PostMapping("/uploadImage/{productId}")
    public ResponseEntity<String> uploadImage(@PathVariable Long productId, @RequestParam("file") MultipartFile file) throws IOException {
        // Subir la imagen a Azure y obtener la URL
        String url = azureStorageService.upload(file);

        // Buscar el producto en la base de datos
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Actualizar el campo image_url con la URL de la imagen
        product.setImage_url(url);

        // Guardar el producto actualizado en la base de datos
        productRepository.save(product);

        return ResponseEntity.ok(url);
    }

    @GetMapping("/list/category/{categoryId}")
    public ResponseEntity<?> obtenerProductosPorCategoria(@PathVariable Long categoryId){
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return ResponseEntity.ok(category.getProducts());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> obtenerProducto(@PathVariable Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Devuelve solo la información necesaria para el carrito
        Map<String, Object> productInfo = new HashMap<>();
        productInfo.put("name", product.getName());
        productInfo.put("size", product.getSize());
        productInfo.put("description", product.getDescription());
        productInfo.put("price", product.getPrice());

        return ResponseEntity.ok(productInfo);
    }

}