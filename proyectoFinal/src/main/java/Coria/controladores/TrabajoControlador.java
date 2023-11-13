/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Coria.controladores;

///// Franco
import Coria.servicios.TrabajoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

=======
///// Desarrolladores
/**
 *
 * @author romi_
 */
///// Franco
@Controller
@RequestMapping("/")
public class TrabajoControlador {
    @Autowired
    private TrabajoServicio traRep;
     @GetMapping("/informacion")
    public String mostrarInformacion() {
        return "informacion.html"; 
        
        //ESTE CONTROLADOR SOLO ESTA EN MODO DE VISTA
    }
=======
public class TrabajoControlador {
    
///// Desarrolladores
}
