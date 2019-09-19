package pt.omegaleo.schedulemanager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.webkit.JavascriptInterface;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Date;
import java.util.Random;

public class WebAppInterface {

    Context context;

    public WebAppInterface(Context context)
    {
        this.context = context;
    }

    @JavascriptInterface
    public void Notify(String msg)
    {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, "default")
                .setContentTitle("Schedule Manager")
                .setContentText(msg)
                .setSmallIcon(R.drawable.ic_notification)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_launcher))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        notificationBuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);

        int m =  (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);

        NotificationManagerCompat notificationManagerComp = NotificationManagerCompat.from(context);
        notificationManagerComp.notify(m, notificationBuilder.build());
    }
}
