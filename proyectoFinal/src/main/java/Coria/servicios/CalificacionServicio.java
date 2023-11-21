/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Coria.servicios;

import Coria.entidades.Calificacion;
import Coria.excepciones.MiException;
import Coria.repositorios.CalificacionRepositorio;
import java.util.Date;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalificacionServicio {

    @Autowired
    private CalificacionRepositorio calRep;

    @Transactional
    public void crearCalificacion(String idProveedor, String idUsuario, Date fechaBaja, String comentario, Integer calificacion) throws MiException {
        validar(idProveedor, idUsuario, fechaBaja, comentario, calificacion);
        Calificacion calif = new Calificacion();

        calif.setIdProveedor(idProveedor);
        calif.setIdUsuario(idUsuario);
        calif.setFechaBaja(fechaBaja);
        calif.setComentario(idUsuario);
        calif.setCalificacion(calificacion);
        calRep.save(calif);
    }

    private void validar(String idProveedor, String idUsuario, Date fechaBaja, String comentario, Integer calificacion) throws MiException {

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
