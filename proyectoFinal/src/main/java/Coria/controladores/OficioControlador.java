
package Coria.controladores;

import Coria.entidades.Oficio;
import Coria.excepciones.MiException;
import Coria.servicios.OficioServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/") //localhost:8080
public class OficioControlador {

    @Autowired
    private OficioServicio oficioServicio;
    
    @GetMapping("/listaOficios")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String listarOficios(ModelMap modelo) {
        List<Oficio> listaOficios = oficioServicio.listarOficio();
        modelo.addAttribute("listaOfiios", listaOficios);
        return "listaOficios.html";
    }
    
    @GetMapping("/crearOficio")
    public String crearOficio(Model model) {
        model.addAttribute("oficio", new Oficio());
        model.addAttribute("nombreOficio", "");
        model.addAttribute("comentarioOficio", "");
        return "crearOficio.html";
    }

    @PostMapping("/crearOficio")
    public String registro(@RequestParam String nombreOficio,@RequestParam String comentarioOficio,RedirectAttributes redirectAttributes) throws MiException {
        try {
            oficioServicio.crearOficio(nombreOficio, comentarioOficio);
            redirectAttributes.addFlashAttribute("mensaje", "Alta completada con Exito");
            return "redirect:/crearOficio";
        } catch (MiException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
            return "redirect:/crearOficio";
        }
    }

    
    @PostMapping("/eliminarOficio/{idOficio}")
    public String eliminarOficio(@PathVariable String idOficio, ModelMap model) {
        try {
            oficioServicio.eliminarOficio(idOficio);
            return "redirect:../listaOficios";
        } catch (Exception e) {
            model.put("error", e.getMessage());
        }
        return "redirect:../listaOficios";
    }

}