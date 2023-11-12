/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Coria.repositorios;

import Coria.entidades.Calificacion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CalificacionRepositorio extends JpaRepository<Calificacion, String>{
    
  @Query("SELECT c FROM Calificacion c WHERE c.idProveedor = :nombre")
    public List<Calificacion> buscarPorProveedor(@Param("nombre") String idProveedor);  
}