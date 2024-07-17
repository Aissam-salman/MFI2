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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The type Product service.
 */
@Service
@RequiredArgsConstructor
public class ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;
    private final UserService userService;

    /**
     * Find all list.
     *
     * @return the list
     */
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    /**
     * Find by id product.
     *
     * @param id the id
     * @return the product
     */
    @Transactional(readOnly = true)
    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    /**
     * Save product.
     *
     * @param productDto the product dto
     * @return the product
     */
    @Transactional
    public Product save(ProductDto productDto) {
        Product pr = Product.builder()
                .name(productDto.getName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .producerOwner((Producer) userService.getUserById(productDto.getProducerId()))
                .build();

        return productRepository.save(pr);
    }

    /**
     * Update product.
     *
     * @param productId  the product id
     * @param productDto the product dto
     * @return the product
     */
    @Transactional
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

    /**
     * Delete boolean.
     *
     * @param id the id
     * @return the boolean
     */
    @Transactional
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
