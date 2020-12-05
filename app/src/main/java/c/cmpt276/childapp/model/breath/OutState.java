package c.cmpt276.childapp.model.breath;

import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import c.cmpt276.childapp.R;
import c.cmpt276.childapp.TakeBreathActivity;

public class OutState extends BreathState {
    TakeBreathActivity context;
    Button btn;

    public OutState(TakeBreathActivity context) {
        this.context = context;
        this.setState(UserState.BREATH_OUT);
    }

    @Override
    public void hearButton(final Button btn) {
        this.btn = btn;
        context.setInstruction(R.string.start_out_instrcut);
        btn.setText(R.string.breath_out);
        btn.setOnTouchListener(new View.OnTouchListener() {
            private Handler handler, tooLongHandler;
            private boolean done = false;

            public boolean onTouch(View view, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    context.setInstruction(R.string.instruction_out);
                    handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            context.setInstruction(R.string.outok);
                            done = true;
                        }
                    }, 3000);
                    tooLongHandler = new Handler();
                    tooLongHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, R.string.after10secOut, Toast.LENGTH_SHORT).show();
                            doneOneCycle();
                            btn.callOnClick();
                        }
                    }, 10000);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    tooLongHandler.removeCallbacksAndMessages(null);
                    if (!done) {
                        context.setInstruction(R.string.breath3secOut_notEnough);
                        handler.removeCallbacksAndMessages(null);
                    } else {
                        doneOneCycle();
                    }
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

    private void doneOneCycle() {
        context.numBreathRemaining--;
        if (context.numBreathRemaining > 0) {
            Toast.makeText(context, R.string.after3secOut_needmore, Toast.LENGTH_LONG).show();
            context.signalNextState(new InState(context));

        } else {
            context.setInstruction(R.string.welldoneInstruction);
            btn.setText(R.string.gj);
            setState(UserState.DONE);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                }
            });
            btn.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                        Handler h = new Handler();
                        btn.setEnabled(false);
                        h.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                btn.setEnabled(true);
                            }
                        }, 250);
                        setState(UserState.READY);
                        context.signalNextState(new ReadyState(context));
                        context.resetNumBreath();
                    }
                    return false;
                }
            });
        }
    }
}
