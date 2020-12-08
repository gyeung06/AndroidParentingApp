package c.cmpt276.childapp.model.timerService;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.Vibrator;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Timer;
import java.util.TimerTask;

import c.cmpt276.childapp.R;
import c.cmpt276.childapp.TimeoutActivity;

/**
 * Functionality part of Timer
 */
public class TimerService extends Service {
    private static long mTimeLeftInMillis;
    private static Vibrator v;
    private static MediaPlayer mp;
    private static long speedMod = 3;
    private final int  COUNTDOWN_INTERVAL = 1000;
    private Timer timer;
    private final IBinder binder = new LocalBinder();

    public static void stopAlarm() {
        v.cancel();
        mp.pause();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mTimeLeftInMillis = intent.getLongExtra("timeLeft", 0);
        startTimer();
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
        TimeoutActivity.setmTimerRunning(false);
    }

    class OnTick extends TimerTask {
        public void run(){
            mTimeLeftInMillis -= speedModifier(COUNTDOWN_INTERVAL);
            TimeoutActivity.setmTimeLeftInMillis(mTimeLeftInMillis);

            if (mTimeLeftInMillis <= 0){
                TimeoutActivity.setmTimerRunning(false);
                timerDoneNotification();
                timer.cancel();

                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                mp = MediaPlayer.create(TimerService.this, notification);
                mp.start();

                v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                long[] pattern = {0, 500, 500};
                v.vibrate(pattern, 0);
            }
        }
    }

    private void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new OnTick(), 500, COUNTDOWN_INTERVAL);
        TimeoutActivity.setmTimerRunning(true);
    }

    public long speedModifier(long interval){
        //25%
        if (speedMod == 0) {
            return interval / 4;
            //50%
        } else if (speedMod == 1) {
            return interval / 2;
            //75%
        } else if (speedMod == 2) {
            return Math.round(interval / 1.3333);
            //100%
        } else if (speedMod == 3) {
            return interval;
            //200%
        } else if (speedMod == 4) {
            return interval * 2;
            //300%
        } else if (speedMod == 5) {
            return interval * 3;
            //400%
        } else if (speedMod == 6) {
            return interval * 4;
        }
        return interval;
    }

    static public void setSpeedModifier(long speed){
        speedMod = speed;
    }

    private void timerDoneNotification() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("TimerService", "TimerService", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        Intent stopIntent = new Intent(this, TimerBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, stopIntent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "TimerService");
        builder.setContentTitle("Timeout Over");
        builder.setContentText("Timer is complete");
        builder.setSmallIcon(R.drawable.ic_baseline_access_alarm_24);
        builder.setAutoCancel(true);
        builder.addAction(R.drawable.ic_baseline_pause_presentation_24, "Stop Alarm", pendingIntent);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(TimerService.this);
        managerCompat.notify(1, builder.build());
    }

    private class LocalBinder extends Binder {
        TimerService getService() {
            return TimerService.this;
        }
    }
}
