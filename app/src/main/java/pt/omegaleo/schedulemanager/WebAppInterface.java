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
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        notificationBuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);

        NotificationManagerCompat notificationManagerComp = NotificationManagerCompat.from(context);
        notificationManagerComp.notify(1, notificationBuilder.build());
    }
}
