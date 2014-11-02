package empresarial.synapsesdk.com.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by Garyfimo on 10/30/14.
 */
public class AlertDialogs {
    public static void mostrarErrorConexion(Context context) {
        AlertDialog alertDialog = AlertDialogs
                .crearMensajeError(
                        context,
                        "Error de conexión",
                        "Ocurrió un error al tratar de conectar al servidor, verifique que cuenta con conexión a internet.",
                        "Aceptar").create();
        alertDialog.show();
    }

    public static AlertDialog.Builder crearMensajeError(Context context,
                                                        String titulo, String mensaje, String botonCancelar) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle(titulo);
        alert.setMessage(mensaje);
        alert.setCancelable(false);
        alert.setNeutralButton(botonCancelar,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        return alert;
    }

    public static AlertDialog.Builder crearMensajeListaVacia(
            final Context context, String titulo, String mensaje) {
        AlertDialog.Builder alert = AlertDialogs.crearMensaje(context, titulo,
                mensaje);

        alert.setNeutralButton("Regresar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        ((Activity) context).finish();
                    }
                });
        return alert;
    }

    public static AlertDialog.Builder crearMensaje(Context context,
                                                   String titulo, String mensaje) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle(titulo);
        alert.setMessage(mensaje);
        alert.setCancelable(false);
        return alert;
    }
}
