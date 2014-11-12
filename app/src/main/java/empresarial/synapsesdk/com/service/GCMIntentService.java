package empresarial.synapsesdk.com.service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


import com.google.android.gms.gcm.GoogleCloudMessaging;

import empresarial.synapsesdk.com.Config;
import empresarial.synapsesdk.com.activity.DescriptionActivity;
import empresarial.synapsesdk.com.activity.GalleryActivity;
import empresarial.synapsesdk.com.activity.PlanActivity;
import empresarial.synapsesdk.com.activity.ProgressActivity;
import empresarial.synapsesdk.com.activity.ProjectActivity;
import empresarial.synapsesdk.com.activity.R;
import empresarial.synapsesdk.com.model.NotificationModel;
import empresarial.synapsesdk.com.model.Screen;

/**
 * Created by Garyfimo on 9/23/14.
 */
public class GCMIntentService extends IntentService {

    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
    public static final String TAG = "GCM Demo";

    public GCMIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                sendNotification("Send error: " + extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
                sendNotification("Deleted messages on server: " + extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                /*for (int i = 0; i < 5; i++) {
                    Log.i(TAG, "Working... " + (i + 1)
                            + "/5 @ " + SystemClock.elapsedRealtime());
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                    }
                }*/
                sendNotification(parseBundle(extras));
            }
            GCMBroadcastReceiver.completeWakefulIntent(intent);
        }
    }

    private NotificationModel parseBundle(Bundle extras) {
        NotificationModel model = new NotificationModel();
        model.mensaje = extras.getString("Mensaje");
        model.idProyecto = extras.getString("IdProyecto");
        model.pantalla = extras.getString("Pantalla");
        model.url = extras.getString("Url");
        model.usuario = extras.getString("Usuario");
        return model;
    }

    private void sendNotification(String msg) {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, ProjectActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.common_signin_btn_icon_dark)
                        .setContentTitle("GCM Notification")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(msg))
                        .setContentText(msg);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

    private void sendNotification(NotificationModel model) {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent();
        if(model.idProyecto != null && !model.idProyecto.equals("0"))
            intent.putExtra("id", model.idProyecto);
        switch (Screen.toEnum(model.pantalla)) {
            case PROYECTOS:
                intent.setClass(this, ProjectActivity.class);
                break;
            case DETALLE:
                intent.setClass(this, DescriptionActivity.class);
                break;
            case UBICACION:
                intent.setClass(this, ProjectActivity.class);
                break;
            case GALERIA:
                intent.setClass(this, GalleryActivity.class);
                break;
            case PLAN:
                intent.setClass(this, PlanActivity.class);
                break;
            case RECURSOS:
                intent.setClass(this, ProjectActivity.class);
                break;
            case AVANCE:
                intent.setClass(this, ProgressActivity.class);
                break;
            case PABELLONES:
                intent.setClass(this, ProjectActivity.class);
                break;
            default:
                intent.setClass(this, ProjectActivity.class);
                break;
        }

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setAutoCancel(true)
                        .setContentTitle(Config.APP_NAME)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(model.mensaje))
                        .setContentText(model.mensaje);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}
