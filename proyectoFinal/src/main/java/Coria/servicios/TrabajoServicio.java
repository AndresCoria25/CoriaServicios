/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Coria.servicios;

import Coria.entidades.Trabajo;
import Coria.excepciones.MiException;
import Coria.repositorios.TrabajoRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class TrabajoServicio {

    @Autowired
    private TrabajoRepositorio traRep;

    @Transactional
    public void crearTrabajo(String idTrabajo, String estado, String tipo, Integer duracion, Double presupuesto) throws MiException {
        validar(idTrabajo, estado, tipo, duracion, presupuesto);
        Trabajo trab = new Trabajo();

        trab.setIdTrabajo(idTrabajo);
        trab.setEstado(estado);
        trab.setTipo(tipo);
        trab.setDuracion(duracion);
        trab.setPresupuesto(presupuesto);
        traRep.save(trab);
    }

   @Transactional
public void modificarTrabajo(String idTrabajo, String estado, String tipo, Integer duracion, Double presupuesto) throws MiException {
    validar(idTrabajo, estado, tipo, duracion, presupuesto);

    Optional<Trabajo> respuesta = traRep.findById(idTrabajo);
    if (respuesta.isPresent()) {
        Trabajo tra = respuesta.get();
        tra.setDuracion(duracion);
        tra.setEstado(estado);
        tra.setPresupuesto(presupuesto);
        tra.setTipo(tipo);
        traRep.save(tra);
    } else {
        throw new MiException("El trabajo no existe");
    }
}

public Trabajo getOne(String id) {
        return traRep.getOne(id);
    }

 @Transactional(readOnly = true)
    public List<Trabajo> listarTrabajo(@RequestParam(required = false) String idTrabajo) {

        List<Trabajo> trabajos = traRep.buscarPorTrabajo(idTrabajo);
        
        if (idTrabajo != null && !idTrabajo.isEmpty()) {
        // Realiza la búsqueda basada en el término de búsqueda
        return traRep.buscarPorTrabajo(idTrabajo);
    } else {
        // Si el término de búsqueda está vacío, devuelve todos los trabajos
        return traRep.findAll();
    }
      
    }

    private void validar(String idTrabajo, String estado, String tipo, Integer duracion, Double presupuesto) throws MiException {

        if (idTrabajo.isEmpty() || idTrabajo == null) {
            throw new MiException("el id del trabajo no puede ser nulo o estar vacío");
        }
        if (estado.isEmpty() || estado == null) {
            throw new MiException("el estado no puede ser nulo o estar vacío");
        }
        if (tipo.isEmpty() || tipo == null) {
            throw new MiException("el tipo de trabajo no puede ser nulo o estar vacío");
        }
        if (duracion == null) {
            throw new MiException("la duración no puede estar vacía");
        }

        if (presupuesto == null) {
            throw new MiException("el presupuesto no puede estar vacío");
        }

    }

}
