/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Coria.controladores;

import Coria.entidades.Usuario;
import Coria.enumeraciones.Rol;
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

            usuarioServicio.modificarUsuario(id, nombre, apellido, email, telefono, currentPassword);
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
    ///////////////////////////////////////

    @GetMapping("/modificarAdmin/{id}")
    public String modificarAdmin(ModelMap modelo) {
        // Lógica para cargar datos necesarios, si es necesario
        modelo.addAttribute("mensaje", "¡Bienvenido al formulario de modificación de contraseña!");
        return "modificarAdmin";
    }

    @PostMapping("/modificarAdmin/{id}")
    public String modificarAdmin(
            @PathVariable String id,
            @RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam String email,
            @RequestParam String telefono,
            @RequestParam Rol nuevoRol,
            RedirectAttributes redirectAttributes
    ) throws MiException {
        try {
            usuarioServicio.AdministradorModifica(id, nombre, apellido, email, telefono, nuevoRol);
            redirectAttributes.addFlashAttribute("mensaje", "Usuario modificado Correctamente");
            return "redirect:../perfilAdmin/{id}";
        } catch (MiException ex) {
            System.out.println(ex.getMessage());
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
            return "redirect:../perfilAdmin/{id}";
        }
    }

    /////////////////////////////////////////////
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

    @GetMapping("/perfilAdmin/{id}")
    public String mostrarFormularioAdmin(@PathVariable String id, ModelMap modelo) {
        // Lógica para obtener el usuario por ID y agregarlo al modelo
        Usuario usuario = usuarioServicio.getOne(id);
        modelo.addAttribute("usuario", usuario);
        return "adminModifica.html";
    }

    @PostMapping("/perfilAdmin/{id}")
    public String mostrarFormularioAdmin(@PathVariable String id, @RequestParam String password, ModelMap modelo,
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
            return "redirect:/adminModifica/{id}";
        }
    }
    
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    @GetMapping("/informacion") // Ruta modificada para evitar ambigüedad
    public String obtenerInformacion(ModelMap modelo, HttpSession session) {
        // Lógica para obtener información del usuario
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");

        // Verificar si el usuario está autenticado
        if (usuario != null) {
            // Aquí puedes acceder a los atributos del usuario y agregarlos al modelo
            modelo.addAttribute("nombre", usuario.getNombre());
            modelo.addAttribute("apellido", usuario.getApellido());
            modelo.addAttribute("email", usuario.getEmail());
            modelo.addAttribute("telefono", usuario.getTelefono());
            // ... y otros atributos que desees mostrar en la página

            return "informacion.html"; // Nombre de la vista (puede ser "informacion.html" en tu caso)
        } else {
            // Manejar el caso en el que el usuario no esté autenticado
            // Puedes redirigirlo a una página de inicio de sesión, por ejemplo
            modelo.addAttribute("mensaje", "Debes iniciar sesión para ver esta información.");

            return "redirect:/login";
        }
    }

    @PostMapping("/informaciones")
    public String procesarInformacion(@RequestParam String datoFormulario, RedirectAttributes redirectAttributes, HttpSession session) {
        try {
            // Lógica para procesar la información enviada por el formulario
            // Puedes utilizar el parámetro "datoFormulario" y procesar los datos según tus necesidades

            // Ejemplo ficticio de procesamiento
            String resultadoProcesamiento = "La información se procesó correctamente: " + datoFormulario;

            // Agregar el resultado al modelo para mostrarlo en la página de información
            redirectAttributes.addFlashAttribute("mensaje", resultadoProcesamiento);

            // Redirige a la página de información
            return "redirect:/informacion";
        } catch (Exception e) {
            // Manejar errores y redirigir a la página de información en caso de error
            redirectAttributes.addFlashAttribute("error", "Hubo un error al procesar la información.");
            return "redirect:/informacion";
        }
    }

    @GetMapping("/contacto")
    public String mostrarFormularioContacto(ModelMap modelo, HttpSession session) {
        // Verificar si hay un usuario autenticado
        Usuario usuarioAutenticado = (Usuario) session.getAttribute("usuariosession");

        // Si hay un usuario autenticado, prellenar el formulario con su información
        if (usuarioAutenticado != null) {
            modelo.addAttribute("nombre", usuarioAutenticado.getNombre());
            modelo.addAttribute("email", usuarioAutenticado.getEmail());
            modelo.addAttribute("telefono", usuarioAutenticado.getTelefono());
        }

        // Puedes agregar lógica adicional aquí si es necesario
        return "contacto.html";
    }

    @PostMapping("/contactos")
    public String enviarMensajeContacto(@RequestParam String nombre, @RequestParam String email,
            @RequestParam String telefono, @RequestParam String comentario,
            RedirectAttributes redirectAttributes, HttpSession session) {
        try {
            // Obtener el usuario autenticado
            Usuario usuarioAutenticado = (Usuario) session.getAttribute("usuariosession");

            // Verificar si hay un usuario autenticado
            if (usuarioAutenticado != null) {
                // Puedes usar el nombre del usuario autenticado en lugar del nombre proporcionado en el formulario
                nombre = usuarioAutenticado.getNombre();
            }

            // Lógica para procesar el mensaje de contacto
            // Puedes almacenar la información en la base de datos, enviar un correo, etc.
            // Ejemplo ficticio de procesamiento
            String mensajeProcesado = "¡Gracias por tu mensaje, " + nombre + "! Nos pondremos en contacto contigo pronto.";

            // Agregar el mensaje al modelo para mostrarlo en la página de contacto
            redirectAttributes.addFlashAttribute("mensaje", mensajeProcesado);

            // Redirige a la página de contacto
            return "redirect:/contacto";
        } catch (Exception e) {
            // Manejar errores y redirigir a la página de contacto en caso de error
            redirectAttributes.addFlashAttribute("error", "Hubo un error al procesar tu mensaje. Por favor, inténtalo de nuevo.");
            return "redirect:/contacto";
        }
    }
}

//@RequestParam("archivo") MultipartFile archivo,
