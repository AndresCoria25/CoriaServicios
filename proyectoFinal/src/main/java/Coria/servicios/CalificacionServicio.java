package Coria.servicios;

import Coria.entidades.Calificacion;
import Coria.excepciones.MiExcepcion;
import Coria.repositorios.CalificacionRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CalificacionServicio {
    @Autowired
    CalificacionRepositorio caliRepo;
    
    @Transactional
    public void crearCalificacion(String idCalificacion, String idUsuario, String idProveedor, String idTrabajo, Integer calificacion, Date fechaBaja, String comentario) throws MiExcepcion {
        validar(calificacion, comentario);
        Calificacion cali = new Calificacion();

        cali.setIdUsuario(idUsuario);
        cali.setIdProveedor(idProveedor);
        cali.setIdTrabajo(idTrabajo);
        cali.setCalificacion(calificacion);
        cali.setComentario(comentario);
        caliRepo.save(cali);
    }
    
    public List<Calificacion> listarCalificaciones() {
        List<Calificacion> calificaciones = new ArrayList();
        calificaciones = caliRepo.findAll();
        return calificaciones;
    }

    public void modificarCalificacion(String idCalificacion, Integer calificacion, String comentario) throws MiExcepcion {
        validar(calificacion, comentario);
        Optional<Calificacion> respuesta = caliRepo.findById(idCalificacion);

        if (respuesta.isPresent()) {
            Calificacion cali = respuesta.get();
            cali.setCalificacion(calificacion);
            cali.setComentario(comentario);
            
            caliRepo.save(cali);
        }
    }
        
    public Calificacion getOne(String idCalificacion){
        return caliRepo.getOne(idCalificacion);
    }

    @Transactional
    public void bajaCalificacion(String idCalificacion) {
        Optional<Calificacion> respuesta = caliRepo.findById(idCalificacion);
        if (respuesta.isPresent()) {
            caliRepo.deleteById(idCalificacion);
        }
    }
    
    private void validar(Integer calificacion,String comentario) throws MiExcepcion{
        
        if(calificacion == null){
            throw new MiExcepcion("La calificación no puede estar vacio o nula");
        }
        if(comentario.isEmpty() || comentario == null){
            throw new MiExcepcion("El comentario no puede estar vacio");
        }
    }
    
}
