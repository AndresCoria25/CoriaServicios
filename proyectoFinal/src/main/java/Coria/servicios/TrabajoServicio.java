package Coria.servicios;

import Coria.entidades.Trabajo;
import Coria.entidades.Usuario;
import Coria.excepciones.MiExcepcion;
import Coria.repositorios.TrabajoRepositorio;
import Coria.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TrabajoServicio {
    
    @Autowired
    TrabajoRepositorio trabRepo;
    
    @Transactional
    public void crearTrabajo(String tipo, String estado, Integer duracion, Double presupuesto) throws MiExcepcion {
        validar(tipo,estado,duracion,presupuesto);
        Trabajo trabajo = new Trabajo();
        trabajo.setTipo(tipo);
        trabajo.setEstado(estado);
        trabajo.setDuracion(duracion);
        trabajo.setPresupuesto(presupuesto);
        trabRepo.save(trabajo);
    }

    @Transactional(readOnly = true)
    public List<Trabajo> listarTrabajos() {
        List<Trabajo> trabajos = new ArrayList();
        trabajos = trabRepo.findAll();
        return trabajos;
    }

    @Transactional
    public void modificarTrabajo(String idTrabajo, String tipo, String estado, Integer duracion, Double presupuesto) throws MiExcepcion {
        validar(tipo,estado,duracion,presupuesto);
        Optional<Trabajo> respuesta = trabRepo.findById(idTrabajo);

        if (respuesta.isPresent()) {
            Trabajo trabajo = respuesta.get();
            
            trabajo.setTipo(tipo);
            trabajo.setEstado(estado);
            trabajo.setDuracion(duracion);
            trabajo.setPresupuesto(presupuesto);
            
            trabRepo.save(trabajo);
        }
    }
        
    public Trabajo getOne(String idTrabajo){
        return trabRepo.getOne(idTrabajo);
    }

    @Transactional
    public void bajaTrabajo(String idTrabajo) {
        Optional<Trabajo> respuesta = trabRepo.findById(idTrabajo);
        if (respuesta.isPresent()) {
            trabRepo.deleteById(idTrabajo);
        }
    }
    
    
    private void validar(String tipo, String estado, Integer duracion, Double presupuesto) throws MiExcepcion{
        
        if(tipo.isEmpty() || tipo == null){
            throw new MiExcepcion("El tipo de trabajo no puede estar vacio o nulo");
        }
        if(estado.isEmpty() || estado == null){
            throw new MiExcepcion("El estado del trabajo no puede estar vacio o nulo");
        }
        if(duracion<=0 || duracion == null){
            throw new MiExcepcion("La duración del trabajo no puede estar vacio o menor que cero");
        }
        if(presupuesto<0 || presupuesto == null){
            throw new MiExcepcion("El presupuesto del trabajo no puede estar vacio o menor a cero");
        }
        
    }    
    

}
