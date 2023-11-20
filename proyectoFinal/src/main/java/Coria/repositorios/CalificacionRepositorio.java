package Coria.repositorios;

import Coria.entidades.Calificacion;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CalificacionRepositorio extends JpaRepository<Calificacion, String> {

    @Query("SELECT c FROM Calificacion c WHERE c.idCalificacion = :idCalificacion")
    public Optional<Calificacion> findById(@Param("idCalificacion") String idCalificacion); 
    
    @Query("SELECT c FROM Calificacion c WHERE c.idUsuario = :idUsuario")
    public Optional<Calificacion> buscarPorUsuario(@Param("idUsuario") String idUsuario); 
    
    @Query("SELECT c FROM Calificacion c WHERE c.idProveedor = :idProveedor")
    public Optional<Calificacion> buscarPorProveedor(@Param("idProveedor") String idProveedor); 
    
    @Query("SELECT c FROM Calificacion c WHERE c.idTrabajo = :idTrabajo")
    public Optional<Calificacion> buscarPorTrabajo(@Param("idTrabajo") String idTrabajo); 
    
}
