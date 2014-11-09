package empresarial.synapsesdk.com.model;

public enum Screen {
    PROYECTOS, DETALLE, UBICACION, GALERIA, PLAN, RECURSOS, AVANCE, PABELLONES;

    public static Screen toEnum(String name) {
        for(Screen comm : Screen.values()){
            if(name.equalsIgnoreCase(comm.toString()))
                return comm;
        }
        return PROYECTOS;
    }
}
