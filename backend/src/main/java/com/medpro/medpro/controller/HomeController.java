package com.medpro.medpro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

   @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/telas/medicos")
    public String medicos() {
        return "medicos";
    }

    @GetMapping("/telas/pacientes")
    public String pacientes() {
        return "pacientes";
    }

    @GetMapping("/telas/consultas")
    public String consultas() {
        return "consultas";
    }

}
