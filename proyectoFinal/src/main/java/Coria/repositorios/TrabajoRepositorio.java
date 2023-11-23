/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Coria.repositorios;

import Coria.entidades.Trabajo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TrabajoRepositorio extends JpaRepository<Trabajo, String> {

    //List<Trabajo> buscarPorIdProveedor(String idProveedor);
    
    @Query("SELECT t FROM Trabajo t WHERE t.idTrabajo = :idTrabajo")
    public Optional<Trabajo> buscarPorIdTrabajo(@Param("idTrabajo") String idTrabajo);

    @Query("SELECT t FROM Trabajo t WHERE t.idUsuario = :idUsuario")
    public List<Trabajo> buscarPorIdUsuario(@Param("idUsuario") String idUsuario);

    @Query("SELECT t FROM Trabajo t WHERE t.idProveedor = :idProveedor")
    public List<Trabajo> buscarPorIdProveedor(@Param("idProveedor") String idProveedor);

    @Query("SELECT t FROM Trabajo t WHERE t.tipo = :tipo")
    public List<Trabajo> buscarPorTipo(@Param("tipo") String tipo);

    @Query("SELECT t FROM Trabajo t WHERE t.estado = :estado")
    public List<Trabajo> buscarPorEstado(@Param("estado") String estado);

//
//    @Query("SELECT t FROM Trabajo t WHERE t.idTrabajo = :nombre")
//    public Optional<Trabajo> buscarPorIdTrabajo(@Param("nombre") String idTrabajo);
//
//    @Query("SELECT t FROM Trabajo t WHERE t.idUsuario = :nombre")
//    public List<Trabajo> buscarPorIdUsuario(@Param("nombre") String idUsuario);
//
//    @Query("SELECT t FROM Trabajo t WHERE t.idProveedor = :nombre")
//    public Optional<Trabajo> buscarPorIdProveedor(@Param("nombre") String idProveedor);
//
//    @Query("SELECT t FROM Trabajo t WHERE t.tipo = :tipo")
//    public List<Trabajo> buscarPorTipo(@Param("tipo") String tipo);
//
//    @Query("SELECT t FROM Trabajo t WHERE t.estado = :estado")
//    public List<Trabajo> buscarPorEstado(@Param("estado") String estado);
}
