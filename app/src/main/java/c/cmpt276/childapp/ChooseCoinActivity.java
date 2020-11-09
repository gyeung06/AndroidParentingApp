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
import c.cmpt276.childapp.model.config.ChildrenConfigCollection;

/**
 * Class for choosing which two children are competing
 */
public class ChooseCoinActivity extends AppCompatActivity {
    private ChildrenConfigCollection configs = ChildrenConfigCollection.getInstance();
    private boolean seeSelectedChildOnly = false , headWins = true;
    private int selectedChild = -1;
    private int selectedRival = -1 , offsetLocation = -1;;
    private List<Integer> hasFlipCoinPositions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setContentView(R.layout.activity_choose_coin);
        hasFlipCoinPositions = new ArrayList<>( configs.getFlipCoinEnabledChildrenPositions());
        setButtons();
        updateSetFirstChildList();
        updatePreviousWinner();
        updateHistory();
    }

    private void updatePreviousWinner() {
        final ListView chose = (ListView) findViewById(R.id.lsChoosingChild);
        final ListView rival = (ListView) findViewById(R.id.lsRival);
        if(!configs.getLastWinner().isEmpty() && !configs.getLastLoser().isEmpty()){
            int loserP = getPositionFromName(configs.getLastLoser()) , winnerP = getPositionFromName(configs.getLastWinner());
            if(loserP == -1 || winnerP == -1){
                Log.d("loser - ",String.valueOf(loserP));
                Log.d("winner -", String.valueOf(winnerP));
                return;
            }
             chose.setItemChecked(loserP,true);
             selectedChild = hasFlipCoinPositions.get(loserP);
             updateSetSecondChildList();
             if(winnerP>= offsetLocation){
                 rival.setItemChecked(winnerP-1,true);
                 selectedRival = hasFlipCoinPositions.get(winnerP);
             }else{
                 rival.setItemChecked(winnerP,true);
                 selectedRival = hasFlipCoinPositions.get(winnerP);
             }
             Log.d("autoselected loser",configs.get(selectedChild).getName());
            Log.d("autoselected winner",configs.get(selectedRival).getName());
        }
    }
    private int getPositionFromName(String name){
        int index = -1;
        for(int i = 0; i< hasFlipCoinPositions.size();i++){
            Log.d("doing", configs.get(hasFlipCoinPositions.get(i)).getName());
            if(configs.get(hasFlipCoinPositions.get(i)).getName().equals(name)){
                index = i;
            }
        }
        return index;
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

        final ListView listView = (ListView) findViewById(R.id.lsChoosingChild);

        List<String> listName = new ArrayList<>();
        for(int i = 0; i< hasFlipCoinPositions.size();i++){
            listName.add(configs.get(hasFlipCoinPositions.get(i)).getName());
        }
        String[] values = listName.toArray(new String[listName.size()]);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, android.R.id.text1, values);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setSelector(R.color.design_default_color_secondary);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(hasFlipCoinPositions.get(i) != selectedChild){
                    //listView.setSelection(hasFlipCoinPositions.get(i));
                    selectedChild = hasFlipCoinPositions.get(i);
                    Log.d("Selected",configs.get(selectedChild).getName());
                    selectedRival = -1;
                    updateSetSecondChildList();
                    if(seeSelectedChildOnly){
                        updateHistory();
                    }

                }
            }
        });




    }



    private void updateSetSecondChildList(){
        final ListView listView = (ListView) findViewById(R.id.lsRival);


        List<String> listName = new ArrayList<>();
        for(int i =0 ;i < hasFlipCoinPositions.size(); i++){

            if(configs.get(hasFlipCoinPositions.get(i)).getName().equals(configs.get(selectedChild).getName())){
                offsetLocation=i;
                continue; //>=
            }
            listName.add(configs.get(hasFlipCoinPositions.get(i)).getName());
        }
        final int ofLo = offsetLocation;
        String[] values = listName.toArray(new String[listName.size()]);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, android.R.id.text1, values);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(hasFlipCoinPositions.get(i) != selectedRival){

                    if(i >= ofLo){
                      //  listView.setSelection(i+1);
                        selectedRival = hasFlipCoinPositions.get(i+1);

                    }else {
                       // listView.setSelection(i);
                        selectedRival = hasFlipCoinPositions.get(i);
                    }
                    Log.d("Selected_Second",configs.get(selectedRival).getName());
                }
            }
        });
        listView.setSelector(R.color.design_default_color_secondary);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

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

        final CheckBox chkViewHis = findViewById(R.id.chkViewHistorySelected);
        chkViewHis.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(selectedChild<0){
                    Toast.makeText(getApplicationContext(),"Cannot filter, you have to select one first",Toast.LENGTH_SHORT).show();
                    chkViewHis.setChecked(false);
                    return;
                }
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
        ArrayAdapter<FlipCoinRecord> adapter;
           if(seeSelectedChildOnly){
               adapter = new HistoryAdapterFiltered(configs.get(selectedChild).getName());
           }else{
               adapter = new HistoryAdapter();
           }

            ListView listView = (ListView) findViewById(R.id.tableHistory);
            listView.removeAllViewsInLayout();
            listView.setAdapter(adapter);
            listView.setClickable(false);
    }





    private class HistoryAdapter extends ArrayAdapter<FlipCoinRecord> {
        public HistoryAdapter() {
            super(ChooseCoinActivity.this, R.layout.history_list_item, configs.getFlipCoinHistory().getArray());

        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // Make sure we have a view to work with (may have been given null)
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.history_list_item, parent, false);
            }
            FlipCoinRecord currentHistory;

            currentHistory = configs.getFlipCoinHistory().get(position);


            TextView head = (TextView) itemView.findViewById(R.id.txtHeadPlayer_item);
            head.setText(currentHistory.getHeadChild());
            TextView tail = (TextView) itemView.findViewById(R.id.txtTailPlayer_item);
            tail.setText(currentHistory.getTailChild());
            TextView date = (TextView) itemView.findViewById(R.id.txtTime_item);
            TextView win = (TextView) itemView.findViewById(R.id.txtWinner_item);

            date.setText(currentHistory.getDate());
            if(currentHistory.isHead()){
                win.setText("Head");
            }else {
                win.setText("Tail");
            }







            return itemView;
        }
    }
    private class HistoryAdapterFiltered extends ArrayAdapter<FlipCoinRecord> {
        String filterToDo;
        public HistoryAdapterFiltered(String filter) {
            super(ChooseCoinActivity.this, R.layout.history_list_item, configs.getFlipCoinHistory().filter(filter).getArray());
            filterToDo = filter;

        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // Make sure we have a view to work with (may have been given null)
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.history_list_item, parent, false);
            }
            FlipCoinRecord currentHistory;
            if(filterToDo.trim().isEmpty()){
                currentHistory = configs.getFlipCoinHistory().get(position);
            }else{
                currentHistory = configs.getFlipCoinHistory().filter(filterToDo).get(position);
            }

            TextView head = (TextView) itemView.findViewById(R.id.txtHeadPlayer_item);
            head.setText(currentHistory.getHeadChild());
            TextView tail = (TextView) itemView.findViewById(R.id.txtTailPlayer_item);
            tail.setText(currentHistory.getTailChild());
            TextView date = (TextView) itemView.findViewById(R.id.txtTime_item);
            TextView win = (TextView) itemView.findViewById(R.id.txtWinner_item);

            date.setText(currentHistory.getDate());
            if(currentHistory.isHead()){
                win.setText("Head");
            }else {
                win.setText("Tail");
            }



            return itemView;
        }
    }
}