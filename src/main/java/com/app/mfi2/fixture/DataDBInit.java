package com.app.mfi2.fixture;

import com.app.mfi2.auth.AuthService;
import com.app.mfi2.auth.dto.RegisterRequest;
import com.app.mfi2.model.Product;
import com.app.mfi2.repository.ProductRepository;
import com.app.mfi2.service.ProductService;
import com.app.mfi2.user.Role;
import com.app.mfi2.user.model.Admin;
import com.app.mfi2.user.model.Client;
import com.app.mfi2.user.model.Producer;
import com.app.mfi2.user.model.User;
import com.app.mfi2.user.service.UserService;
import com.app.mfi2.utils.MapperDTO;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataDBInit implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(DataDBInit.class);
    private final AuthService authService;
    private final UserService userService;
    private final ProductService productService;
    private final ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {
        logger.info("\uD83C\uDFC1  Starting data initialization...");
        initUser();
        initProduct();
    }

    private void initUser() throws Exception {
        logger.info("Initializing user  \uD83E\uDEF5");
        User admin = Admin.builder()
                .firstname("lamjadab")
                .lastname("salman")
                .email("salman@gmail.com")
                .password("secret")
                .role(Role.ADMIN)
                .build();
        User producer = Producer.builder()
                .firstname("Produ")
                .lastname("cer")
                .email("producer@gmail")
                .password("secret")
                .role(Role.PRODUCER)
                .siret("123456789")
                .build();
        User client = Client.builder()
                .firstname("cli")
                .lastname("clicli")
                .email("cli@gmail.com")
                .password("secret")
                .role(Role.CLIENT)
                .build();

        authService.register(MapperDTO.convertToDto(admin, RegisterRequest.class));
        authService.register(MapperDTO.convertToDto(producer, RegisterRequest.class));
        authService.register(MapperDTO.convertToDto(client, RegisterRequest.class));
        logger.info("Registered user successfully ✅");
    }

    private void initProduct() {
        logger.info("Initializing product \uD83C\uDF47");
        Product product = Product.builder()
                .name("Banana")
                .description("Culture bananier au soleil")
                .price(1.99)
                .producerOwner((Producer) userService.getUserById(2L))
                .build();
        Product product1 = Product.builder()
                .name("Pomme")
                .description("Champs unique à la plage")
                .price(2.89)
                .producerOwner((Producer) userService.getUserById(2L))
                .build();
        Product product2 = Product.builder()
                .name("Kiwi")
                .description("Couleur jaune unique avec un gout sucré")
                .price(4.79)
                .producerOwner((Producer) userService.getUserById(2L))
                .build();
        productRepository.save(product);
        productRepository.save(product1);
        productRepository.save(product2);
        logger.info("Registered product successfully ✅");
    }
}
