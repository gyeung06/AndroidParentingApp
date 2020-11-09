package c.cmpt276.childapp;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Locale;

import c.cmpt276.childapp.model.config.ChildrenConfigCollection;
import c.cmpt276.childapp.model.timerService.TimerService;

/**
 * Timer main UI
 */
//TODO Add background

public class TimeoutActivity extends AppCompatActivity implements View.OnClickListener {
    private ChildrenConfigCollection configs = ChildrenConfigCollection.getInstance();

    private static final long START_TIME_IN_MILLIS = 60000;
    @SuppressLint("StaticFieldLeak")
    private static TextView mTextViewCountDown;
    private Button mButtonStartPause;
    private Button mButtonReset;

    private static boolean mTimerRunning;
    private static long mTimeLeftInMillis = START_TIME_IN_MILLIS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTextViewCountDown = findViewById(R.id.text_view_timer);
        mButtonStartPause = findViewById(R.id.timer_button_start);
        mButtonReset = findViewById(R.id.timer_button_reset);

        onCreateButtonInit();
        updateCountDownText();
    }

    public static void setmTimerRunning(boolean newRunning) {
        mTimerRunning = newRunning;
    }

    public static void setmTimeLeftInMillis(long newTimeLeft) {
        mTimeLeftInMillis = newTimeLeft;
    }

    public static void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        mTextViewCountDown.setText(timeLeftFormatted);
    }

    private void onCreateButtonInit() {
        Button optionBtn;

        optionBtn = findViewById(R.id.timer_1min);
        optionBtn.setOnClickListener(this);
        optionBtn = findViewById(R.id.timer_2min);
        optionBtn.setOnClickListener(this);
        optionBtn = findViewById(R.id.timer_3min);
        optionBtn.setOnClickListener(this);
        optionBtn = findViewById(R.id.timer_5min);
        optionBtn.setOnClickListener(this);
        optionBtn = findViewById(R.id.timer_10min);
        optionBtn.setOnClickListener(this);
        optionBtn = findViewById(R.id.timer_custombtn);
        optionBtn.setOnClickListener(this);

        mButtonStartPause.setOnClickListener(this);
        mButtonReset.setOnClickListener(this);
    }

    private long getLastUsedTime(){
        SharedPreferences pref = getSharedPreferences("time", 0);
        return pref.getLong("time", 60000);
    }

    private void saveLastUsedTime(long time){
        SharedPreferences pref = getSharedPreferences("time", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putLong("time", time);
        editor.apply();
    }

    private void initializeButtons(long time) {
        if (mTimerRunning) {
            Toast.makeText(TimeoutActivity.this, "Pause Timer First", Toast.LENGTH_SHORT).show();
        } else {
            saveLastUsedTime(time);
            resetTimer();
        }
    }

    @SuppressLint("SetTextI18n")
    private void startTimer() {
        Intent newTimer = new Intent(this, TimerService.class);
        newTimer.putExtra("timeLeft", mTimeLeftInMillis);
        startService(newTimer);
        mButtonStartPause.setText("Pause");
    }

    @SuppressLint("SetTextI18n")
    private void pauseTimer() {
        stopService(new Intent(this, TimerService.class));
        mButtonStartPause.setText("Start");
    }

    @SuppressLint("SetTextI18n")
    private void resetTimer() {
        stopService(new Intent(this, TimerService.class));
        mTimeLeftInMillis = getLastUsedTime();
        updateCountDownText();
        mButtonStartPause.setText("Start");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.timer_button_start:
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
                break;
            case R.id.timer_button_reset:
                resetTimer();
                break;
            case R.id.timer_1min:
                initializeButtons(60000);
                break;
            case R.id.timer_2min:
                initializeButtons(120000);
                break;
            case R.id.timer_3min:
                initializeButtons(180000);
                break;
            case R.id.timer_5min:
                initializeButtons(300000);
                break;
            case R.id.timer_10min:
                initializeButtons(600000);
                break;
            case R.id.timer_custombtn:
                EditText num = findViewById(R.id.timer_custom_input);
                String test = num.getText().toString();
                if(TextUtils.isEmpty(test)){
                    Toast.makeText(TimeoutActivity.this, "Enter a time", Toast.LENGTH_SHORT).show();
                } else {
                    long minutes = Long.parseLong(test);
                    minutes *= 60000;
                    initializeButtons(minutes);
                }
                break;
        }
    }

    protected void onPause() {
        SharedPreferences sp = getSharedPreferences("USER_CHILDREN", MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.clear();
        ed.putString("CHILDREN_INFO", configs.getJSON());
        ed.apply();
        super.onPause();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static Intent createIntent(Context context) {
        return new Intent(context, TimeoutActivity.class);
    }
}



