/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Coria.dto;

/**
 *
 * @author romi_
 */
public class ProveedorDto {
    private String nombreEmpresa;
    private String tipoServicio;
    private String calificacion;

    public ProveedorDto() {
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

    public ProveedorDto(String nombreEmpresa, String tipoServicio, String calificacion) {
        this.nombreEmpresa = nombreEmpresa;
        this.tipoServicio = tipoServicio;
        this.calificacion = calificacion;
    }
    
    
}
