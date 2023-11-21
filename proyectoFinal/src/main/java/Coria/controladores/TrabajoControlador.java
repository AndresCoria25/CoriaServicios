/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Coria.controladores;

///// Franco
import Coria.entidades.Trabajo;
import Coria.servicios.TrabajoServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

     @GetMapping("/buscar")
    public ResponseEntity<List<Trabajo>> buscarProductos(@RequestParam String searchTerm) {
        List<Trabajo> trabajo = traRep.listarTrabajo(searchTerm);
        return new ResponseEntity<>(trabajo, HttpStatus.OK);
        
    }

    @GetMapping("/search")
    public String search(@RequestParam(name = "query", required = false) String query, ModelMap model) {
        // Lógica de búsqueda aquí
        // Puedes pasar los resultados de búsqueda al modelo
        model.addAttribute("query", query);
        // Devuelve la vista de resultados de búsqueda
        return "listaBusqueda.html";
    }
    
    
}
