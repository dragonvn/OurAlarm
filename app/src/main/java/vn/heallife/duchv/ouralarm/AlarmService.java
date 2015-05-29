package vn.heallife.duchv.ouralarm;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * Created by AnhLH on 5/19/15.
 */
public class AlarmService extends IntentService {


    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public AlarmService() {
        super("Alarm Service");
    }
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder mBuilder;

    @Override
    protected void onHandleIntent(Intent intent) {
//        intent
        Log.d("Notifi","Notifi apear");
        sendNotification(intent.getStringExtra("message"));

    }
    private void sendNotification(String msg){
        mNotificationManager = (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,new Intent(this,MainActivity.class),0);
        mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("Notification Alarm")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .setContentText(msg);
        mBuilder.setContentIntent(pendingIntent);
        mNotificationManager.notify(1,mBuilder.build());
    }
}
