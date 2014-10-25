package empresarial.synapsesdk.com.model;

/**
 * Created by Garyfimo on 10/19/14.
 */
public class Project {

    private int idProyecto;
    private String nombre;
    private String descripcion;
    private String descripcionPabellones;
    private String descripcionAcabados;
    private String descripcionServicios;
    private String imagenComplejoURL;
    private String imagenGoogleMaps;
    private String fechaInicio;
    private String fechaFin;
    private String presupuestoTotal;
    private String[] indicadores;

    public Project() {
    }

    public Project(int idProyecto, String nombre, String descripcion, String descripcionPabellones, String descripcionAcabados, String descripcionServicios, String imagenComplejoURL, String imagenGoogleMaps, String fechaInicio, String fechaFin, String presupuestoTotal, String[] indicadores) {
        this.idProyecto = idProyecto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.descripcionPabellones = descripcionPabellones;
        this.descripcionAcabados = descripcionAcabados;
        this.descripcionServicios = descripcionServicios;
        this.imagenComplejoURL = imagenComplejoURL;
        this.imagenGoogleMaps = imagenGoogleMaps;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.presupuestoTotal = presupuestoTotal;
        this.indicadores = indicadores;
    }


    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcionPabellones() {
        return descripcionPabellones;
    }

    public void setDescripcionPabellones(String descripcionPabellones) {
        this.descripcionPabellones = descripcionPabellones;
    }

    public String getDescripcionAcabados() {
        return descripcionAcabados;
    }

    public void setDescripcionAcabados(String descripcionAcabados) {
        this.descripcionAcabados = descripcionAcabados;
    }

    public String getDescripcionServicios() {
        return descripcionServicios;
    }

    public void setDescripcionServicios(String descripcionServicios) {
        this.descripcionServicios = descripcionServicios;
    }

    public String getImagenComplejoURL() {
        return imagenComplejoURL;
    }

    public void setImagenComplejoURL(String imagenComplejoURL) {
        this.imagenComplejoURL = imagenComplejoURL;
    }

    public String getImagenGoogleMaps() {
        return imagenGoogleMaps;
    }

    public void setImagenGoogleMaps(String imagenGoogleMaps) {
        this.imagenGoogleMaps = imagenGoogleMaps;
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

    public String getPresupuestoTotal() {
        return presupuestoTotal;
    }

    public void setPresupuestoTotal(String presupuestoTotal) {
        this.presupuestoTotal = presupuestoTotal;
    }

    public String[] getIndicadores() {
        return indicadores;
    }

    public void setIndicadores(String[] indicadores) {
        this.indicadores = indicadores;
    }
}
