package com.app.mfi2.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * The type User controller.
 */
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Tag(name = "Users", description = "API gestion utilisateurs")
@CrossOrigin(value = "*")
public class HomeController {
    /**
     * Home string.
     *
     * @return the string
     */
    @GetMapping
    @ResponseBody
    public String Home() {
        return "Hello to the server!";
    }


}
