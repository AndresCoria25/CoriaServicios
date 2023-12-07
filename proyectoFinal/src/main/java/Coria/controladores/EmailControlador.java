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

//        try {
//            // Validación de parámetros
//            if (name.isEmpty() || email.isEmpty() || subject.isEmpty() || body.isEmpty()) {
//                // Manejar la validación fallida, puedes lanzar una excepción o mostrar un mensaje al usuario
//                return "error";
//            }
        // Construir el contenido del correo electrónico
        String emailContent = "\nNombre: " + name + "\nEmail: " + email + "\nConsulta: " + body ;

        // Enviar el correo electrónico
        emailServicio.sendEmail("asesora.vero@gmail.com", "romina.figueroa1986@gmail.com", subject, emailContent);

        return "redirect:/informacion";
//        } catch (Exception e) {
//            // Manejar cualquier excepción que pueda ocurrir durante el envío del correo electrónico
//            e.printStackTrace();  // Puedes personalizar esto según tus necesidades
//
//        }
//        return "redirect:/informacion";
    }
}