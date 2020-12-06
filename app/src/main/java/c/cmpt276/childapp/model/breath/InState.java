package c.cmpt276.childapp.model.breath;

import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
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
                    context.setImageView(R.drawable.breath);
                    ScaleAnimation breath_in = new ScaleAnimation(0,1,0,1, Animation.RELATIVE_TO_SELF,
                            0.5f,Animation.RELATIVE_TO_SELF,0.5f);

                    breath_in.setDuration(3000);
                    context.startAnimation(breath_in);

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
                        context.setImageView(R.drawable.image_nothing);
                        handler.removeCallbacksAndMessages(null);
                        tooLongHandler.removeCallbacksAndMessages(null);
                        ScaleAnimation breath_in = new ScaleAnimation(0,1,0,1, Animation.RELATIVE_TO_SELF,
                                0.5f,Animation.RELATIVE_TO_SELF,0.5f);

                        breath_in.setDuration(endTouchTime - startTouchTime);


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
