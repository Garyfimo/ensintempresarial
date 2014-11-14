package empresarial.synapsesdk.com.model;

/**
 * Created by Garyfimo on 11/11/14.
 */
public class Dispositivo implements IMapeable{

    private int idDispositivo;
    private String ip;
    private String codigoInventario;
    private String nombre;
    private String tipo;

    public Dispositivo() {
    }

    public Dispositivo(int idDispositivo, String ip, String codigoInventario, String nombre, String tipo) {
        this.idDispositivo = idDispositivo;
        this.ip = ip;
        this.codigoInventario = codigoInventario;
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public int getIdDispositivo() {
        return idDispositivo;
    }

    public void setIdDispositivo(int idDispositivo) {
        this.idDispositivo = idDispositivo;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCodigoInventario() {
        return codigoInventario;
    }

    public void setCodigoInventario(String codigoInventario) {
        this.codigoInventario = codigoInventario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
