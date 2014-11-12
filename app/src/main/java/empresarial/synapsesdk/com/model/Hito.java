package empresarial.synapsesdk.com.model;

/**
 * Created by Garyfimo on 11/9/14.
 */
public class Hito {

    private int idHito;
    private String nombre;
    private String fecha;
    private String idFase;

    public Hito() {
    }

    public Hito(int idHito, String nombre, String fecha, String idFase) {
        this.idHito = idHito;
        this.nombre = nombre;
        this.fecha = fecha;
        this.idFase = idFase;
    }

    @Override
    public String toString()
    {
        return new StringBuilder()
                .append(nombre)
                .append(" ").append(fecha)
                .toString();
    }

    public int getIdHito() {
        return idHito;
    }

    public void setIdHito(int idHito) {
        this.idHito = idHito;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getIdFase() {
        return idFase;
    }

    public void setIdFase(String idFase) {
        this.idFase = idFase;
    }
}
