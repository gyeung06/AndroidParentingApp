package c.cmpt276.childapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import c.cmpt276.childapp.model.breath.BreathState;
import c.cmpt276.childapp.model.breath.ReadyState;
import c.cmpt276.childapp.model.breath.UserState;
import c.cmpt276.childapp.model.config.ChildrenConfigCollection;

public class TakeBreathActivity extends AppCompatActivity {

    public int numBreathRemaining;
    private BreathState state = new ReadyState(TakeBreathActivity.this);
    private Button begin;
    private Spinner spn;
    private TextView instruction;

    public void resetNumBreath() {
        numBreathRemaining = spn.getSelectedItemPosition() + 1;
        spn.setEnabled(true);
    }

    public void signalNextState(BreathState newState) {
        state = newState;
        if (state.currentState() != UserState.READY) {
            spn.setEnabled(false);
        }
        state.hearButton(begin);
    }

    public void setInstruction(int resource) {
        instruction.setText(resource);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_breath);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setButtons();
    }

    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public static Intent createIntent(Context context) {
        return new Intent(context, TakeBreathActivity.class);
    }

    private void setButtons() {
        final List<Integer> breathes = new ArrayList<>();
        spn = findViewById(R.id.spnBreathNum);
        for (int i = 1; i < 11; i++) {
            breathes.add(i);
        }
        ArrayAdapter<Integer> adapterS = new ArrayAdapter<Integer>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, breathes);
        adapterS.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spn.setAdapter(adapterS);
        spn.setSelection(2);
        numBreathRemaining = breathes.get(2);//default
        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                numBreathRemaining = breathes.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        begin = findViewById(R.id.btnMainBreath);
        instruction = findViewById(R.id.txtInstruction);
        signalNextState(state);

    }

    protected void onPause() {
        ChildrenConfigCollection.getInstance().save(this);
        super.onPause();
    }


}