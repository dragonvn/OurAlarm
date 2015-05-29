package vn.heallife.duchv.ouralarm;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import java.util.Calendar;

import vn.heallife.duchv.ouralarm.gcm.GcmIntentService;

/**
 * Created by Duc Hoang on 5/18/15.
 */
public class AlarmReceiver extends WakefulBroadcastReceiver {

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

//    private String msg = "No Messeage";
    @Override
    public void onReceive(Context context, Intent intent) {
        ComponentName componentName = new ComponentName(context.getPackageName(),AlarmService.class.getName());
        startWakefulService(context, intent.setComponent(componentName));
        Log.d("Notifi", "start notifi");
        setResultCode(Activity.RESULT_OK);

//        if (checkDb(id)) {
//            // do something
//        } else {
//            cancelAlarm();
//        }
    }

    public void setAlarm(int h, int m, String text,Context context){
        alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context,AlarmReceiver.class);
        intent.putExtra("message", ""+text);

        pendingIntent = PendingIntent.getBroadcast(context,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);

        //create Calender
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, h);
        calendar.set(Calendar.MINUTE,m);
        Log.d("Notifi","schedule Notifi");
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),1000*60*10,pendingIntent);
    }
    public void cancelAlarm(){
        if(alarmManager!= null){
            alarmManager.cancel(pendingIntent);
        }
    }
}
