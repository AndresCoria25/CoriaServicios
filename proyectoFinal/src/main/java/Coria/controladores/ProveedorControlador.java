/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Coria.Controladores;

import Coria.Entidades.Proveedor;

import Coria.Servicios.ProveedorServicio;
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
    public String registro(@RequestParam String nombre, ModelMap modelo) {

        try {
            provServ.crearProveedor(nombre);
            modelo.put("exito", "El Proveedor fue registrado correctamente");
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            return "proveedor_form.html";
        }

        return "index.html";
    }

    @GetMapping("/proveedor")
    public String listar(ModelMap modelo) {

        List<Proveedor> proveedor = provServ.listarProveedor();

        modelo.addAttribute("proveedores", proveedor);

        return "proveedor_list.html";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) {
        modelo.put("proveedor", provServ.getOne(id));
        return "proveedor_modificar.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String nombre, ModelMap modelo) {
        try {
            provServ.modificarProveedor(id, nombre);
            modelo.put("exito", "El Proveedor fue modificado correctamente");
            return "redirect:/proveedor/lista";
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            return "proveedor_modificar.html";
        }

    }

}


