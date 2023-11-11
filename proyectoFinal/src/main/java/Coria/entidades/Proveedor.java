/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Coria.Entidades;

import Coria.entidades.Usuario;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;



@Entity
public class Proveedor extends Usuario implements Serializable{

   @Id  
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String idProveedor;
    private String nombreEmpresa;
    private String tipoServicio;
    private String calificacion;

    public Proveedor() {
    }

    public Proveedor(String idProveedor, String nombreEmpresa, String tipoServicio, String calificacion) {
        this.idProveedor = idProveedor;
        this.nombreEmpresa = nombreEmpresa;
        this.tipoServicio = tipoServicio;
        this.calificacion = calificacion;
    }

    public String getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(String idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
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


}
