/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Coria.controladores;

///// Franco

import Coria.entidades.Proveedor;
import Coria.excepciones.MiException;
import Coria.servicios.ProveedorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class ProveedorControlador {

    @Autowired
    private ProveedorServicio provServ;

    @Controller
    public class TuControlador {

        @GetMapping("/editar/{nombreEmpresa}")
        public String editarProveedor(@PathVariable String nombreEmpresa, ModelMap modelo) {
            try {
                Proveedor proveedor = provServ.obtenerPorNombre(nombreEmpresa);
                modelo.put("proveedor", proveedor);
                return "editar_proveedor.html";
            } catch (MiException ex) {
                modelo.put("error", ex.getMessage());
                return "error.html";
            }
        }

        @PostMapping("/guardarEdicion")
        public String guardarEdicion(@RequestParam String nombreEmpresa, @RequestParam String tipoServicio, @RequestParam String calificacion, ModelMap modelo) {
            try {
                provServ.actualizar(nombreEmpresa, tipoServicio, calificacion, calificacion, tipoServicio, nombreEmpresa, tipoServicio);
                modelo.put("exito", "Proveedor actualizado correctamente");
            } catch (MiException ex) {
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
            } catch (MiException ex) {
                modelo.put("error", ex.getMessage());
            }
            return "redirect:/proveedor/listar";
        }
    }
}