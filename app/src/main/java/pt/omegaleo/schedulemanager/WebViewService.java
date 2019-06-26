package pt.omegaleo.schedulemanager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class WebViewService extends Service {

    private WebView mWebView;
    private WindowManager.LayoutParams params;
    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        params = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 0;
        params.y = 0;
        params.width = 0;
        params.height = 0;

        LinearLayout view = new LinearLayout(this);
        view.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));


        mWebView = new WebView(this);

        mWebView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        mWebView.setWebViewClient(new WebViewClient());
        
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDisplayZoomControls(false);
        mWebView.setWebChromeClient(new WebChromeClient());

        mWebView.addJavascriptInterface(new WebAppInterface(MainActivity.context),"Android");

        mWebView.loadUrl("https://schedule-manager.omegaleo.pt");

        NotificationChannel channel = new NotificationChannel("default",
                "ScheduleManager",
                NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription("Notification channel for ScheduleManager");

        NotificationChannel channel2 = new NotificationChannel("maxPriority",
                "ScheduleManager",
                NotificationManager.IMPORTANCE_DEFAULT);
        channel2.setDescription("Channel for the Max Priority Notifications for ScheduleManager");

        NotificationManager notificationManager =
                (NotificationManager) MainActivity.context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.createNotificationChannel(channel);
        notificationManager.createNotificationChannel(channel2);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.context,"maxPriority")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setOngoing(true)
                        .setContentTitle("Schedule Manager")
                        .setContentText("Schedule Manager is running in background!")
                        .setPriority(NotificationCompat.PRIORITY_MAX);

        builder.setDefaults(Notification.FLAG_NO_CLEAR);

        NotificationManagerCompat notificationManagerComp = NotificationManagerCompat.from(MainActivity.context);
        notificationManagerComp.notify(1, builder.build());
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }
}
