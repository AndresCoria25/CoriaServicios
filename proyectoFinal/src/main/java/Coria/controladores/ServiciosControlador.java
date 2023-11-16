/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Coria.controladores;

import Coria.entidades.Proveedor;
import Coria.servicios.ProveedorServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author romi_
 */
@Controller
@RequestMapping("/servicios")
public class ServiciosControlador {

    private final ProveedorServicio provServ;

    @Autowired
    public ServiciosControlador(ProveedorServicio provServ) {
        this.provServ = provServ;
    }

    @GetMapping("/gasista")
    public String mostrarGasistas(Model model) {
        List<Proveedor> gasistas = provServ.obtenerProveedoresPorTipo("Gasista");
        model.addAttribute("gasistas", gasistas);
        return "gasista"; // El nombre del archivo HTML sin la extensión y la ruta según tu configuración
    }

    @GetMapping("/electricista")
    public String mostrarElectricistas(Model model) {
        List<Proveedor> electricistas = provServ.obtenerProveedoresPorTipo("Electricista");
        model.addAttribute("electricistas", electricistas);
        return "electricista";
    }

    @GetMapping("/plomero")
    public String mostrarPlomeros(Model model) {
        List<Proveedor> plomeros = provServ.obtenerProveedoresPorTipo("Plomero");
        model.addAttribute("plomeros", plomeros);
        return "plomero";
    }
}