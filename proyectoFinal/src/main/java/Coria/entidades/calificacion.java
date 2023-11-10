/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Coria.entidades;

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Usuario
 */
public class calificacion extends Usuario {
    
    @Id
    @GeneratedValue(generator = "uuid")//el id se hace solo
    @GenericGenerator(name = "uuid", strategy = "uuid2")//ningun id se repite
    private Usuario proveedor;
    
    private Integer calificacion;
    @Temporal(TemporalType.DATE)
    private Date fechaBaja;
    private String comentario;

    public calificacion() {
    }

    public Usuario getProveedor() {
        return proveedor;
    }

    public void setProveedor(Usuario proveedor) {
        this.proveedor = proveedor;
    }

    public Integer getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    
    
}
