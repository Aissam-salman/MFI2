package com.app.mfi2.fixture;

import com.app.mfi2.auth.AuthService;
import com.app.mfi2.auth.dto.RegisterRequest;
import com.app.mfi2.model.ECartStatus;
import com.app.mfi2.model.Product;
import com.app.mfi2.repository.ProductRepository;
import com.app.mfi2.service.CartService;
import com.app.mfi2.service.ProductService;
import com.app.mfi2.user.Role;
import com.app.mfi2.user.model.Admin;
import com.app.mfi2.user.model.Client;
import com.app.mfi2.user.model.Producer;
import com.app.mfi2.user.model.User;
import com.app.mfi2.user.repository.ProducerRepository;
import com.app.mfi2.user.service.UserService;
import com.app.mfi2.utils.MapperDTO;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * The type Data db init.
 */
@Component
@AllArgsConstructor
public class DataDBInit implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(DataDBInit.class);
    private final AuthService authService;
    private final UserService userService;
    private final ProductService productService;
    private final ProductRepository productRepository;
    private final CartService cartService;
    private final ProducerRepository producerRepository;

    @Override
    public void run(String... args) throws Exception {
        logger.info("\uD83C\uDFC1  Starting data initialization...");
        initUser();
        initProduct();
        initCart();
    }

    private void initUser() throws Exception {
        logger.info("Initializing user  \uD83E\uDEF5");
        User admin = Admin.builder()
                .id(1L)
                .firstname("lamjadab")
                .lastname("salman")
                .email("salman@gmail.com")
                .password("secret")
                .role(Role.ADMIN)
                .build();
        User producer = Producer.builder()
                .id(2L)
                .firstname("Produ")
                .lastname("cer")
                .email("producer@gmail")
                .password("secret")
                .role(Role.PRODUCER)
                .siret("123456789")
                .build();
        User client = Client.builder()
                .id(3L)
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

        User producerData = userService.getUserById(2L);

        Producer producer = MapperDTO.convertToDto(producerData, Producer.class);

        Product product = Product.builder()
                .name("Banana")
                .description("Culture bananier au soleil")
                .price(1.99)
                .producerOwner(producer)
                .build();
        Product product1 = Product.builder()
                .name("Pomme")
                .description("Champs unique à la plage")
                .price(2.89)
                .producerOwner(producer)
                .build();
        Product product2 = Product.builder()
                .name("Kiwi")
                .description("Couleur jaune unique avec un gout sucré")
                .price(4.79)
                .producerOwner(producer)
                .build();
        productRepository.save(product);
        productRepository.save(product1);
        productRepository.save(product2);
        logger.info("Registered product successfully ✅");
    }

    private void initCart() {
        logger.info("Initializing cart \uD83C\uDF47");
        logger.info(">> add 3 Banana \uD83C\uDF4C");
        cartService.addItemToCart(3L, 1L, 2);
        logger.info(">> add 5 Pomme \uD83C\uDF4E");
        cartService.addItemToCart(3L, 2L, 5);
        logger.info(">> add 2 Kiwi \uD83E\uDD5D");
        cartService.addItemToCart(3L, 3L, 2);
        logger.info("Registered cart successfully ✅");
        cartService.changeStatus(3L, ECartStatus.VALIDATE);
        logger.info("Cart status: VALIDATE \uD83D\uDED2");
    }
}
