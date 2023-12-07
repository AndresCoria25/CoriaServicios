/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Coria.servicios;

import Coria.busqueda.ResultadoBusquedaDTO;
import Coria.entidades.Oficio;
import Coria.entidades.Proveedor;
import Coria.entidades.Usuario;
import Coria.repositorios.OficioRepositorio;
import Coria.repositorios.ProveedorRepositorio;
import Coria.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Usuario
 */
@Service
public class BusquedaServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private ProveedorRepositorio proveedorRepositorio;
    
    @Autowired
    private OficioRepositorio oficioRepositorio;

    public List<ResultadoBusquedaDTO> buscar(String terminoBusqueda) {
       List<Usuario> usuarios = usuarioRepositorio.findByNombreContainingOrEmailContaining(terminoBusqueda);
        List<Proveedor> proveedores = proveedorRepositorio.findByNombreEmpresaContainingOrTipoServicioContaining(terminoBusqueda);
        List<Oficio> oficios = oficioRepositorio.findByNombreOficioContainingOrIDOficioContaining(terminoBusqueda);

        List<ResultadoBusquedaDTO> resultados = new ArrayList<>();

        for (Usuario usuario : usuarios) {
            resultados.add(mapUsuarioAResultadoDTO(usuario));
        }

        for (Proveedor proveedor : proveedores) {
            resultados.add(mapProveedorAResultadoDTO(proveedor));
        }
        
        for (Oficio oficio : oficios){
            resultados.add(mapOficioAResultadoDTO(oficio));
        }

        return resultados;
    }

    private ResultadoBusquedaDTO mapUsuarioAResultadoDTO(Usuario usuario) {
        ResultadoBusquedaDTO dto = new ResultadoBusquedaDTO();
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setEmail(usuario.getEmail());
        dto.setTelefono(usuario.getTelefono());
        dto.setTipoEntidad("Usuario");

        return dto;
    }

    private ResultadoBusquedaDTO mapProveedorAResultadoDTO(Proveedor proveedor) {
        ResultadoBusquedaDTO dto = new ResultadoBusquedaDTO();
        dto.setId(proveedor.getId());
        dto.setNombre(proveedor.getNombre());
        dto.setEmail(proveedor.getEmail());
        dto.setTelefono(proveedor.getTelefono());
        dto.setTipoEntidad("Proveedor");
        dto.setNombreEmpresa(proveedor.getNombreEmpresa());
        dto.setCalificacionPromedio(proveedor.getCalificacionPromedio());
        dto.setNumeroCalificaciones(proveedor.getNumeroCalificaciones());

        return dto;
    }
    
    private ResultadoBusquedaDTO mapOficioAResultadoDTO (Oficio oficio){
        ResultadoBusquedaDTO dto = new ResultadoBusquedaDTO();
        dto.setIdOficio(oficio.getIdOficio());
        dto.setNombreOficio(oficio.getNombreOficio());
    
        
        
        return dto;
    }
    
    
}

