package com.monarca.backendmonarca.controller.product;

import com.monarca.backendmonarca.domain.product.DataRegisterProduct;
import com.monarca.backendmonarca.domain.product.DataUpdateProduct;
import com.monarca.backendmonarca.domain.product.Product;
import com.monarca.backendmonarca.domain.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @PostMapping
    public ResponseEntity<Product> crearProducto(@RequestBody DataRegisterProduct dataRegisterProduct) {
        Product newProduct = productRepository.save(new Product(dataRegisterProduct));
        return ResponseEntity.ok(newProduct);
    }

    @GetMapping
    public ResponseEntity<?> obtenerProductos(){
        return ResponseEntity.ok(productRepository.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarProducto(@PathVariable Long id, @RequestBody DataUpdateProduct dataUpdateProduct){
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        product.dataUpdate(dataUpdateProduct);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable Long id){
        productRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
