package c.cmpt276.childapp.model.breath;

import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import c.cmpt276.childapp.R;
import c.cmpt276.childapp.TakeBreathActivity;

public class InState extends BreathState {
    TakeBreathActivity context;

    public InState(TakeBreathActivity context) {
        this.context = context;
        this.setState(UserState.BREATH_IN);
    }

    @Override
    public void hearButton(Button btn) {
        context.setInstruction(R.string.start_in_instrcut);
        btn.setText(R.string.breath_in);
        btn.setOnTouchListener(new View.OnTouchListener() {
            private long startTouchTime, endTouchTime;
            private boolean calc = false;
            private Handler handler, tooLongHandler;
            private boolean breathOut = false;

            public boolean onTouch(View view, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    context.setInstruction(R.string.instruction_in);
                    startTouchTime = System.currentTimeMillis();
                    handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            context.setInstruction(R.string.after3secIn);
                            breathOut = true;
                        }
                    }, 3000);
                    tooLongHandler = new Handler();
                    tooLongHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            context.setInstruction(R.string.after10SecIn);
                        }
                    }, 10000);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    calc = true;
                    endTouchTime = System.currentTimeMillis();
                    if (breathOut) {
                        tooLongHandler.removeCallbacksAndMessages(null);
                        context.signalNextState(new OutState(context));
                    }
                }
                if (calc) {
                    Log.d("holded", String.valueOf((endTouchTime - startTouchTime) / 1000));
                    if (((endTouchTime - startTouchTime) / 1000) < 3) {
                        context.setInstruction(R.string.start_in_instrcut);
                        handler.removeCallbacksAndMessages(null);
                        tooLongHandler.removeCallbacksAndMessages(null);
                    }
                    calc = false;
                }


                return false;
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //DO NOTHING
            }
        });
    }
}
