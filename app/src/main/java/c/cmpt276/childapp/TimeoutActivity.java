package c.cmpt276.childapp;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Locale;

import c.cmpt276.childapp.model.config.ChildrenConfigCollection;
import c.cmpt276.childapp.model.timerService.TimerService;

/**
 * Timer main UI
 */

public class TimeoutActivity extends AppCompatActivity implements View.OnClickListener {
    private static final long START_TIME_IN_MILLIS = 60000;
    @SuppressLint("StaticFieldLeak")
    private static TextView mTextViewCountDown;
    private static boolean mTimerRunning;
    private static long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private ChildrenConfigCollection configs = ChildrenConfigCollection.getInstance();
    private Button mButtonStartPause;
    private Button mButtonReset;

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

    public static Intent createIntent(Context context) {
        return new Intent(context, TimeoutActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeout);

        Toolbar toolbar = findViewById(R.id.time_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTextViewCountDown = findViewById(R.id.text_view_timer);
        mButtonStartPause = findViewById(R.id.timer_button_start);
        mButtonReset = findViewById(R.id.timer_button_reset);

        onCreateButtonInit();
        updateCountDownText();
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

    private long getLastUsedTime() {
        SharedPreferences pref = getSharedPreferences("time", 0);
        return pref.getLong("time", 60000);
    }

    private void saveLastUsedTime(long time) {
        SharedPreferences pref = getSharedPreferences("time", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putLong("time", time);
        editor.apply();
    }

    private void initializeButtons(long time) {
        if (mTimerRunning) {
            Toast.makeText(TimeoutActivity.this, R.string.pause_warning, Toast.LENGTH_SHORT).show();
        } else {
            saveLastUsedTime(time);
            resetTimer();
        }
    }

    private void initializeOptionsButtons(int percent, MenuItem item){
        if (item.isChecked()){
            return;
        }
        item.setChecked(true);
        TimerService.setSpeedModifier(percent);
        SharedPreferences pref = getSharedPreferences("timeMod", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putLong("timeMod", percent);
        editor.apply();
    }

    private int getLastUsedSpeedMod(){
        SharedPreferences pref = getSharedPreferences("timeMod", 0);
        return pref.getInt("timeMod", 3);
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
                    if (mTimeLeftInMillis <= 0) {
                        Toast.makeText(getApplicationContext(), R.string.greater_0_warning, Toast.LENGTH_SHORT).show();
                        return;
                    }
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
                if (TextUtils.isEmpty(test)) {
                    Toast.makeText(TimeoutActivity.this, R.string.Enter_time_warning, Toast.LENGTH_SHORT).show();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_timer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_25:
                initializeOptionsButtons(0, item);
            case R.id.menu_50:
                initializeOptionsButtons(1, item);
            case R.id.menu_75:
                initializeOptionsButtons(2, item);
            case R.id.menu_100:
                initializeOptionsButtons(3, item);
            case R.id.menu_200:
                initializeOptionsButtons(4, item);
            case R.id.menu_300:
                initializeOptionsButtons(5, item);
            case R.id.menu_400:
                initializeOptionsButtons(6, item);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}



