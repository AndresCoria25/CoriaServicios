/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Coria.repositorios;

import Coria.entidades.Proveedor;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepositorio extends JpaRepository<Proveedor, String> {

    @Query("SELECT p FROM Proveedor p WHERE p.email = :email")
    Proveedor buscarPorEmail(@Param("email") String email);

    Proveedor findByNombreEmpresa(String nombreEmpresa);

    void deleteByNombreEmpresa(String nombreEmpresa);

    List<Proveedor> findByTipoServicio(String tipoServicio);

}
