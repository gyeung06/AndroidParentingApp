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
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.Vibrator;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import c.cmpt276.childapp.R;
import c.cmpt276.childapp.TimeoutActivity;

public class TimerService extends Service {
    private static long mTimeLeftInMillis;
    private static Vibrator v;
    private static MediaPlayer mp;
    private CountDownTimer mCountDownTimer;

    public static void stopAlarm() {
        v.cancel();
        mp.stop();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
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
        mCountDownTimer.cancel();
        TimeoutActivity.setmTimerRunning(false);
    }

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 200) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                TimeoutActivity.setmTimeLeftInMillis(mTimeLeftInMillis);
                TimeoutActivity.updateCountDownText();
            }

            @Override
            public void onFinish() {
                TimeoutActivity.setmTimerRunning(false);
                timerDoneNotification();

                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                mp = MediaPlayer.create(TimerService.this, notification);
                mp.start();

                v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                long[] pattern = {0, 500, 500};
                v.vibrate(pattern, 0);
            }
        }.start();
        TimeoutActivity.setmTimerRunning(true);
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
}
