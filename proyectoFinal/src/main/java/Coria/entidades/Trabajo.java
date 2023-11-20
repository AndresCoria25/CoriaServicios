package Coria.entidades;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Trabajo {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String idTrabajo;
    private String estado;
    private String tipo;
    private Integer duracion;
    private Double presupuesto;

    public Trabajo() {
    }

    public Trabajo(String idTrabajo, String estado, String tipo, Integer duracion, Double presupuesto) {
        this.idTrabajo = idTrabajo;
        this.estado = estado;
        this.tipo = tipo;
        this.duracion = duracion;
        this.presupuesto = presupuesto;
    }

    public String getIdTrabajo() {
        return idTrabajo;
    }

    public void setIdTrabajo(String idTrabajo) {
        this.idTrabajo = idTrabajo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public Double getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(Double presupuesto) {
        this.presupuesto = presupuesto;
    }

    
}
