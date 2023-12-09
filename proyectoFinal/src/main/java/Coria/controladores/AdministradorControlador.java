package Coria.controladores;

import Coria.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

public class AdministradorControlador {

    @Controller
    @RequestMapping("/admin")
    public class AdminControlador {

        @Autowired
        private UsuarioServicio usuarioServicio;

        @GetMapping("/dashboard")
        public String panelAdministrativo() {
            return "panel.html";
        }
//
//    @GetMapping("/usuarios")
//    public String listar(ModelMap modelo) {
//        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
//        modelo.addAttribute("usuarios", usuarios);
//
//        return "usuario_list.html";
//    }

       
    }

}
