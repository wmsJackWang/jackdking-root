package com.jta.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

 
@Controller
public class SwaggerApi {

    /**
     * Swagger ui string.
     *
     * @return the string
     */
    @GetMapping("/")
    public String swaggerUi() {
        return "redirect:/swagger-ui.html";
    }
}