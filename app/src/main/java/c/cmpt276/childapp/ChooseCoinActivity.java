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

import java.util.ArrayList;
import java.util.List;

import c.cmpt276.childapp.model.FlipCoinHistory.FlipCoinRecord;
import c.cmpt276.childapp.model.FlipCoinHistory.HistoryCollection;
import c.cmpt276.childapp.model.config.ChildrenConfigCollection;

public class ChooseCoinActivity extends AppCompatActivity {
    private ChildrenConfigCollection configs = ChildrenConfigCollection.getInstance();
    private boolean seeSelectedChildOnly = false, headWins = true;
    private int selectedChild = -1;
    private int selectedRival = -1;
    RadioButton rbHead;
    RadioButton rbTail;
    ListView listChild, listRival, listHistory;
    Button btnContinue;
    CheckBox chkViewHis;
    private List<Integer> hasFlipCoinPositions;

    public static Intent createIntent(Context context) {
        return new Intent(context, ChooseCoinActivity.class);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setContentView(R.layout.activity_choose_coin);

        hasFlipCoinPositions = configs.getFlipCoinEnabledChildrenPositions();

        rbHead = findViewById(R.id.rdbHead);
        rbTail = findViewById(R.id.rdbTail);
        btnContinue = findViewById(R.id.continuebutton);
        chkViewHis = findViewById(R.id.chkViewHistorySelected);

        listChild = findViewById(R.id.lsChoosingChild);
        listRival = findViewById(R.id.lsRival);
        listHistory = findViewById(R.id.tableHistory);

        setButtons();
        updateSetFirstChildList();
        updateHistory();
    }

    private void updateSetFirstChildList() {
        int lastLoser = -1, lastWinner = -1;

        List<String> listName = new ArrayList<>();
        for (int child : hasFlipCoinPositions) {
            listName.add(configs.get(child).getName());
            if (configs.get(child).getName().equals(configs.getLastLoser())) {
                lastLoser = child;
            } else if (configs.get(child).getName().equals(configs.getLastWinner())) {
                lastWinner = child;
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, android.R.id.text1, listName);
        listChild.setAdapter(adapter);
        listChild.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listChild.setSelector(R.color.design_default_color_secondary);
        listChild.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int child = hasFlipCoinPositions.get(i);
                if (child != selectedChild) {
                    selectedChild = child;
                    listChild.setSelection(child);
                    Log.d("Selected", configs.get(child).getName());
                    selectedRival = -1;
                    updateSetSecondChildList(-1);
                }
            }
        });

        if (lastLoser != -1 && lastWinner != -1) {
            listChild.setItemChecked(lastLoser, true);
            // Log.d("selection:",listView.getSelectedItem().toString());
            selectedChild = lastLoser;
            updateSetSecondChildList(lastWinner);
        }
    }

    private void updateSetSecondChildList(int counterWinner) {
        int offsetLocation = -1;
        List<String> listName = new ArrayList<>();
        for (int i = 0; i < hasFlipCoinPositions.size(); i++) {
            int child = hasFlipCoinPositions.get(i);
            if (configs.get(child).getName().equals(configs.get(selectedChild).getName())) {
                offsetLocation = i;
                continue; //>=
            }
            listName.add(configs.get(child).getName());
        }
        final int ofLo = offsetLocation;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, android.R.id.text1, listName);
        listRival.setAdapter(adapter);

        listRival.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (hasFlipCoinPositions.get(i) != selectedRival) {
                    if (i >= ofLo) {
                        listRival.setSelection(i + 1);
                        selectedRival = hasFlipCoinPositions.get(i + 1);
                    } else {
                        listRival.setSelection(i);
                        selectedRival = hasFlipCoinPositions.get(i);
                    }
                    Log.d("Selected_Second", configs.get(selectedRival).getName());
                }
            }
        });
        listRival.setSelector(R.color.design_default_color_secondary);
        listRival.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        if (counterWinner != -1) {
            // listRival.requestFocusFromTouch();
            if (counterWinner >= ofLo) {//TODO not sure why unable to click
                listRival.setItemChecked(counterWinner - 1, true);
                // listRival.setSelection(counterWinner-1);
            } else {
                listRival.setItemChecked(counterWinner, true);

            }
            Log.d("Counter winner: ", configs.get(counterWinner).getName());
            selectedRival = counterWinner;
        }
    }

    private void setButtons() {
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int k = headWins ? 1 : 0;
                startActivity(FlipCoinActivity.createIntent(ChooseCoinActivity.this, selectedChild, selectedRival, k));
                finish();
            }
        });


        rbHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rbTail.setChecked(false);
                headWins = true;
                Toast.makeText(getApplicationContext(), "Head selected", Toast.LENGTH_SHORT).show();
            }
        });

        rbTail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rbHead.setChecked(false);
                headWins = false;
                Toast.makeText(getApplicationContext(), "Tail selected", Toast.LENGTH_SHORT).show();
            }
        });

        chkViewHis.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (selectedChild < 0) {
                    Toast.makeText(getApplicationContext(), "Cannot filter, you have to select one first", Toast.LENGTH_SHORT).show();
                    chkViewHis.setChecked(false);
                    return;
                }
                seeSelectedChildOnly = b;
                updateHistory();
            }
        });
    }

    private void updateHistory() {
        ArrayAdapter<FlipCoinRecord> adapter;
        HistoryCollection history = configs.getFlipCoinHistory();
        if (seeSelectedChildOnly) {
            adapter = new HistoryAdapter(history, configs.get(selectedChild).getName());
        } else {
            adapter = new HistoryAdapter(history);
        }

        listHistory.removeAllViewsInLayout();
        listHistory.setAdapter(adapter);
        listHistory.setClickable(false);
    }

    private class HistoryAdapter extends ArrayAdapter<FlipCoinRecord> {
        String filterToDo = "";
        HistoryCollection history;

        public HistoryAdapter(HistoryCollection history, String filter) {
            super(ChooseCoinActivity.this, R.layout.history_list_item, history.getArray());
            this.history = history;
            filterToDo = filter;
        }

        public HistoryAdapter(HistoryCollection history) {
            super(ChooseCoinActivity.this, R.layout.history_list_item, history.getArray());
            this.history = history;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // Make sure we have a view to work with (may have been given null)
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.history_list_item, parent, false);
            }

            FlipCoinRecord currentHistory;
            if (filterToDo.trim().isEmpty()) {
                currentHistory = history.get(position);
            } else {
                currentHistory = history.filter(filterToDo).get(position);
            }

            TextView date = itemView.findViewById(R.id.txtTime_item);
            TextView head = itemView.findViewById(R.id.txtHeadPlayer_item);
            TextView tail = itemView.findViewById(R.id.txtTailPlayer_item);
            TextView win = itemView.findViewById(R.id.txtWinner_item);
            date.setText(currentHistory.getDate());
            head.setText(currentHistory.getHeadChild());
            tail.setText(currentHistory.getTailChild());
            win.setText(currentHistory.isHead() ? "Head" : "Tail");

            return itemView;
        }
    }
}
