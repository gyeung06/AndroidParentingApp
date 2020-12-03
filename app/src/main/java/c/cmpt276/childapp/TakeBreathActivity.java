package c.cmpt276.childapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class TakeBreathActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_breath);
    }

    public static Intent createIntent(Context context) {
        return new Intent(context, TakeBreathActivity.class);
    }
}