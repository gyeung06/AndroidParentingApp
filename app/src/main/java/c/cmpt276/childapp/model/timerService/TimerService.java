package c.cmpt276.childapp.model.timerService;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;


import androidx.annotation.Nullable;

import java.util.Locale;

import c.cmpt276.childapp.TimeoutActivity;

public class TimerService extends Service {
    private long mTimeLeftInMillis;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private TimeoutActivity activity = new TimeoutActivity();


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mTimeLeftInMillis = intent.getLongExtra("timeLeft", 0);
        startTimer();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        activity.setmTimeLeftInMillis(mTimeLeftInMillis);
        mCountDownTimer.cancel();
        activity.setmTimerRunning(false);
    }

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis,999) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                activity.setmTimeLeftInMillis(mTimeLeftInMillis);

                int minutes = (int)(mTimeLeftInMillis/1000)/60;
                int seconds = (int)(mTimeLeftInMillis/1000)%60;

                String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
                Log.i("huh", "onTick: "+timeLeftFormatted);
                activity.updateCountDownText(timeLeftFormatted);
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                activity.setmTimerRunning(false);
            }
        }.start();
        mTimerRunning = true;
        activity.setmTimerRunning(true);
    }
}
