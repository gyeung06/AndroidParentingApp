package c.cmpt276.childapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TakeBreathActivity extends AppCompatActivity {

    private int numBreath;
    TextView nBreath;

    private abstract class State{
        void handleClickLessThanThree(){}
        void handleClickThreeToTen(){}
        //void handleClickGreaterThanTen(){}
        void handleEnter(){}
        void handleExit(){}
    }

    private final State stateLessThanThree = new state1();
    private final State stateThreeToTen = new state2();
    //private final State stateGreaterThanTen = new state3();

    private State currentState;

    private void setState(State newState){
        if(currentState != null) {
            currentState.handleExit();
        }
        currentState = newState;
        currentState.handleEnter();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_breath);

        nBreath = findViewById(R.id.textView2);
        setButtons();
        setState(stateLessThanThree);
    }

    public static Intent createIntent(Context context) {
        return new Intent(context, TakeBreathActivity.class);
    }

    private void setButtons(){
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
            public void onClick(View view) {
                currentState.handleClickLessThanThree();
            }
        });
        Button btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                currentState.handleClickThreeToTen();
            }
        });

    }

    private class state1 extends State {
        private int count = 0;

        Handler timerHandler = new Handler();
        Runnable timerRunnable = new Runnable() {
            @Override
            public void run() {
                setState(stateThreeToTen);
            }
       };
        @Override
        void handleEnter() {
            //super.handleEnter();
            final Button begin = findViewById(R.id.button);
            begin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    begin.setText("In");
                    currentState.handleClickLessThanThree();
                }
            });
           timerHandler.postDelayed(timerRunnable, 3000);
        }

        @Override
        void handleExit() {
            //super.handleExit();
            Log.i("offState","exit");
        }

        @Override
        void handleClickLessThanThree() {

            //super.handleClickLessThanThree();
            Toast.makeText(TakeBreathActivity.this, "form 3 to 3",Toast.LENGTH_SHORT )
                    .show();
        }

        @Override
        void handleClickThreeToTen() {
            count++;
           // super.handleClickThreeToTen();
            Toast.makeText(TakeBreathActivity.this, "form 3 to 3~10",Toast.LENGTH_SHORT )
                    .show();

            setState(stateThreeToTen);
        }

    }

    private class state2 extends State {
        Handler timerHandler = new Handler();
        Runnable timerRunnable = new Runnable() {
            @Override
            public void run() {
                setState(stateLessThanThree);
            }
        };
        @Override
        void handleEnter() {
           // super.handleEnter();
            final Button begin = findViewById(R.id.button);
            begin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    begin.setText("Out");
                    currentState.handleClickLessThanThree();
                }
            });
            timerHandler.postDelayed(timerRunnable, 2000);
        }

        @Override
        void handleClickLessThanThree() {
            //super.handleClickLessThanThree();

            Toast.makeText(TakeBreathActivity.this, "form 3~10 to 3",Toast.LENGTH_SHORT )
                    .show();
            setState(stateLessThanThree);

        }


        @Override
        void handleClickThreeToTen() {
            super.handleClickThreeToTen();
            timerHandler.removeCallbacks(timerRunnable);
            timerHandler.postDelayed(timerRunnable,2000);
        }
    }

//    private class state3 extends State {
//        @Override
//        void handleClickLessThanThree() {
//            super.handleClickLessThanThree();
//            Toast.makeText(TakeBreathActivity.this, "form less than 10 to 3",Toast.LENGTH_SHORT )
//                    .show();
//        }
//    }
}