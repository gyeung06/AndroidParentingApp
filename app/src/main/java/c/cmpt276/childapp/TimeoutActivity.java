package c.cmpt276.childapp;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import c.cmpt276.childapp.model.config.ChildrenConfigCollection;
import c.cmpt276.childapp.model.timerService.TimerService;

public class TimeoutActivity extends AppCompatActivity {
    private ChildrenConfigCollection configs = ChildrenConfigCollection.getInstance();

    private static final long START_TIME_IN_MILLIS = 5000;
    private static TextView mTextViewCountDown;
    private Button mButtonStartPause;
    private Button mButtonReset;

    private static boolean mTimerRunning;

    private static long mTimeLeftInMillis = START_TIME_IN_MILLIS;

    public static void setmTimerRunning(boolean mTimerRunning) {
        TimeoutActivity.mTimerRunning = mTimerRunning;
    }

    public static void setmTimeLeftInMillis(long mTimeLeftInMillis) {
        TimeoutActivity.mTimeLeftInMillis = mTimeLeftInMillis;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeout);

        mTextViewCountDown = findViewById(R.id.text_view_timer);

        mButtonStartPause = findViewById(R.id.button_start);
        mButtonReset = findViewById(R.id.button_reset);

        mButtonStartPause.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (mTimerRunning) {
                    pauseTimer();
                }
                else{
                    startTimer();
                }
            }
        });
        mButtonReset.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });

        updateCountDownText("00:05");

    }

    private void startTimer() {
        Intent newTimer = new Intent(this, TimerService.class);
        newTimer.putExtra("timeLeft", mTimeLeftInMillis);
        startService(newTimer);
        mButtonStartPause.setText("Pause");
        mButtonReset.setVisibility(View.INVISIBLE);
    }

    private void pauseTimer() {
        stopService(new Intent(this, TimerService.class));
        mButtonStartPause.setText("Start");
        mButtonReset.setVisibility(View.VISIBLE);
    }

    private void resetTimer() {
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        stopService(new Intent(this, TimerService.class));
        mButtonReset.setVisibility(View.INVISIBLE);
        mButtonStartPause.setVisibility(View.VISIBLE);
    }

    public static void updateCountDownText(String timeLeftFormatted) {
        mTextViewCountDown.setText(timeLeftFormatted);
    }

    protected void onPause() {
        SharedPreferences sp = getSharedPreferences("USER_CHILDREN", MODE_PRIVATE);;
        SharedPreferences.Editor ed = sp.edit();
        ed.clear();
        ed.putString("CHILDREN_INFO",configs.getJSON());
        ed.commit();
        super.onPause();
    }



    public static Intent createIntent(Context context) {
        Intent i = new Intent(context, TimeoutActivity.class);
        return i;
    }

}



