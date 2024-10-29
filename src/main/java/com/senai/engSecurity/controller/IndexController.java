package com.senai.engSecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class IndexController {

    @GetMapping("/")
    public String acesso() {
        return "Todos podem acessar";
    }

    @GetMapping("/user")
    public String usuario() {
        return "Acesso do Usuário";
    }

    @GetMapping("/admin")
    public String admin() {
        return "Acesso do Administrador";
    }
}
