package pt.omegaleo.schedulemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.TypefaceCompat;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import java.io.File;
import java.io.FileWriter;

public class MainActivity extends AppCompatActivity {

    public static WebView mWebView;
    public static Context context;
    public static WebViewService webViewService;

    Intent service;

    SensorRestarterBroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;

        Thread.setDefaultUncaughtExceptionHandler(new CustomizedExceptionHandler("/mnt/sdcard/"));

        setContentView(R.layout.activity_main);

        mWebView = (WebView) findViewById(R.id.mWebView);
        context = this;

        webViewService = new WebViewService();
        service = new Intent(this,webViewService.getClass());

        //startService(service);

        if (!isMyServiceRunning(WebViewService.class)) {
            startForegroundService(service);
        }


        mWebView.setWebViewClient(new WebViewClient());

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDisplayZoomControls(false);
        mWebView.setWebChromeClient(new WebChromeClient());

        mWebView.addJavascriptInterface(new WebAppInterface(MainActivity.context),"Android");

        mWebView.loadUrl("https://schedule-manager.omegaleo.pt");

        Button btnRefresh = findViewById(R.id.btn_refresh);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.reload();
            }
        });


        IntentFilter filter = new IntentFilter(Intent.ACTION_TIME_TICK);
        receiver = new SensorRestarterBroadcastReceiver();
        registerReceiver(receiver,filter);

    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        stopService(service);
        super.onDestroy();
    }

    public Context getCtx() {
        return context;
    }

    @Override
    protected void onStop()
    {
        unregisterReceiver(receiver);
        super.onStop();
    }
}
