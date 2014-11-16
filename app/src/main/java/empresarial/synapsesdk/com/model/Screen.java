package empresarial.synapsesdk.com.model;

public enum Screen {
    PROYECTOS, DETALLE, UBICACION, GALERIA, PLAN, RECURSOS, INVERSION, PABELLONES;

    public static Screen toEnum(String name) {
        for(Screen comm : Screen.values()){
            if(name.equalsIgnoreCase(comm.toString()))
                return comm;
        }
        return PROYECTOS;
    }
}
