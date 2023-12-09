/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seguridad;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 *
 * @author romi_
 */
@Component
public class proveedorSecurity {
   
   public boolean checkProveedor(Authentication authentication, String proveedorId) {
        // Obtiene el proveedor actual desde el contexto de seguridad
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String proveedorActualId = userDetails.getUsername();

        
        // Verifica si el proveedor actual es igual al proveedor asociado al trabajo
        return proveedorActualId.equals(proveedorId);
    }
}

