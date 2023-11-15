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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Controller
@RequestMapping("/")
public class ProveedorControlador {

    @Autowired
    private ProveedorServicio provServ;

    @GetMapping("/registrarP")
    public String registrarProveedor(Model model) {
        model.addAttribute("proveedor", new Proveedor()); // Asegura que se inicialice un Proveedor para el formulario
        return "registroP";
    }


//    @PostMapping("/registro")
//    public String registroProveedor(@RequestParam String nombreEmpresa, 
//                                    @RequestParam String tipoServicio, 
//                                    @RequestParam String calificacion, 
//                                    ModelMap modelo) {
//        try {
//            provServ.registrar(nombreEmpresa, tipoServicio, calificacion);
//            modelo.put("exito", "El Proveedor fue registrado correctamente");
//        } catch (MiExcepcion ex) {
//            modelo.put("error", ex.getMessage());
//            return "proveedor_form.html";
//        }
//        return "redirect:/proveedor/listar";
//    }
    
  @PostMapping("/registroP")
    public String registro(@ModelAttribute Proveedor proveedor, RedirectAttributes redirectAttributes) throws MiExcepcion {
        try {
            provServ.registrar(proveedor.getNombre(), proveedor.getApellido(), proveedor.getEmail(),
                    proveedor.getTelefono(), proveedor.getPassword(), proveedor.getNombreEmpresa(),
                    proveedor.getTipoServicio());
            redirectAttributes.addFlashAttribute("mensaje", "El Proveedor fue registrado correctamente");
            return "redirect:/";
        } catch (MiExcepcion ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
            return "redirect:/";
        }
    }

    @GetMapping("/editar/{nombreEmpresa}")
    public String editarProveedor(@PathVariable String nombreEmpresa, ModelMap modelo) {
        try {
            Proveedor proveedor = provServ.obtenerPorNombre(nombreEmpresa);
            modelo.put("proveedor", proveedor);
            return "editar_proveedor.html";
        } catch (MiExcepcion ex) {
            modelo.put("error", ex.getMessage());
            return "error.html";
        }
    }

    @PostMapping("/guardarEdicion")
    public String guardarEdicion(@RequestParam String nombreEmpresa, @RequestParam String tipoServicio, @RequestParam String calificacion, ModelMap modelo) {
        try {
            provServ.actualizar(nombreEmpresa, tipoServicio, calificacion, calificacion, tipoServicio, nombreEmpresa, tipoServicio);
            modelo.put("exito", "Proveedor actualizado correctamente");
        } catch (MiExcepcion ex) {
            modelo.put("error", ex.getMessage());
            return "editar_proveedor.html";
        }
        return "redirect:/proveedor/listar";
    }

    @GetMapping("/eliminar/{nombreEmpresa}")
    public String eliminarProveedor(@PathVariable String nombreEmpresa, ModelMap modelo) {
        try {
            provServ.eliminar(nombreEmpresa);
            modelo.put("exito", "Proveedor eliminado correctamente");
        } catch (MiExcepcion ex) {
            modelo.put("error", ex.getMessage());
        }
        return "redirect:/proveedor/listar";
    }
}

