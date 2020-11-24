package c.cmpt276.childapp;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;

import androidx.constraintlayout.widget.ConstraintLayout;

public class CheckableConstraintLayout extends ConstraintLayout implements Checkable {

    private static final int[] CHECKED_STATE_SET = {
            android.R.attr.state_checked
    };
    private boolean checked;

    public CheckableConstraintLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }

    @Override
    public boolean isChecked() {
        return checked;
    }

    @Override
    public void setChecked(boolean checked) {
        if (this.checked != checked) {
            this.checked = checked;
            refreshDrawableState();
        }
    }

    @Override
    public void toggle() {
        this.checked = !this.checked;
    }
}
