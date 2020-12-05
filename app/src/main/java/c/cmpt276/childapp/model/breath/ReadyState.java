package c.cmpt276.childapp.model.breath;

import android.view.View;
import android.widget.Button;

import c.cmpt276.childapp.R;
import c.cmpt276.childapp.TakeBreathActivity;

public class ReadyState extends BreathState {
    TakeBreathActivity context;

    public ReadyState(TakeBreathActivity context) {
        this.context = context;
        this.setState(UserState.READY);
    }

    public void hearButton(Button btn) {
        context.setInstruction(R.string.ready_instruc);
        btn.setText(R.string.begin);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.signalNextState(new InState(context));
            }
        });
    }

}
