package c.cmpt276.childapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import c.cmpt276.childapp.model.FlipCoinHistory.FlipCoinRecord;
import c.cmpt276.childapp.model.FlipCoinHistory.HistoryCollection;
import c.cmpt276.childapp.model.config.ChildrenConfigCollection;

/**
 * Class for choosing which two children are competing
 */
public class ChooseCoinActivity extends AppCompatActivity {
    private ChildrenConfigCollection configs = ChildrenConfigCollection.getInstance();
    private int selectedChild = -1;
    private boolean seeSelectedChildOnly = false, headWins = true;
    private int selectedRival = -1;
    private List<String> hasFlipCoinNames, secondChildList;

    Button btnContinue;
    RadioButton radHead;
    RadioButton radTail;
    CheckBox chkViewHis;
    ListView listFirstChild;
    ListView listRival;
    ListView listHistory;
    ArrayAdapter<String> listRivalAdapter;

    public static Intent createIntent(Context context) {
        return new Intent(context, ChooseCoinActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_coin);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        hasFlipCoinNames = configs.getFlipCoinEnabledChildrenNames();
        secondChildList = new ArrayList<>();

        btnContinue = findViewById(R.id.continuebutton);
        radHead = findViewById(R.id.rdbHead);
        radTail = findViewById(R.id.rdbTail);
        chkViewHis = findViewById(R.id.chkViewHistorySelected);

        listHistory = findViewById(R.id.tableHistory);
        listFirstChild = findViewById(R.id.lsChoosingChild);
        listRival = findViewById(R.id.lsRival);

        setButtons();
        setupSecondChildList();

        updateSetFirstChildList();
        updatePreviousWinner();
        updateHistory();
    }

    private void updatePreviousWinner() {
        String lastLoser = configs.getLastLoser();
        String lastWinner = configs.getLastWinner();

        if (!lastLoser.isEmpty() && !lastWinner.isEmpty()) {
            if (!hasFlipCoinNames.contains(lastLoser) || !hasFlipCoinNames.contains(lastWinner)) {
                Log.d("loser - ", lastLoser);
                Log.d("winner -", lastWinner);
                return;
            }

            selectedChild = hasFlipCoinNames.indexOf(lastLoser);
            listFirstChild.setItemChecked(selectedChild, true);

            updateSetSecondChildList();

            selectedRival = secondChildList.indexOf(lastWinner);
            listRival.setItemChecked(selectedRival, true);

            Log.d("autoselected loser", lastLoser);
            Log.d("loser index", "" + selectedChild);
            Log.d("autoselected winner", lastWinner);
            Log.d("winner index", "" + selectedRival);
        }
    }

    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    protected void onPause() {
        SharedPreferences sp = getSharedPreferences("USER_CHILDREN", MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.clear();
        ed.putString("CHILDREN_INFO", configs.getJSON());
        ed.apply();
        super.onPause();
    }

    private void updateSetFirstChildList() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, android.R.id.text1, hasFlipCoinNames);
        listFirstChild.setAdapter(adapter);
        listFirstChild.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listFirstChild.setSelector(R.color.design_default_color_secondary);
        listFirstChild.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == selectedChild) return;

                //listView.setSelection(hasFlipCoinPositions.get(i));
                selectedChild = i;
                Log.d("Selected", hasFlipCoinNames.get(selectedChild));
                selectedRival = -1;

                updateSetSecondChildList();

                if (seeSelectedChildOnly) {
                    updateHistory();
                }
            }
        });
    }

    private void setupSecondChildList() {
        listRivalAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, android.R.id.text1, secondChildList);
        listRival.setAdapter(listRivalAdapter);
        listRival.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == selectedRival) return;
                selectedRival = i;
                Log.d("Selected_Second", secondChildList.get(selectedRival));
            }
        });
        listRival.setSelector(R.color.design_default_color_secondary);
        listRival.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    private void updateSetSecondChildList() {
        // repopulate omitting first child
        secondChildList.clear();
        for (int i = 0; i < hasFlipCoinNames.size(); i++) {
            if (i == selectedChild) continue;
            secondChildList.add(hasFlipCoinNames.get(i));
        }
        listRival.setAdapter(listRivalAdapter);
    }

    private void setButtons() {
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstChild = "";
                String secondChild = "";

                if (selectedChild != -1 && selectedRival != -1) {
                    firstChild = hasFlipCoinNames.get(selectedChild);
                    secondChild = secondChildList.get(selectedRival);
                }

                startActivity(FlipCoinActivity.createIntent(ChooseCoinActivity.this, firstChild, secondChild, headWins));

                finish();
            }
        });

        radHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radTail.setChecked(false);
                headWins = true;
                Toast.makeText(getApplicationContext(), "Head selected", Toast.LENGTH_SHORT).show();
            }
        });

        radTail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radHead.setChecked(false);
                headWins = false;
                Toast.makeText(getApplicationContext(), "Tail selected", Toast.LENGTH_SHORT).show();
            }
        });

        chkViewHis.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (selectedChild < 0) {
                    Toast.makeText(getApplicationContext(), "Cannot filter, you have to select the choosing child first", Toast.LENGTH_SHORT).show();
                    chkViewHis.setChecked(false);
                    return;
                }
                seeSelectedChildOnly = b;
                updateHistory();
            }
        });
    }

    private void updateHistory() {
        HistoryCollection history = configs.getFlipCoinHistory();

        if (seeSelectedChildOnly) {
            history = history.filter(hasFlipCoinNames.get(selectedChild));
        }

        ArrayAdapter<FlipCoinRecord> adapter = new HistoryAdapter(history.getArray());
        listHistory.removeAllViewsInLayout();
        listHistory.setAdapter(adapter);
        listHistory.setClickable(false);
    }

    private class HistoryAdapter extends ArrayAdapter<FlipCoinRecord> {
        List<FlipCoinRecord> list;

        public HistoryAdapter(List<FlipCoinRecord> list) {
            super(ChooseCoinActivity.this, R.layout.history_list_item, list);
            this.list = list;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // Make sure we have a view to work with (may have been given null)
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.history_list_item, parent, false);
            }

            FlipCoinRecord currentHistory = list.get(position);

            TextView chooser = itemView.findViewById(R.id.txtChooser);
            TextView rival = itemView.findViewById(R.id.txtRival);
            TextView date = itemView.findViewById(R.id.txtTime_item);
            TextView choice = itemView.findViewById(R.id.txtChoice);
            TextView result = itemView.findViewById(R.id.txtResult);

            chooser.setText(currentHistory.getChooser());
            rival.setText(currentHistory.getRival());
            date.setText(currentHistory.getDate());

            if (currentHistory.getChoseHead()) {
                choice.setText("Heads");
            } else {
                choice.setText("Tails");
            }

            if (currentHistory.getResult()) {
                result.setText("Heads");
            } else {
                result.setText("Tails");
            }

            return itemView;
        }
    }
}
