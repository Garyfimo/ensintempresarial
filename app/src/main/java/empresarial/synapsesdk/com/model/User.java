package empresarial.synapsesdk.com.model;

/**
 * Created by Garyfimo on 10/16/14.
 */
public class User {
    private int idUsuario;
    private String username;
    private String nombre;

    public User() {

    }

    public User(int idUsuario, String username, String nombre) {
        this.idUsuario = idUsuario;
        this.username = username;
        this.nombre = nombre;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
