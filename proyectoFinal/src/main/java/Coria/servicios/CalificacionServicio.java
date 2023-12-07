/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Coria.servicios;

import Coria.entidades.Calificacion;
import Coria.excepciones.MiException;
import Coria.repositorios.CalificacionRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalificacionServicio {

    @Autowired
    private CalificacionRepositorio caliRepo;

    @Transactional
    public void crearCalificacion(String idProveedor, String idUsuario, Date fechaBaja, String comentario, String calificacion) throws MiException {
        validar(idProveedor, idUsuario, fechaBaja, comentario, calificacion);
        Calificacion calif = new Calificacion();

        calif.setIdProveedor(idProveedor);
        calif.setIdUsuario(idUsuario);
        calif.setFechaBaja(fechaBaja);
        calif.setComentario(idUsuario);
        calif.setCalificacion(calificacion);
        caliRepo.save(calif);
    }

    public List<Calificacion> listarCalificaciones() {
        List<Calificacion> calificaciones = new ArrayList();
        calificaciones = caliRepo.findAll();
        return calificaciones;
    }

    public void modificarCalificacion(String idProveedor, String idUsuario, Date fechaBaja, String idCalificacion, String calificacion, String comentario) throws MiException {
        validar(idProveedor, idUsuario, fechaBaja, comentario, calificacion);
        Optional<Calificacion> respuesta = caliRepo.findById(idCalificacion);

        if (respuesta.isPresent()) {
            Calificacion cali = respuesta.get();
            cali.setCalificacion(calificacion); // Corregir este método
            cali.setComentario(comentario);

            caliRepo.save(cali);
        }
    }

    public Calificacion getOne(String idCalificacion) {
        return caliRepo.getOne(idCalificacion);
    }

    @Transactional
    public void bajaCalificacion(String idCalificacion) {
        Optional<Calificacion> respuesta = caliRepo.findById(idCalificacion);
        if (respuesta.isPresent()) {
            caliRepo.deleteById(idCalificacion);
        }
    }

    public void enviarCorreoAdministrador(String proveedor, String tipoTrabajo, String comentario, int calificacion) throws MiException {
        try {
            Calificacion nuevaCalificacion = new Calificacion();
            nuevaCalificacion.setIdCalificacion("correo-administrador@example.com");
            nuevaCalificacion.setCalificacion(comentario);
            nuevaCalificacion.setIdProveedor("Nueva Calificación recibida");
            nuevaCalificacion.setComentario("Se ha recibido una nueva calificación:\n\n"
                    + "Proveedor: " + proveedor + "\n"
                    + "Tipo de Trabajo: " + tipoTrabajo + "\n"
                    + "Comentario: " + comentario + "\n"
                    + "Calificación: " + calificacion);

            caliRepo.save(nuevaCalificacion); // Guardar la nueva calificación en la base de datos
        } catch (Exception e) {
            // Manejar la excepción en caso de error al enviar el correo
            e.printStackTrace();
        }
    }

    private void validar(String idProveedor, String idUsuario, Date fechaBaja, String comentario, String calificacion) throws MiException {

        if (idProveedor.isEmpty() || idProveedor == null) {
            throw new MiException("el id del proveedor no puede ser nulo o estar vacío");
        }
        if (idUsuario.isEmpty() || idUsuario == null) {
            throw new MiException("el id de usuario no puede ser nulo o estar vacío");
        }
        if (fechaBaja == null) {
            throw new MiException("la fecha de baja no puede estar vacío");
        }
        if (comentario.isEmpty() || comentario == null) {
            throw new MiException("el comentario no puede ser nulo o estar vacío");
        }
        if (calificacion == null) {
            throw new MiException("la calificacion no puede estar vacío");
        }
    }

}
