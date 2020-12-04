package c.cmpt276.childapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TakeBreathActivity extends AppCompatActivity {

    private int numBreath;
    TextView nBreath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_breath);

        nBreath = findViewById(R.id.textView2);
        setButtons();
    }

    public static Intent createIntent(Context context) {
        return new Intent(context, TakeBreathActivity.class);
    }

    void setButtons(){
        Button breath1 = findViewById(R.id.button1);
        breath1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numBreath = 1;
                nBreath.setText("Let's take " + numBreath +" breaths together");
            }
        });

        Button breath2 = findViewById(R.id.button2);
        breath2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numBreath = 2;
                nBreath.setText("Let's take " + numBreath +" breaths together");
            }
        });

        Button breath3 = findViewById(R.id.button3);
        breath3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numBreath = 3;
                nBreath.setText("Let's take " + numBreath +" breaths together");
            }
        });

        Button breath4 = findViewById(R.id.button4);
        breath4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numBreath = 4;
                nBreath.setText("Let's take " + numBreath +" breaths together");
            }
        });

        Button breath5 = findViewById(R.id.button5);
        breath5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numBreath = 5;
                nBreath.setText("Let's take " + numBreath +" breaths together");
            }
        });
        Button breath6 = findViewById(R.id.button6);
        breath6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numBreath = 6;
                nBreath.setText("Let's take " + numBreath +" breaths together");
            }
        });
        Button breath7 = findViewById(R.id.button7);
        breath7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numBreath = 7;
                nBreath.setText("Let's take " + numBreath +" breaths together");
            }
        });

        Button breath8 = findViewById(R.id.button8);
        breath8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numBreath = 8;
                nBreath.setText("Let's take " + numBreath +" breaths together");
            }
        });

        Button breath9 = findViewById(R.id.button9);
        breath9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numBreath = 9;
                nBreath.setText("Let's take " + numBreath +" breaths together");
            }
        });

        Button breath10 = findViewById(R.id.button10);
        breath10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numBreath = 10;
                nBreath.setText("Let's take " + numBreath +" breaths together");
            }
        });

        Button begin = findViewById(R.id.button);
        begin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}