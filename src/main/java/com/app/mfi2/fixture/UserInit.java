package com.app.mfi2.fixture;

import com.app.mfi2.auth.AuthService;
import com.app.mfi2.auth.dto.RegisterRequest;
import com.app.mfi2.user.Role;
import com.app.mfi2.user.model.Admin;
import com.app.mfi2.user.model.Client;
import com.app.mfi2.user.model.Producer;
import com.app.mfi2.user.model.User;
import com.app.mfi2.utils.MapperDTO;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserInit implements CommandLineRunner {
    private final AuthService authService;
    private static final Logger logger =  LoggerFactory.getLogger(UserInit.class);

    @Override
    public void run(String... args) throws Exception {
        logger.info("Starting data initialization...");
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
        logger.info("Registered user successfully");
    }
}
