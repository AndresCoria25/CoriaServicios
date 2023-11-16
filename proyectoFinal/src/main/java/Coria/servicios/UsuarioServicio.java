package Coria.servicios;

import Coria.entidades.Imagen;
import Coria.entidades.Usuario;
import Coria.enumeraciones.Rol;
import Coria.excepciones.MiExcepcion;
import Coria.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private ImagenServicio imagenServicio;

    @Transactional
    public void registrar(MultipartFile archivo, String nombre, String apellido, String email, String telefono, String password) throws MiExcepcion {

        validar(nombre, apellido, email, telefono, password);

        Usuario usuarioExistente = usuarioRepositorio.buscarPorEmail(email);
        if (usuarioExistente != null) {
            throw new MiExcepcion("El correo electrónico ya está en uso.");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setEmail(email);
        usuario.setTelefono(telefono);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        usuario.setRol(Rol.USER);
        Imagen imagen = imagenServicio.guardar(archivo);
        usuario.setImagen(imagen);
        usuarioRepositorio.save(usuario);
    }

    @Transactional
    public Usuario actualizar(MultipartFile archivo, String id, String nombre, String apellido, String email, String telefono, String password) throws MiExcepcion {
        validar(nombre, apellido, email, telefono, password);

        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();

            if (!usuario.getEmail().equals(email) && usuarioRepositorio.buscarPorEmail(email) != null) {
                throw new MiExcepcion("El email ya está en uso");
            }

            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuario.setEmail(email);
            usuario.setTelefono(telefono);
            usuario.setPassword(new BCryptPasswordEncoder().encode(password));
            usuario.setRol(usuario.getRol());

            String idImagen = null;

            if (usuario.getImagen() != null) {
                idImagen = usuario.getImagen().getId();
            }
            Imagen imagen = imagenServicio.actualizar(archivo, idImagen);
            usuario.setImagen(imagen);
            usuarioRepositorio.save(usuario);

            return usuarioRepositorio.save(usuario);
        } else {
            throw new MiExcepcion("Usuario no encontrado"); // Manejo de caso donde el usuario no se encuentra
        }
    }

    @Transactional
    public Usuario modificarUsuario(String id, String nombre, String apellido) throws MiExcepcion {

        if (nombre.isEmpty() || nombre == null) {
            throw new MiExcepcion("el nombre no puede ser nulo o estar vacío");
        }
        if (apellido.isEmpty() || apellido == null) {
            throw new MiExcepcion("el apellido no puede ser nulo o estar vacío");
        }

        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();

            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            
            usuarioRepositorio.save(usuario);
            return usuario; // Devuelve el usuario modificado
        } else {
            throw new MiExcepcion("Usuario no encontrado");
        }
    }

    public Usuario getOne(String id) {
        return usuarioRepositorio.getOne(id);
    }

    @Transactional(readOnly = true)
    public List<Usuario> listarUsuarios() {

        List<Usuario> usuarios = new ArrayList();
        usuarios = usuarioRepositorio.findAll();
        return usuarios;
    }

    @Transactional
    public void cambiarRol(String id) {
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            if (usuario.getRol().equals(Rol.USER)) {
                usuario.setRol(Rol.ADMIN);
            } else if (usuario.getRol().equals(Rol.ADMIN)) {
                usuario.setRol(Rol.USER);
            } else if (usuario.getRol().equals(Rol.PROVEEDOR)) {
                usuario.setRol(Rol.USER);
            }
        }
    }

    public void darDeBajaAdmin(String Id, String motivo) {
        Usuario admin = usuarioRepositorio.findById(Id)
                .orElseThrow(() -> new RuntimeException("No se ha encontrado al administrador con ID: " + Id));

        if (admin.getRol().equals(Rol.ADMIN)) {

            admin.setFechaBaja(new Date());

            admin.setMotivoBaja(motivo);

            usuarioRepositorio.save(admin);
        } else {
            // El usuario no es un administrador, lanzar una excepción
            throw new RuntimeException("El usuario no es un administrador");
        }
    }

    public void eliminarUsuario(String id) throws MiExcepcion {
        if (id.isEmpty() || id.equals("")) {
            throw new MiExcepcion("el id proporcionado es nulo");
        } else {
            Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
            if (respuesta.isPresent()) {
                Usuario usuario = respuesta.get();
                usuarioRepositorio.delete(usuario);
            }
        }
    }

    private void validar(String nombre, String apellido, String email, String telefono, String password) throws MiExcepcion {

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

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepositorio.buscarPorEmail(email);

        if (usuario != null) {

            List<GrantedAuthority> permisos = new ArrayList();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());
            permisos.add(p);
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", usuario);

            return new User(usuario.getEmail(), usuario.getPassword(), permisos);
        } else {
            return null;
        }

    }

}
