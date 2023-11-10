/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Coria.Entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;





@Entity
public class Proveedor {

   @Id  // Usar javax.persistence.Id para la clave primaria
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String nombre;
    private String tipoServicio;
    private String calificacion;

    public Proveedor() {
    }

    public Proveedor(String id, String nombre, String tipoServicio, String calificacion) {
        this.id = id;
        this.nombre = nombre;
        this.tipoServicio = tipoServicio;
        this.calificacion = calificacion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public String getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(String calificacion) {
        this.calificacion = calificacion;
    }

    @Override
    public String toString() {
        return "Proveedor{" + "id=" + id + ", nombre=" + nombre + ", tipoServicio=" + tipoServicio + ", calificacion=" + calificacion + '}';
    }

}
