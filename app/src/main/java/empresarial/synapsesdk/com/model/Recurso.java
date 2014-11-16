package empresarial.synapsesdk.com.model;

/**
 * Created by Garyfimo on 11/16/14.
 */
public class Recurso {
    private String descripcion;
    private String urlImagen;
    private String cantidad;
    private int numImagenes;

    public Recurso() {
    }

    public Recurso(String descripcion, String urlImagen, String cantidad, int numImagenes) {
        this.descripcion = descripcion;
        this.urlImagen = urlImagen;
        this.cantidad = cantidad;
        this.numImagenes = numImagenes;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public int getNumImagenes() {
        return numImagenes;
    }

    public void setNumImagenes(int numImagenes) {
        this.numImagenes = numImagenes;
    }
}
