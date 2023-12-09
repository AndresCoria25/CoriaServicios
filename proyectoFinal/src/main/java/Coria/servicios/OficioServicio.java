
package Coria.servicios;

import Coria.entidades.Oficio;
import Coria.excepciones.MiException;
import Coria.repositorios.OficioRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class OficioServicio {
    
    @Autowired
    private OficioRepositorio ofiRep;
    
    
    @Transactional
    public void crearOficio(String nombreOficio, String comentarioOficio) throws MiException {
        validar(nombreOficio, comentarioOficio);
        Oficio oficio = new Oficio();

        oficio.setNombreOficio(nombreOficio);
        oficio.setComentarioOficio(comentarioOficio);
        ofiRep.save(oficio);
    }

   @Transactional
    public void modificarOficio(String idOficio, String nombreOficio, String comentarioOficio) throws MiException {
        validar(nombreOficio, comentarioOficio);

        Optional<Oficio> respuesta = ofiRep.findById(idOficio);
        if (respuesta.isPresent()) {
            Oficio oficio = respuesta.get();
            oficio.setIdOficio(idOficio);
            oficio.setNombreOficio(nombreOficio);
            oficio.setComentarioOficio(comentarioOficio);
            ofiRep.save(oficio);
        } else {
            throw new MiException("El oficio no existe");
        }
    }
    
    @Transactional(readOnly = true)
    public List<Oficio> listarOficio() {
        List<Oficio> listaOficios = new ArrayList();
        listaOficios = ofiRep.findAll();
        return listaOficios;
    }
    
    public Oficio getOne(String idOficio) {
        return ofiRep.getOne(idOficio);
    }
    
    public void eliminarOficio(String idOficio) throws MiException {
        if (idOficio.isEmpty() || idOficio.equals("")) {
            throw new MiException("el id proporcionado es nulo");
        } else {
            Optional<Oficio> respuesta = ofiRep.findById(idOficio);
            if (respuesta.isPresent()) {
                Oficio oficio = respuesta.get();
                ofiRep.delete(oficio);
            }
        }
    }
    
    private void validar(String nombreOficio, String comentarioOficio) throws MiException {

        if (nombreOficio.isEmpty() || nombreOficio == null) {
            throw new MiException("el nombre del oficio no puede ser nulo o estar vacío");
        }
        if (comentarioOficio.isEmpty() || comentarioOficio == null) {
            throw new MiException("el comentario del oficio no puede ser nulo o estar vacío");
        }
        
    }
}
