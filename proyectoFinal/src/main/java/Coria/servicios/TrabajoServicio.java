/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Coria.servicios;

import Coria.entidades.Trabajo;
import Coria.excepciones.MiExcepcion;
import Coria.repositorios.TrabajoRepositorio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TrabajoServicio {

    @Autowired
    private TrabajoRepositorio traRep;

    @Transactional
    public void crearTrabajo(String idTrabajo, String estado, String tipo, Integer duracion, Double presupuesto) throws MiExcepcion {
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
public void modificarTrabajo(String idTrabajo, String estado, String tipo, Integer duracion, Double presupuesto) throws MiExcepcion {
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
        throw new MiExcepcion("El trabajo no existe");
    }
}

    private void validar(String idTrabajo, String estado, String tipo, Integer duracion, Double presupuesto) throws MiExcepcion {

        if (idTrabajo.isEmpty() || idTrabajo == null) {
            throw new MiExcepcion("el id del trabajo no puede ser nulo o estar vacío");
        }
        if (estado.isEmpty() || estado == null) {
            throw new MiExcepcion("el estado no puede ser nulo o estar vacío");
        }
        if (tipo.isEmpty() || tipo == null) {
            throw new MiExcepcion("el tipo de trabajo no puede ser nulo o estar vacío");
        }
        if (duracion == null) {
            throw new MiExcepcion("la duración no puede estar vacía");
        }

        if (presupuesto == null) {
            throw new MiExcepcion("el presupuesto no puede estar vacío");
        }

    }

}
