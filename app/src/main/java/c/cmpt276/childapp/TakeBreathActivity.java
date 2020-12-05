package c.cmpt276.childapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class TakeBreathActivity extends AppCompatActivity {

    private int numBreath;

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


        setButtons();
        setState(stateLessThanThree);
    }

    public static Intent createIntent(Context context) {
        return new Intent(context, TakeBreathActivity.class);
    }

    private void setButtons() {
        final List<Integer> breathes = new ArrayList<>();
        Spinner spn = findViewById(R.id.spnBreathNum);
        for (int i = 1; i < 11; i++) {
            breathes.add(i);
        }
        ArrayAdapter<Integer> adapterS = new ArrayAdapter<Integer>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, breathes);
        adapterS.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);//TODO fix not centered text in spinner
        spn.setAdapter(adapterS);
        spn.setSelection(2);
        numBreath = breathes.get(2);//default
        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                numBreath = breathes.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button begin = findViewById(R.id.btnMainBreath);
        begin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentState.handleClickLessThanThree();
            }
        });
        Button btn = findViewById(R.id.btn); //what does this do?
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                currentState.handleClickThreeToTen();
            }
        });

    }

    //TODO need to rework here
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
            final Button begin = findViewById(R.id.btnMainBreath);
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
            final Button begin = findViewById(R.id.btnMainBreath);
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
    private abstract class State {
        void handleClickLessThanThree() {
        }

        void handleClickThreeToTen() {
        }

        //void handleClickGreaterThanTen(){}
        void handleEnter() {
        }

        void handleExit() {
        }
    }
}