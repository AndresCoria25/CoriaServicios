/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Coria.entidades;

import javax.persistence.Entity;

@Entity
public class Proveedor extends Usuario {

    private String nombreEmpresa;
    private String tipoServicio;
    private String calificacion;

    public Proveedor() {
    }

    public Proveedor(String nombreEmpresa, String tipoServicio, String calificacion) {
        this.nombreEmpresa = nombreEmpresa;
        this.tipoServicio = tipoServicio;
        this.calificacion = calificacion;
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