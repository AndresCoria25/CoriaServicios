/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Coria.controladores;

import Coria.entidades.OrdenTrabajo;
import Coria.repositorios.OrdenTrabajoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author romi_
 */
@Controller
public class OrdenTrabajoControlador {

    @Autowired
    private OrdenTrabajoRepository ordenTrabajoRepository;

    @GetMapping("/ordenTrabajo/{id}")
    public String verOrdenTrabajo(@PathVariable Long id, Model model) {
        OrdenTrabajo ordenTrabajo = ordenTrabajoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Orden de trabajo no encontrada"));
        model.addAttribute("ordenTrabajo", ordenTrabajo);
        return "OrdenTrabajo";
    }
}
