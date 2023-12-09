/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Coria.controladores;

import Coria.servicios.EmailServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author romi_
 */
@Controller
public class EmailControlador {

    @Autowired
    private EmailServicio emailServicio;

    @GetMapping("/sendMail")
    public String index() {
        return "contacto";
    }

    @PostMapping("/sendMail")
    public String sendMail(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("subject") String subject,
            @RequestParam("body") String body) {
        String emailContent = "\nNombre: " + name + "\nEmail: " + email + "\nConsulta: " + body ;

        // Enviar el correo electrónico
        emailServicio.sendEmail("azufrepo@gmail.com", "azufrepo@gmail.com", subject, emailContent);

        return "redirect:/informacion";

    }
}