/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Coria.controladores;

///// Franco

import Coria.entidades.Proveedor;
import Coria.excepciones.MiExcepcion;
import Coria.servicios.ProveedorServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/proveedor")
public class ProveedorControlador {
    

    @Autowired
    private ProveedorServicio provServ;

    @GetMapping("/registrar")
    public String registrar() {
        return "proveedor_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombreEmpresa,@RequestParam String tipoServicio,@RequestParam String calificacion, ModelMap modelo) {

        try {
            provServ.registrar(nombreEmpresa, tipoServicio, calificacion);
            modelo.put("exito", "El Proveedor fue registrado correctamente");
        } catch (MiExcepcion ex) {
            modelo.put("error", ex.getMessage());
            return "proveedor_form.html";
        }

        return "index.html";
    }

   

}

=======
/**
 *
 * @author romi_
 */
public class ProveedorControlador {
    
}
///// Desarrolladores
