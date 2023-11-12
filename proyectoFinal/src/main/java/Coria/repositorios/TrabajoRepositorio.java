/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Coria.repositorios;

import Coria.entidades.Trabajo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TrabajoRepositorio extends JpaRepository<Trabajo, String> {

//    @Query("SELECT t FROM Trabajo t WHERE t.idProveedor = :nombre")
//    List<Trabajo> buscarPorProveedor(@Param("nombre") String idProveedor);
//
//    @Query("SELECT t FROM Trabajo t WHERE t.idUsuario = :nombre")
//    List<Trabajo> buscarPorUsuario(@Param("nombre") String idUsuario);

    @Query("SELECT t FROM Trabajo t WHERE t.idTrabajo = :nombre")
    List<Trabajo> buscarPorTrabajo(@Param("nombre") String idTrabajo);

}
