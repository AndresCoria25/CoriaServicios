/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Coria.controladores;


import Coria.entidades.Usuario;
import Coria.excepciones.MiExcepcion;
import Coria.servicios.UsuarioServicio;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author FraNko
 */
@Controller
@RequestMapping("/") //localhost:8080
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/")//localhost:8080
    public String index(ModelMap modelo, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        if (usuario != null) {
            modelo.put("usuario", usuario);//usuarioServicio.getOne(usuario.getId())
        }
        return "index.html";

    }

    @GetMapping("/registrar")//localhost:8080/registrar
    public String registrar() {
        return "registro.html";
    }

     @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String apellido, @RequestParam String email,
            @RequestParam String password, String password2, @RequestParam String telefono, RedirectAttributes redirectAttributes) throws MiExcepcion {
        try {
           usuarioServicio.registrar(nombre, apellido, email, telefono, password);
            redirectAttributes.addFlashAttribute("mensaje", "Registro Exitoso. Ahora puedes Iniciar Sesión.");
            return "redirect:/";
        } catch (MiExcepcion ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
            return "redirect:/";
        }
    }

    @GetMapping("/login")//localhost:8080/login
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {
        if (error != null) {
            modelo.put("error", "Usuario o Contraseña Invalidos!");
        }
        return "login.html";
    }

    @GetMapping("/perfil/{id}")
    public String actualizar(@PathVariable String id, @RequestParam String nombre, @RequestParam String apellido,
            @RequestParam String email, @RequestParam String password,@RequestParam String telefono, ModelMap modelo,
            HttpSession session) throws Exception {
        try {
            Usuario usuario = usuarioServicio.actualizar(id, nombre, apellido, email, telefono, password);
            session.setAttribute("usuariosession", usuario);
            return "redirect:/";
        } catch (MiExcepcion ex) {
            modelo.put("error", ex.getMessage());
            ex.printStackTrace();
            Usuario usuario = usuarioServicio.getOne(id);
            modelo.put("usuario", usuario);
            return "modificar.html";
        }
    }

}

//@RequestParam("archivo") MultipartFile archivo,
