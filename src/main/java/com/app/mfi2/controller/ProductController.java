package com.app.mfi2.controller;

import com.app.mfi2.dto.ProductDto;
import com.app.mfi2.model.Product;
import com.app.mfi2.service.ProductService;
import com.app.mfi2.utils.MapperDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The type Product controller.
 */
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Produits")
@CrossOrigin(value = "*")
public class ProductController {
    private final ProductService productService;

    /**
     * Gets all products.
     *
     * @return the all products
     */
    @GetMapping
    @ResponseBody
    @Operation(summary = "Liste des produits")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> productDtos = productService.findAll().stream().map(pr -> MapperDTO.convertToDto(pr,
                ProductDto.class)).toList();
        return ResponseEntity.ok(productDtos);
    }

    /**
     * Gets product by id.
     *
     * @param id the id
     * @return the product by id
     */
    @GetMapping("/{id}")
    @ResponseBody
    @Operation(summary = "Trouver un produit avec son Id")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        Product product = productService.findById(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(MapperDTO.convertToDto(product, ProductDto.class));
    }

    /**
     * Create product response entity.
     *
     * @param productDto the product dto
     * @return the response entity
     */
    @PostMapping
    @ResponseBody
    @Operation(summary = "Créer un nouveau produit")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        Product pr = productService.save(productDto);
        if (pr == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(MapperDTO.convertToDto(pr, ProductDto.class));
    }

    /**
     * Update product response entity.
     *
     * @param id         the id
     * @param productDto the product dto
     * @return the response entity
     */
    @PutMapping("/{id}")
    @ResponseBody
    @Operation(summary = "Mise à jour d'un produit")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        Product pr = productService.update(id, productDto);
        if (pr == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(MapperDTO.convertToDto(pr, ProductDto.class));
    }

    /**
     * Delete product response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    @Operation(summary = "Supprimer un produit selon son Id")
    public ResponseEntity<ProductDto> deleteProduct(@PathVariable Long id) {
        boolean success = productService.delete(id);
        if (success) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
