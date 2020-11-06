package c.cmpt276.childapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import c.cmpt276.childapp.model.FlipCoinHistory.HistoryCollection;
import c.cmpt276.childapp.model.config.ChildrenConfigCollection;

public class ChooseCoinActivity extends AppCompatActivity {
    private ChildrenConfigCollection configs = ChildrenConfigCollection.getInstance();
    private boolean seeSelectedChildOnly = false , headWins = true;
    private int selectedChild = -1;
    private int selectedRival = -1;
    private int[] hasFlipCoinPositions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setContentView(R.layout.activity_choose_coin);
        hasFlipCoinPositions = configs.getFlipCoinEnabledChildrenPositions();
        setButtons();
        updateSetFirstChildList();
        updateHistory();
    }
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
    protected void onPause(){
        SharedPreferences sp = getSharedPreferences("USER_CHILDREN", MODE_PRIVATE);;
        SharedPreferences.Editor ed = sp.edit();
        ed.clear();
        ed.putString("CHILDREN_INFO",configs.getJSON());
        ed.commit();
        super.onPause();
    }
    private void updateSetFirstChildList() {
        int lastLoser = -1, lastWinner = -1;
        final ListView listView = (ListView) findViewById(R.id.lsChoosingChild);

        List<String> listName = new ArrayList<>();
        for(int i = 0; i< hasFlipCoinPositions.length;i++){
            listName.add(configs.get(hasFlipCoinPositions[i]).getName());
            if(configs.get(hasFlipCoinPositions[i]).getName().equals( configs.getLastLoser())){
                lastLoser = hasFlipCoinPositions[i];
            }else if(configs.get(hasFlipCoinPositions[i]).getName().equals(configs.getLastWinner())){
                lastWinner = i;
            }
        }
        String[] values = listName.toArray(new String[listName.size()]);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(hasFlipCoinPositions[i] != selectedChild){
                    listView.setSelection(hasFlipCoinPositions[i]);
                    selectedChild = hasFlipCoinPositions[i];
                    Log.d("Selected",configs.get(selectedChild).getName());
                    updateSetSecondChildList(-1);

                }
            }
        });
        listView.setSelector(R.color.design_default_color_secondary);

        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        if(lastLoser != -1 && lastWinner!=-1){
            listView.setSelection(lastLoser);
            selectedChild = lastLoser;
            updateSetSecondChildList(lastWinner);
        }
    }
    private void updateSetSecondChildList(int counterWinner){
        final ListView listView = (ListView) findViewById(R.id.lsRival);
        int offsetLocation = -1;
        List<String> listName = new ArrayList<>();
        for(int i =0 ;i < hasFlipCoinPositions.length; i++){

            if(configs.get(hasFlipCoinPositions[i]).getName().equals(configs.get(selectedChild).getName())){
                offsetLocation=i;
                continue; //>=
            }
            listName.add(configs.get(hasFlipCoinPositions[i]).getName());
        }
        final int ofLo = offsetLocation;
        String[] values = listName.toArray(new String[listName.size()]);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(hasFlipCoinPositions[i] != selectedRival){

                    if(i >= ofLo){
                        listView.setSelection(i+1);
                        selectedRival = hasFlipCoinPositions[i+1];
                    }else {
                        listView.setSelection(i);
                        selectedRival = hasFlipCoinPositions[i];
                    }
                    Log.d("Selected_Second",configs.get(selectedRival).getName());
                }
            }
        });
        listView.setSelector(R.color.design_default_color_secondary);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        if(counterWinner != -1){
            if(counterWinner >= ofLo){
                listView.setSelection(counterWinner-1);
            }else{
                listView.setSelection(counterWinner);
            }
            Log.d("Counter winner: ", configs.get(counterWinner).getName());
            selectedRival = counterWinner;
        }
    }


    private void setButtons() {
        Button cont = findViewById(R.id.continuebutton);
        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int k = headWins? 1:0;
                startActivity(FlipCoinActivity.createIntent(ChooseCoinActivity.this,selectedChild,selectedRival,k));
                finish();
            }
        });


        final RadioButton radHead = findViewById(R.id.rdbHead);
        final RadioButton radTail = findViewById(R.id.rdbTail);
        radHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radTail.setChecked(false);
                headWins = true;
                Toast.makeText(getApplicationContext(),"Head selected",Toast.LENGTH_SHORT).show();
            }
        });
        radTail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radHead.setChecked(false);
                headWins=false;
                Toast.makeText(getApplicationContext(),"Tail selected",Toast.LENGTH_SHORT).show();
            }
        });

        CheckBox chkViewHis = findViewById(R.id.chkViewHistorySelected);
        chkViewHis.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                seeSelectedChildOnly = b;
                updateHistory();
            }
        });
    }


    public static Intent createIntent(Context context){
        Intent i = new Intent(context, ChooseCoinActivity.class);
        return i;
    }
    private void updateHistory() {

        TableLayout table = findViewById(R.id.tableHistory);
        table.removeAllViews();
        HistoryCollection history = configs.getFlipCoinHistory();
        if(seeSelectedChildOnly && selectedChild >= 0){
            history = history.filter(configs.get(selectedChild).getName());
        }

        for(int j = -1 ;j < history.size();j++){

            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.MATCH_PARENT ,1f));
            table.addView(row);

            TextView text0 = new TextView(this);
            text0.setPadding(0,0,0,0);
            text0.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT,1f));
            row.addView(text0);


            TextView text1 = new TextView(this);
            text1.setPadding(0,0,0,0);
            text1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT,1f));
            row.addView(text1);


            TextView text2 = new TextView(this);
            text2.setPadding(0,0,0,0);
            text2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT,1f));
            row.addView(text2);


            TextView text3 = new TextView(this);
            text3.setPadding(0,0,0,0);
            text3.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT,1f));
            row.addView(text3);
            String win = "";


            if(j == -1){
                text0.setText("Date");
                text1.setText("Head player");
                text2.setText("Tail player");
                text3.setText("Winner");
            }else{
                if(history.get(j).isHead()) {
                    win = "Head";
                }else {
                    win = "Tail";
                }
                text0.setText(history.get(j).getDate());
                text1.setText(history.get(j).getHeadChild());
                text2.setText(history.get(j).getTailChild());
                text3.setText(win);
            }
        }
    }



    private void lockTextView(TextView textView){
        textView.setMinWidth(textView.getWidth());
        textView.setMaxWidth(textView.getWidth());
        textView.setMaxHeight(textView.getHeight());
        textView.setMinHeight(textView.getHeight());
    }
}