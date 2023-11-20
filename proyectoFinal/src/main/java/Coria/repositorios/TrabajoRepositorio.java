package Coria.repositorios;

import Coria.entidades.Trabajo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface TrabajoRepositorio extends JpaRepository<Trabajo, String> {

    @Query("SELECT t FROM Trabajo t WHERE t.idTrabajo = :idTrabajo")
    public Optional<Trabajo> findById(@Param("idTrabajo") String idTrabajo); 
    
    @Query("SELECT t FROM Trabajo t WHERE t.idUsuario = :idUsuario")
    public Optional<Trabajo> buscarPorUsuario(@Param("idUsuario") String idUsuario); 
    
    @Query("SELECT t FROM Trabajo t WHERE t.idProveedor = :idProveedor")
    public Optional<Trabajo> buscarPorProveedor(@Param("idProveedor") String idProveedor); 
    
    @Query("SELECT t FROM Trabajo t WHERE t.tipo = :tipo")
    public Optional<Trabajo> buscarPorTipo(@Param("tipo") String tipo); 
    
    @Query("SELECT t FROM Trabajo t WHERE t.estado = :estado")
    public Optional<Trabajo> buscarPorEstado(@Param("estado") String estado); 
    
    
    
}