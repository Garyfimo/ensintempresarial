package empresarial.synapsesdk.com.model;

import java.util.ArrayList;

/**
 * Created by Garyfimo on 11/9/14.
 */
public class Plan {

    private int idFase;
    private String nombre;
    private String descripcion;
    private String fechaInicio;
    private String fechaFin;
    private int porcAvance;
    private int idProyecto;
    private ArrayList<Hito> hitos;

    public Plan() {
    }

    public Plan(int idFase, String nombre, String descripcion, String fechaInicio, String fechaFin, int porcAvance, int idProyecto, ArrayList<Hito> hitos) {
        this.idFase = idFase;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.porcAvance = porcAvance;
        this.idProyecto = idProyecto;
        this.hitos = hitos;
    }

    public int getIdFase() {
        return idFase;
    }

    public void setIdFase(int idFase) {
        this.idFase = idFase;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getPorcAvance() {
        return porcAvance;
    }

    public void setPorcAvance(int porcAvance) {
        this.porcAvance = porcAvance;
    }

    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    public ArrayList<Hito> getHitos() {
        return hitos;
    }

    public void setHitos(ArrayList<Hito> hitos) {
        this.hitos = hitos;
    }
}
