package empresarial.synapsesdk.com.model;

import java.util.ArrayList;
import java.util.List;

public class ScreenSharingBody {

    List<SSUser> usuarios;
    public String url;

    public ScreenSharingBody() {
        usuarios = new ArrayList<SSUser>();
    }

    public void addUser(SSUser user) {
        boolean add = true;
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).pos.equals(user.pos)) {
                usuarios.get(i).usuario = user.usuario;
                add = false;
                break;
            }
        }

        if(add){
            usuarios.add(user);
        }

    }

    public void check(String username){
        SSUser user = null;
        for (SSUser usuario : usuarios) {
            if(usuario.usuario.equals(username))
                user = usuario;
        }

        if(user != null)
            usuarios.remove(user);
    }

    public static class SSUser {
        public String usuario;
        public String pos;
    }
}
