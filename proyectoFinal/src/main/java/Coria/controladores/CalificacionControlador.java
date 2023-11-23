/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Coria.controladores;

import Coria.entidades.Usuario;
import Coria.servicios.CalificacionServicio;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author romi_
 */
@Controller
@RequestMapping("/")
public class CalificacionControlador {

    @Autowired
    private CalificacionServicio califSer;

    @GetMapping("/calificacion")
    public String mostrarPaginaCalificacion(ModelMap model, HttpSession session) {
        // Verificar si hay un usuario autenticado
        Usuario usuarioAutenticado = (Usuario) session.getAttribute("usuariosession");

        if (usuarioAutenticado != null) {
            // Lógica para obtener información del proveedor (ajusta según tu modelo)
            String proveedor = "Nombre del Proveedor";
            String tipoTrabajo = "Tipo de Trabajo Realizado";
            model.addAttribute("proveedor", proveedor);
            model.addAttribute("tipoTrabajo", tipoTrabajo);

            return "calificacion.html"; // Nombre de la vista (puede ser "calificacion.html" en tu caso)
        } else {
            // Manejar el caso en el que no haya un usuario autenticado
            // Puedes redirigirlo a una página de inicio de sesión, por ejemplo
            return "redirect:/login";
        }
    }

    // Procesar la calificación
    @PostMapping("/calificacion")
    public String procesarCalificacion(
            @RequestParam String proveedor,
            @RequestParam String tipoTrabajo,
            @RequestParam String comentario,
            @RequestParam int calificacion,
            RedirectAttributes redirectAttributes) {
        try {
            // Lógica para procesar la calificación (almacenar en la base de datos, etc.)
            // Enviar correo al administrador utilizando el servicio
            califSer.enviarCorreoAdministrador(proveedor, tipoTrabajo, comentario, calificacion);

            // Resto del código...
            // Ejemplo ficticio de procesamiento
            String mensaje = "¡Calificación exitosa! Gracias por tu opinión.";
            redirectAttributes.addFlashAttribute("mensaje", mensaje);

            return "redirect:/calificacion"; // Redirige a la página de calificación después del procesamiento
        } catch (Exception e) {
            // Manejar errores y redirigir a la página de calificación en caso de error
            redirectAttributes.addFlashAttribute("error", "Hubo un error al procesar la calificación.");
            return "redirect:/calificacion";
        }
    }

    public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

        @RequestMapping("/error")
        public String handleError(HttpServletRequest request) {
            // Obtener el código de error
            Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

            if (status != null) {
                int statusCode = Integer.parseInt(status.toString());

                // Manejar diferentes códigos de error según sea necesario
                if (statusCode == HttpStatus.NOT_FOUND.value()) {
                    return "error-404"; // Página personalizada para error 404
                } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                    return "error-500"; // Página personalizada para error 500
                }
            }

            // Enviar a la página de error predeterminada si no coincide con los códigos anteriores
            return "error";
        }

    }
}