package empresarial.synapsesdk.com.model;

import java.util.ArrayList;

public class Body {

    private ArrayList<String> tvs;
    private ArrayList<String> usuarios;
    private String pantalla;
    private String url;
    private String mensaje;
    private String usuario;
    private String idProyecto;

    public Body(ArrayList<String> tvs, ArrayList<String> usuarios, String pantalla, String url, String mensaje, String usuario, String idProyecto) {
        this.tvs = tvs;
        this.usuarios = usuarios;
        this.pantalla = pantalla;
        this.url = url;
        this.mensaje = mensaje;
        this.usuario = usuario;
        this.idProyecto = idProyecto;
    }

    public ArrayList<String> getTvs() {
        return tvs;
    }

    public void setTvs(ArrayList<String> tvs) {
        this.tvs = tvs;
    }

    public ArrayList<String> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(ArrayList<String> usuarios) {
        this.usuarios = usuarios;
    }

    public String getPantalla() {
        return pantalla;
    }

    public void setPantalla(String pantalla) {
        this.pantalla = pantalla;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(String idProyecto) {
        this.idProyecto = idProyecto;
    }
}
