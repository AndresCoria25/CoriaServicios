/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Coria.controladores;

import Coria.entidades.Usuario;
import Coria.excepciones.MiException;
import Coria.excepciones.MiException;
import Coria.servicios.UsuarioServicio;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
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

    //CORRESPONDE AL ADMIN
    @GetMapping("/lista")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String listarUsuarios(ModelMap modelo) {
        List<Usuario> listaUsuarios = usuarioServicio.listarUsuarios();
        modelo.addAttribute("listaUsuarios", listaUsuarios);
        return "listaUsuarios.html";
    }
    //CORRESPONDE AL ADMIN

    @PostMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable String id, ModelMap model) {
        try {
            usuarioServicio.eliminarUsuario(id);
            return "redirect:../lista";
        } catch (Exception e) {
            model.put("error", e.getMessage());
        }
        return "redirect:../lista";
    }

    @GetMapping("/modificar/{id}")
    public String mostrarFormularioModificarContrasena(ModelMap modelo) {
        // Lógica para cargar datos necesarios, si es necesario
        modelo.addAttribute("mensaje", "¡Bienvenido al formulario de modificación de contraseña!");
        return "modificar";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(
            @PathVariable String id,
            @RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam String email,
            @RequestParam String telefono,
            @RequestParam String currentPassword,
            RedirectAttributes redirectAttributes
    ) throws MiException {
        try {

            usuarioServicio.modificarUsuario(id, nombre, apellido, email, telefono,currentPassword);
            redirectAttributes.addFlashAttribute("mensaje", "Usuario modificado Correctamente");
            return "redirect:../perfil/{id}";
        } catch (MiException ex) {
            System.out.println(ex.getMessage());
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
            return "redirect:../perfil/{id}";
        }
    }

    @GetMapping("/modificar1/{id}")
    public String mostrarFormularioModificarContrasena1(ModelMap modelo) {
        // Lógica para cargar datos necesarios, si es necesario
        modelo.addAttribute("mensaje", "¡Bienvenido al formulario de modificación de contraseña!");
        return "modificar1";
    }

    @PostMapping("/modificar1/{id}")
    public String modificar1(
            @PathVariable String id,
            @RequestParam String currentPassword,
            @RequestParam String newPassword,
            @RequestParam String newPassword2,
            RedirectAttributes redirectAttributes
    ) throws MiException {
        try {
            if (!newPassword.equals(newPassword2)) {
                throw new MiException("Las contraseñas no coinciden");
            }

            usuarioServicio.actualizarPassword(id, currentPassword, newPassword);
            redirectAttributes.addFlashAttribute("mensaje", "Contraseña modificada correctamente");
            return "redirect:../perfil1/{id}";
        } catch (MiException ex) {
            System.out.println(ex.getMessage());
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
            return "redirect:../perfil1/{id}";
        }
    }

    @GetMapping("/registrar")//localhost:8080/registrar
    public String registrar() {
        return "registro.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String apellido, @RequestParam String email,
            @RequestParam String password, String password2, @RequestParam String telefono, MultipartFile archivo, RedirectAttributes redirectAttributes) throws MiException {
        try {
            usuarioServicio.registrar(archivo, nombre, apellido, email, telefono, password);
            redirectAttributes.addFlashAttribute("mensaje", "Registro Exitoso. Ahora puedes Iniciar Sesión.");
            return "redirect:/";
        } catch (MiException ex) {
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
    public String mostrarFormulario(@PathVariable String id, ModelMap modelo) {
        // Lógica para obtener el usuario por ID y agregarlo al modelo
        Usuario usuario = usuarioServicio.getOne(id);
        modelo.addAttribute("usuario", usuario);
        return "perfil_usuario.html";
    }

    @PostMapping("/perfil/{id}")
    public String actualizar(@PathVariable String id, @RequestParam String nombre, @RequestParam String apellido,
            @RequestParam String email, @RequestParam String password, @RequestParam String telefono, MultipartFile archivo, ModelMap modelo,
            HttpSession session) throws Exception {
        try {
            System.out.println("Controlador de perfil ejecutado. ID: " + id);

            Usuario usuario = usuarioServicio.actualizar(archivo, id, nombre, apellido, email, telefono, password);
            session.setAttribute("usuariosession", usuario);
            return "redirect:/";
        } catch (MiException ex) {
            System.out.println("Error en el controlador de perfil: " + ex.getMessage());
            modelo.put("error", ex.getMessage());
            ex.printStackTrace();
            Usuario usuario = usuarioServicio.getOne(id);
            modelo.put("usuario", usuario);
            return "redirect:/perfil/{id}";
        }
    }

    @GetMapping("/perfil1/{id}")
    public String mostrarFormulario1(@PathVariable String id, ModelMap modelo) {
        // Lógica para obtener el usuario por ID y agregarlo al modelo
        Usuario usuario = usuarioServicio.getOne(id);
        modelo.addAttribute("usuario", usuario);
        return "perfil_usuario1.html";
    }

    @PostMapping("/perfil1/{id}")
    public String actualizarContraseña(@PathVariable String id, @RequestParam String password, ModelMap modelo,
            HttpSession session) throws Exception {
        try {
            System.out.println("Controlador de perfil ejecutado. ID: " + id);

            Usuario usuario = usuarioServicio.actualizarPassword(id, password, password);
            session.setAttribute("usuariosession", usuario);
            return "redirect:/";
        } catch (MiException ex) {
            System.out.println("Error en el controlador de perfil: " + ex.getMessage());
            modelo.put("error", ex.getMessage());
            ex.printStackTrace();
            Usuario usuario = usuarioServicio.getOne(id);
            modelo.put("usuario", usuario);
            return "redirect:/perfil1/{id}";
        }
    }
}

//@RequestParam("archivo") MultipartFile archivo,
