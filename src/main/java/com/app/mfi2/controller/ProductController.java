package com.app.mfi2.controller;

import com.app.mfi2.dto.ProductDto;
import com.app.mfi2.model.Product;
import com.app.mfi2.service.ProductService;
import com.app.mfi2.utils.MapperDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Produits")
@CrossOrigin(value = "*")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> productDtos = productService.findAll().stream().map(pr -> MapperDTO.convertToDto(pr,
                ProductDto.class)).toList();
        return ResponseEntity.ok(productDtos);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        Product product = productService.findById(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(MapperDTO.convertToDto(product, ProductDto.class));
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        Product pr = productService.save(productDto);
        if (pr == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(MapperDTO.convertToDto(pr, ProductDto.class));
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        Product pr = productService.update(id, productDto);
        if (pr == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(MapperDTO.convertToDto(pr, ProductDto.class));
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<ProductDto> deleteProduct(@PathVariable Long id) {
        boolean success = productService.delete(id);
        if (success) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
