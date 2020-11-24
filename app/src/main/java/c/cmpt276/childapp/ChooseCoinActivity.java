package c.cmpt276.childapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import c.cmpt276.childapp.model.FlipCoinHistory.FlipCoinRecord;
import c.cmpt276.childapp.model.FlipCoinHistory.HistoryCollection;
import c.cmpt276.childapp.model.config.ChildrenConfigCollection;
import c.cmpt276.childapp.model.config.IndividualConfig;

/**
 * Class for choosing which two children are competing
 */
public class ChooseCoinActivity extends AppCompatActivity {
    private ChildrenConfigCollection configs = ChildrenConfigCollection.getInstance();
    private int selectedChild = -1;
    private boolean seeSelectedChildOnly = false, headWins = true;
    private int selectedRival = -1;
    ChildNameAdapter listRivalAdapter;

    Button btnContinue;
    Button btnClear;
    RadioButton radHead;
    RadioButton radTail;
    CheckBox chkViewHis;
    ListView listFirstChild;
    ListView listRival;
    ListView listHistory;
    private List<IndividualConfig> firstChildList, secondChildList;

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

        firstChildList = configs.getFlipCoinEnabledChildren();
        firstChildList.sort(new Comparator<IndividualConfig>() {
            @Override
            public int compare(IndividualConfig t0, IndividualConfig t1) {
                return Long.compare(t0.getLastChose(), t1.getLastChose());
            }
        });

        secondChildList = new ArrayList<>();

        btnContinue = findViewById(R.id.continuebutton);
        btnClear = findViewById(R.id.btn_clear_selection);
        radHead = findViewById(R.id.rdbHead);
        radTail = findViewById(R.id.rdbTail);
        chkViewHis = findViewById(R.id.chkViewHistorySelected);

        listHistory = findViewById(R.id.tableHistory);
        listFirstChild = findViewById(R.id.lsChoosingChild);
        listRival = findViewById(R.id.lsRival);

        setButtons();
        setupSecondChildList();

        updateSetFirstChildList();
        updateInitialChooser();
        updateHistory();
    }

    private void updateInitialChooser() {
        Log.e("ASD", "" + listFirstChild.getAdapter().getCount());
        //listFirstChild.setItemChecked(0, true);
        listFirstChild.performItemClick(null, 0, listFirstChild.getItemIdAtPosition(0));
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
        final ChildNameAdapter adapter = new ChildNameAdapter(firstChildList, listFirstChild);
        listFirstChild.setAdapter(adapter);
        listFirstChild.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == selectedChild) return;

                //listView.setSelection(hasFlipCoinPositions.get(i));
                selectedChild = i;
                Log.d("Selected", firstChildList.get(selectedChild).getName());
                selectedRival = -1;
                adapter.notifyDataSetChanged();

                updateSetSecondChildList();

                if (seeSelectedChildOnly) {
                    updateHistory();
                }
            }
        });
    }

    private void setupSecondChildList() {
        listRivalAdapter = new ChildNameAdapter(secondChildList, listRival);
        listRival.setAdapter(listRivalAdapter);
        listRival.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == selectedRival) return;
                selectedRival = i;
                Log.d("Selected_Second", secondChildList.get(selectedRival).getName());
                listRivalAdapter.notifyDataSetChanged();
            }
        });
    }

    private void updateSetSecondChildList() {
        // repopulate omitting first child
        secondChildList.clear();
        for (int i = 0; i < firstChildList.size(); i++) {
            if (i == selectedChild) continue;
            secondChildList.add(firstChildList.get(i));
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
                    firstChild = firstChildList.get(selectedChild).getName();
                    secondChild = secondChildList.get(selectedRival).getName();
                }

                startActivity(FlipCoinActivity.createIntent(ChooseCoinActivity.this, firstChild, secondChild, headWins));

                finish();
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedChild = -1;
                secondChildList.clear();
                updateSetFirstChildList();
                setupSecondChildList();
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
            history = history.filter(firstChildList.get(selectedChild).getName());
        }

        ArrayAdapter<FlipCoinRecord> adapter = new HistoryAdapter(history.getArray());
        listHistory.removeAllViewsInLayout();
        listHistory.setAdapter(adapter);
        listHistory.setClickable(false);
    }

    private class ChildNameAdapter extends ArrayAdapter<IndividualConfig> {
        private final ListView listView;
        List<IndividualConfig> list;

        public ChildNameAdapter(List<IndividualConfig> list, ListView listView) {
            super(ChooseCoinActivity.this, R.layout.choosechild_list_item, list);
            this.list = list;
            this.listView = listView;
        }

        @Override
        public View getView(final int position, View itemView, ViewGroup parent) {
            // Make sure we have a view to work with (may have been given null)
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.choosechild_list_item, parent, false);
            }

            if (listView.isItemChecked(position)) {
                itemView.setBackgroundResource(R.color.design_default_color_secondary);
            } else {
                itemView.setBackgroundResource(R.color.design_default_color_background);
            }

            final IndividualConfig currentConfig = list.get(position);

            TextView nameText = itemView.findViewById(R.id.txtNameItem);
            nameText.setText(currentConfig.getName());
            ImageView img = itemView.findViewById(R.id.chooserImg);
            Bitmap bitmap = currentConfig.getBase64Bitmap();
            if (bitmap == null) {
                img.setImageResource(R.drawable.ic_baseline_face_24);
            } else {
                img.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 200, 200, false));
            }

            return itemView;
        }
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

            boolean win = currentHistory.getChoseHead() && currentHistory.getResult();
            String rivalText, chooserText;
            if (win) {
                chooserText = currentHistory.getChooser() + getString(R.string.winnerTag);
                rivalText = currentHistory.getRival();
            } else {
                chooserText = currentHistory.getChooser();
                rivalText = currentHistory.getRival() + getString(R.string.winnerTag);
            }
            chooser.setText(chooserText);
            rival.setText(rivalText);
            date.setText(currentHistory.getDate());

            if (currentHistory.getChoseHead()) {
                choice.setText(R.string.head_name);
            } else {
                choice.setText(R.string.tail_name);
            }

            if (currentHistory.getResult()) {
                result.setText(R.string.head_name);
            } else {
                result.setText(R.string.tail_name);
            }

            return itemView;
        }
    }
}
