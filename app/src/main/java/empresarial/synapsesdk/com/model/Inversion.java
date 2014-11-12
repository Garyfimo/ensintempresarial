package empresarial.synapsesdk.com.model;

/**
 * Created by Garyfimo on 11/11/14.
 */
public class Inversion {
    private int idInversion;
    private String totalDestinado;
    private String totalUsado;
    private String nombre;

    public Inversion() {
    }

    public Inversion(int idInversion, String totalDestinado, String totalUsado, String nombre) {
        this.idInversion = idInversion;
        this.totalDestinado = totalDestinado;
        this.totalUsado = totalUsado;
        this.nombre = nombre;
    }

    public int getIdInversion() {
        return idInversion;
    }

    public void setIdInversion(int idInversion) {
        this.idInversion = idInversion;
    }

    public String getTotalDestinado() {
        return totalDestinado;
    }

    public void setTotalDestinado(String totalDestinado) {
        this.totalDestinado = totalDestinado;
    }

    public String getTotalUsado() {
        return totalUsado;
    }

    public void setTotalUsado(String totalUsado) {
        this.totalUsado = totalUsado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
