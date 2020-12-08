package c.cmpt276.childapp.model.breath;

import android.widget.Button;

/**
 * Abstract class that extended by In, Out and Ready State
 */
public abstract class BreathState {
    private UserState state;

    public abstract void hearButton(Button btn);

    public UserState currentState() {
        return state;
    }

    public void setState(UserState state) {
        this.state = state;
    }

}
