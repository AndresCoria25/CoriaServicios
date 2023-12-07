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
import org.springframework.stereotype.Repository;

@Repository
public interface TrabajoRepositorio extends JpaRepository<Trabajo, String> {

    List<Trabajo> findByUsuarioId(String idUsuario);

    List<Trabajo> findByProveedorId(String idProveedor);

    List<Trabajo> findByEstado(String estado);

    Optional<Trabajo> findById(String idTrabajo);

    
    List<Trabajo> findByProveedorIdAndEstado(String proveedorId, String estado);
}
