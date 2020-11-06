package c.cmpt276.childapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import c.cmpt276.childapp.model.config.ChildrenConfigCollection;
import c.cmpt276.childapp.model.config.IndividualConfig;

public class ConfigureChooserActivity extends AppCompatActivity {
    ChildrenConfigCollection manager = ChildrenConfigCollection.getInstance();
    public static Intent createIntent(Context context){
        Intent i = new Intent(context,ConfigureChooserActivity.class);
        return i;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setContentView(R.layout.activity_configure_chooser);
        FloatingActionButton fab = findViewById(R.id.fabNew);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(ConfigureActivity.createIntent(getApplicationContext(), -1));
                //finish();
            }
        });
        updateList();
    }
    protected void onPause(){
        SharedPreferences sp = getSharedPreferences("USER_CHILDREN", MODE_PRIVATE);;
        SharedPreferences.Editor ed = sp.edit();
        ed.clear();
        ed.putString("CHILDREN_INFO",manager.getJSON());
        ed.commit();
        super.onPause();
    }
    protected void onResume() {
        updateList();
        super.onResume();
    }
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
    private void updateList(){
        ArrayAdapter<IndividualConfig> adapter = new ChildNameAdapter();
        ListView listView = (ListView) findViewById(R.id.LsChildren);
        listView.setAdapter(adapter);
    }
    private class ChildNameAdapter extends ArrayAdapter<IndividualConfig> {
        public ChildNameAdapter() {
            super(ConfigureChooserActivity.this, R.layout.child_list_item, manager.getArray());
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // Make sure we have a view to work with (may have been given null)
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.child_list_item, parent, false);
            }

            IndividualConfig currentConfig = manager.get(position);

            TextView nameText = (TextView) itemView.findViewById(R.id.txtNameItem);
            nameText.setText(currentConfig.getName());

            Button edit = itemView.findViewById(R.id.btnEditItem);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Log.d("Choosed",String.valueOf(position));
                    startActivity(ConfigureActivity.createIntent(getApplicationContext(), position));
                    //finish();
                }
            });

            return itemView;
        }

    }
}


