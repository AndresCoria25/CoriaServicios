/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Coria.Servicios;

import Coria.Entidades.Proveedor;
import Coria.Repositorio.ProveedorRepositorio;
import Coria.excepciones.MiExcepcion;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProveedorServicio {
    
    @Autowired
    ProveedorRepositorio provRep;
    
    @Transactional
    public void crearProveedor(String nombreEmpresa) throws MiExcepcion {
        validar(nombreEmpresa);
        Proveedor proveedor = new Proveedor();

        proveedor.setNombreEmpresa(nombreEmpresa);

        provRep.save(proveedor);
    }

    public List<Proveedor> listarProveedor() {
        List<Proveedor> proveedor = new ArrayList();
        proveedor = provRep.findAll();

        return proveedor;
    }

    public void modificarProveedor(String id, String nombreEmpresa) throws MiExcepcion {
        validar(nombreEmpresa);
        Optional<Proveedor> respuesta = provRep.findById(id);

        if (respuesta.isPresent()) {
            Proveedor proveedor = respuesta.get();
            proveedor.setNombreEmpresa(nombreEmpresa);

            provRep.save(proveedor);
        }
    }
    
    public Proveedor getOne(String id){
        return provRep.getOne(id);
    }

    private void validar(String nombreEmpresa) throws MiExcepcion{
        
        if(nombreEmpresa.isEmpty() || nombreEmpresa == null){
            throw new MiExcepcion("El nombre del proveedor no puede estar vacio");
        }
    }
}

