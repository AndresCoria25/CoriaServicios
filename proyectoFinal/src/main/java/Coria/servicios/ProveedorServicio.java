/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Coria.servicios;

import Coria.entidades.Imagen;
import Coria.entidades.Proveedor;
import Coria.entidades.Trabajo;
import Coria.entidades.Usuario;
import Coria.enumeraciones.Rol;
import Coria.excepciones.MiException;
import Coria.repositorios.ProveedorRepositorio;
import Coria.dto.ProveedorDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProveedorServicio implements UserDetailsService {

    @Autowired
    private ProveedorRepositorio provRep;

    @Autowired
    private TrabajoServicio traserv;

    @Autowired
    public ProveedorServicio(ProveedorRepositorio proveedorRepositorio) {
        this.provRep = provRep;
    }
    @Autowired
    private ImagenServicio imagenServicio;

    public List<Proveedor> obtenerProveedoresPorTipo(String tipoServicio) {
        return provRep.findByTipoServicio(tipoServicio);
    }

    public List<Proveedor> listarProveedores() {

        return provRep.findAll();

    }
    

    public Proveedor getOne(String id) {
        return provRep.getOne(id);
    }

 public void calificarProveedor(String idTrabajo, Integer calificacion) throws MiException {
    Trabajo tra = traserv.getOne(idTrabajo);

    if (tra != null) {
        String idProveedor = tra.getProveedorId();
        Optional<Proveedor> optionalProveedor = provRep.findById(idProveedor);

        if (optionalProveedor.isPresent()) {
            Proveedor proveedor = optionalProveedor.get();

            if (proveedor.getCalificacionPromedio() != null && proveedor.getNumeroCalificaciones() != null) {
                double nuevoPromedio = ((proveedor.getCalificacionPromedio() * proveedor.getNumeroCalificaciones()) + calificacion)
                        / (proveedor.getNumeroCalificaciones() + 1);

                proveedor.setCalificacionPromedio(Math.floor(nuevoPromedio));
                proveedor.setNumeroCalificaciones(proveedor.getNumeroCalificaciones() + 1);

                provRep.save(proveedor);
            } else {
                throw new MiException("Los valores de calificación promedio o número de calificaciones son nulos.");
            }
        } else {
            throw new MiException("Proveedor no encontrado");
        }
    } else {
        throw new MiException("Trabajo no encontrado");
    }
}


    public List<Proveedor> listarProveedor() {
        List<Proveedor> proveedor = new ArrayList();
        proveedor = provRep.findAll();
        return proveedor;
    }

 

    @Transactional
    public void actualizar(String nombre, String apellido, String email, String telefono, String password, String nombreEmpresa, String tipoServicio) throws MiException {
        validar(nombre, apellido, email, telefono, password, nombreEmpresa, tipoServicio);

        Optional<Proveedor> optionalProveedor = provRep.findById(nombreEmpresa);
        if (optionalProveedor.isPresent()) {
            Proveedor prov = optionalProveedor.get();

            prov.setTipoServicio(tipoServicio);

            provRep.save(prov);
        } else {
            throw new MiException("Proveedor no encontrado");
        }
    }

    @Transactional
    public void eliminar(String nombreEmpresa) throws MiException {
        if (nombreEmpresa == null || nombreEmpresa.isEmpty()) {
            throw new MiException("Nombre de empresa no válido");
        }

        Optional<Proveedor> optionalProveedor = provRep.findById(nombreEmpresa);
        if (optionalProveedor.isPresent()) {
            provRep.deleteByNombreEmpresa(nombreEmpresa);
        } else {
            throw new MiException("Proveedor no encontrado");
        }
    }

    public Proveedor obtenerPorNombre(String nombreEmpresa) throws MiException {
        if (nombreEmpresa == null || nombreEmpresa.isEmpty()) {
            throw new MiException("Nombre de empresa no válido");
        }

        Optional<Proveedor> optionalProveedor = provRep.findById(nombreEmpresa);
        if (optionalProveedor.isPresent()) {
            return optionalProveedor.get();
        } else {
            throw new MiException("Proveedor no encontrado");
        }
    }

    private void validar(String nombre, String apellido, String email, String telefono, String password, String nombreEmpresa, String tipoServicio) throws MiException {

        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("el nombre no puede ser nulo o estar vacío");
        }
        if (apellido.isEmpty() || apellido == null) {
            throw new MiException("el apellido no puede ser nulo o estar vacío");
        }

        if (email.isEmpty() || email == null) {
            throw new MiException("el email no puede ser nulo o estar vacio");
        }

        if (telefono.isEmpty()) {
            throw new MiException("el telefono no puede estar vacio");
        }
        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new MiException("La contraseña no puede estar vacía, y debe tener más de 5 dígitos");
        }
        if (nombreEmpresa.isEmpty() || nombreEmpresa == null) {
            throw new MiException("el nombre de empresa no puede ser nulo o estar vacío");
        }
        if (tipoServicio.isEmpty() || tipoServicio == null) {
            throw new MiException("el tipo de servicio no puede ser nulo o estar vacio");
        }

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = provRep.buscarPorEmail(email);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado con el correo electrónico: " + email);
        }

        // Puedes construir tu objeto UserDetails basado en la entidad Usuario
        // Aquí, estoy usando una implementación simple de User
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_" + usuario.getRol().name());

        return new User(usuario.getEmail(), usuario.getPassword(), authorities);
    }
}