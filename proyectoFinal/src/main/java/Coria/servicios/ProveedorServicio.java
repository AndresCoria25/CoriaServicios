/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Coria.servicios;

import Coria.entidades.Proveedor;
import Coria.excepciones.MiExcepcion;
import Coria.repositorios.ProveedorRepositorio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ProveedorServicio {

    @Autowired
    private ProveedorRepositorio provRep;

    @Transactional
    public void registrar(String nombreEmpresa, String tipoServicio, String calificacion) throws MiExcepcion {

        validar(nombreEmpresa, tipoServicio, calificacion);

        if (provRep.findById(nombreEmpresa) != null) {
            throw new MiExcepcion("el proveedor ya esta registrado");

        }

        Proveedor prov = new Proveedor();
        prov.setNombreEmpresa(nombreEmpresa);
        prov.setTipoServicio(tipoServicio);
        prov.setCalificacion(calificacion);
        provRep.save(prov);
    }
    
    @Transactional
    public void actualizar(String nombreEmpresa, String tipoServicio, String calificacion) throws MiExcepcion {

        validar(nombreEmpresa, tipoServicio, calificacion);

        Optional<Proveedor> optionalProveedor = provRep.findById(nombreEmpresa);
        if (optionalProveedor.isPresent()) {
            Proveedor prov = optionalProveedor.get();

            prov.setNombreEmpresa(nombreEmpresa);
            prov.setTipoServicio(tipoServicio);
            prov.setCalificacion(calificacion);
            provRep.save(prov);
        }
    }
    

    private void validar(String nombreEmpresa, String tipoServicio, String calificacion) throws MiExcepcion {

        if (nombreEmpresa.isEmpty() || nombreEmpresa == null) {
            throw new MiExcepcion("el nombre de empresa no puede ser nulo o estar vacío");
        }
        if (tipoServicio.isEmpty() || tipoServicio == null) {
            throw new MiExcepcion("el tipo de servicio no puede ser nulo o estar vacio");
        }

        if (calificacion.isEmpty() || calificacion == null) {
            throw new MiExcepcion("la calificacion no puede estar vacio");
        }
    }

}
