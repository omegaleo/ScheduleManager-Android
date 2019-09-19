package pt.omegaleo.schedulemanager;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import java.util.Calendar;
import java.util.Date;

public class SensorRestarterBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Date currentTime = Calendar.getInstance().getTime();

        Log.i(SensorRestarterBroadcastReceiver.class.getSimpleName(), currentTime + "ping");

        boolean isServiceRunning = false;
        if(intent!=null)
        {
            if(intent.getAction()!=null) {
                if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) {
                    ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
                    for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
                        if ("pt.omegaleo.scheduleManager.WebViewService".equals(service.service.getClassName())) {
                            isServiceRunning = true;
                        }
                    }
                }
            }
        }
        if (!isServiceRunning) {
            Intent i = new Intent(context, WebViewService.class);
            context.startForegroundService(i);



            Log.i(SensorRestarterBroadcastReceiver.class.getSimpleName(), currentTime + " - Service Stops! Oooooooooooooppppssssss!!!!");
        }
        //context.startForegroundService(new Intent(context, WebViewService.class));
    }
}
