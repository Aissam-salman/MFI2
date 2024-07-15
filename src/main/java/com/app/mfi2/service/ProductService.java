package com.app.mfi2.service;

import com.app.mfi2.dto.ProductDto;
import com.app.mfi2.model.Product;
import com.app.mfi2.repository.ProductRepository;
import com.app.mfi2.user.model.Producer;
import com.app.mfi2.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;
    private final UserService userService;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product save(ProductDto productDto) {
        Product pr = Product.builder()
                .name(productDto.getName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .producerOwner((Producer) userService.getUserById(productDto.getProducerId()))
                .build();

        return productRepository.save(pr);
    }

    public Product update(Long productId, ProductDto productDto) {
        Product prod = findById(productId);
        if (prod == null) {
            return null;
        }
        if (productDto.getName() != null) {
            prod.setName(productDto.getName());
        }
        if (productDto.getDescription() != null) {
            prod.setDescription(productDto.getDescription());
        }
        if (productDto.getPrice() != null) {
            prod.setPrice(productDto.getPrice());
        }
        if (productDto.getProducerId() != null) {
            prod.setProducerOwner((Producer) userService.getUserById(productDto.getProducerId()));
        }

        return productRepository.save(prod);
    }

    public boolean delete(Long id) {
        try {
            productRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }

    }
}
