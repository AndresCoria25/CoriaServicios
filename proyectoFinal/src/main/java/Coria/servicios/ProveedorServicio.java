/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Coria.servicios;

import Coria.entidades.Proveedor;
import Coria.entidades.Usuario;
import Coria.enumeraciones.Rol;
import Coria.excepciones.MiExcepcion;
import Coria.repositorios.ProveedorRepositorio;
import dto.ProveedorDto;

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

@Service
public class ProveedorServicio implements UserDetailsService {
    
    @Autowired
    private ProveedorRepositorio provRep;
    
    @Transactional
    public void registrar(String nombre, String apellido, String email, String telefono, String password, String nombreEmpresa, String tipoServicio) throws MiExcepcion {
        
        validar(nombre, apellido, email, telefono, password, nombreEmpresa, tipoServicio);
        Proveedor usuarioExistente = provRep.buscarPorEmail(email);
        if (usuarioExistente != null) {
            throw new MiExcepcion("El correo electrónico ya está en uso.");
        }
        
        Proveedor prov = new Proveedor();
        prov.setNombre(nombre);
        prov.setApellido(apellido);
        prov.setEmail(email);
        prov.setTelefono(telefono);
        prov.setPassword(new BCryptPasswordEncoder().encode(password));
        prov.setRol(Rol.PROVEEDOR);
        prov.setNombreEmpresa(nombreEmpresa);
        prov.setTipoServicio(tipoServicio);
        
        provRep.save(prov);
    }
    
    public List<Proveedor> listarProveedor() {
        List<Proveedor> proveedor = new ArrayList();
        proveedor = provRep.findAll();
        return proveedor;
    }
    
    public List<ProveedorDto> listarProveedores() {
        List<ProveedorDto> proveedoresDto = new ArrayList<>();
        List<Proveedor> proveedores = provRep.findAll();
        
        for (Proveedor prov : proveedores) {
            ProveedorDto proveedorDto = new ProveedorDto();
            proveedorDto.setNombreEmpresa(prov.getNombreEmpresa());
            proveedorDto.setTipoServicio(prov.getTipoServicio());
            proveedorDto.setCalificacion(prov.getCalificacion());
            
            proveedoresDto.add(proveedorDto);
        }
        
        return proveedoresDto;
    }
    
    @Transactional
    public void actualizar(String nombre, String apellido, String email, String telefono, String password, String nombreEmpresa, String tipoServicio) throws MiExcepcion {
        validar(nombre, apellido, email, telefono, password, nombreEmpresa, tipoServicio);
        
        Optional<Proveedor> optionalProveedor = provRep.findById(nombreEmpresa);
        if (optionalProveedor.isPresent()) {
            Proveedor prov = optionalProveedor.get();
            
            prov.setTipoServicio(tipoServicio);
            
            provRep.save(prov);
        } else {
            throw new MiExcepcion("Proveedor no encontrado");
        }
    }
    
    @Transactional
    public void eliminar(String nombreEmpresa) throws MiExcepcion {
        if (nombreEmpresa == null || nombreEmpresa.isEmpty()) {
            throw new MiExcepcion("Nombre de empresa no válido");
        }
        
        Optional<Proveedor> optionalProveedor = provRep.findById(nombreEmpresa);
        if (optionalProveedor.isPresent()) {
            provRep.deleteByNombreEmpresa(nombreEmpresa);
        } else {
            throw new MiExcepcion("Proveedor no encontrado");
        }
    }
    
    public Proveedor obtenerPorNombre(String nombreEmpresa) throws MiExcepcion {
        if (nombreEmpresa == null || nombreEmpresa.isEmpty()) {
            throw new MiExcepcion("Nombre de empresa no válido");
        }
        
        Optional<Proveedor> optionalProveedor = provRep.findById(nombreEmpresa);
        if (optionalProveedor.isPresent()) {
            return optionalProveedor.get();
        } else {
            throw new MiExcepcion("Proveedor no encontrado");
        }
    }
    
    private void validar(String nombre, String apellido, String email, String telefono, String password, String nombreEmpresa, String tipoServicio) throws MiExcepcion {
        
        if (nombre.isEmpty() || nombre == null) {
            throw new MiExcepcion("el nombre no puede ser nulo o estar vacío");
        }
        if (apellido.isEmpty() || apellido == null) {
            throw new MiExcepcion("el apellido no puede ser nulo o estar vacío");
        }
        
        if (email.isEmpty() || email == null) {
            throw new MiExcepcion("el email no puede ser nulo o estar vacio");
        }
        
        if (telefono.isEmpty()) {
            throw new MiExcepcion("el telefono no puede estar vacio");
        }
        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new MiExcepcion("La contraseña no puede estar vacía, y debe tener más de 5 dígitos");
        }
        if (nombreEmpresa.isEmpty() || nombreEmpresa == null) {
            throw new MiExcepcion("el nombre de empresa no puede ser nulo o estar vacío");
        }
        if (tipoServicio.isEmpty() || tipoServicio == null) {
            throw new MiExcepcion("el tipo de servicio no puede ser nulo o estar vacio");
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
