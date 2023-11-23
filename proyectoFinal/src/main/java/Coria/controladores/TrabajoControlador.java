/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Coria.controladores;

///// Franco
import Coria.entidades.Trabajo;
import Coria.repositorios.TrabajoRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

///// Desarrolladores
/**
 *
 * @author romi_
 */
///// Franco
@RestController
@RequestMapping("/trabajos")
public class TrabajoControlador {

    @Autowired
    private TrabajoRepositorio trabajoRepositorio;

    @GetMapping("/{idTrabajo}")
    public ResponseEntity<Trabajo> obtenerTrabajoPorId(@PathVariable String idTrabajo) {
        Optional<Trabajo> trabajo = trabajoRepositorio.buscarPorIdTrabajo(idTrabajo);
        return trabajo.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Trabajo>> obtenerTrabajosPorUsuario(@PathVariable String idUsuario) {
        List<Trabajo> trabajos = trabajoRepositorio.buscarPorIdUsuario(idUsuario);
        return new ResponseEntity<>(trabajos, HttpStatus.OK);
    }

    @GetMapping("/proveedor/{idProveedor}")
    public ResponseEntity<List<Trabajo>> obtenerTrabajoPorProveedor(@PathVariable String idProveedor) {
        List<Trabajo> trabajos = trabajoRepositorio.buscarPorIdProveedor(idProveedor);

        if (!trabajos.isEmpty()) {
            return new ResponseEntity<>(trabajos, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Trabajo>> obtenerTrabajosPorTipo(@PathVariable String tipo) {
        List<Trabajo> trabajos = trabajoRepositorio.buscarPorTipo(tipo);
        return new ResponseEntity<>(trabajos, HttpStatus.OK);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Trabajo>> obtenerTrabajosPorEstado(@PathVariable String estado) {
        List<Trabajo> trabajos = trabajoRepositorio.buscarPorEstado(estado);
        return new ResponseEntity<>(trabajos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Trabajo> crearTrabajo(@RequestBody Trabajo trabajo) {
        Trabajo nuevoTrabajo = trabajoRepositorio.save(trabajo);
        return new ResponseEntity<>(nuevoTrabajo, HttpStatus.CREATED);
    }
}
