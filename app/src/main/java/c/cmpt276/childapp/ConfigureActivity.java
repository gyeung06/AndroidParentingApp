package c.cmpt276.childapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import c.cmpt276.childapp.model.config.ChildrenConfigCollection;
import c.cmpt276.childapp.model.config.IndividualConfig;

public class ConfigureActivity extends AppCompatActivity {
    private ChildrenConfigCollection configs = ChildrenConfigCollection.getInstance();
    private boolean editorMode = false;
    private int editIndex = -1;
    private boolean flipCoinEnable , timerEnable;
    /**
     * create intent
     * @param context context of the origin
     * @param indexOfChildConfig if less than 0 then means this is creating a new IndividualConfig. if greater than 0, it will access and load info from ChildrenConfigCollection using the index provided.
     * @return the intent to be started
     */
    public static Intent createIntent(Context context, int indexOfChildConfig){
        Intent i = new Intent(context,ConfigureActivity.class);
        i.putExtra("CHILD_SELECTED", indexOfChildConfig);
        return i;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int position = getIntent().getIntExtra("CHILD_SELECTED",-9);
        if(position > 0){
            editIndex = position;
            editorMode = true;
            autoPopulateFields();
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setContentView(R.layout.activity_configure);
        setListeners();
    }

    private void setListeners() {
        CheckBox timer = findViewById(R.id.chkTimer);
        CheckBox fc = findViewById(R.id.chkFlipCoin);
        timer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                timerEnable = b;
            }
        });
        fc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                flipCoinEnable = b;
            }
        });
        Button save = findViewById(R.id.btnSave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
    }

    private void saveData() {
        String name = ((EditText)findViewById(R.id.edtName)).getText().toString().trim();
        if(name.isEmpty()){
            Toast.makeText(ConfigureActivity.this,"Cannot save because name is empty",Toast.LENGTH_SHORT).show();
            return;
        }
        if(editorMode){
            configs.get(editIndex).set(name,flipCoinEnable,timerEnable);
        }else{
            configs.add(new IndividualConfig(name,flipCoinEnable,timerEnable));
            editorMode =true;
            editIndex = configs.size()-1;
        }
    }

    private void autoPopulateFields() {
        CheckBox timer = findViewById(R.id.chkTimer);
        CheckBox fc = findViewById(R.id.chkFlipCoin);
        EditText edtName =findViewById(R.id.edtName);
        edtName.setText(configs.get(editIndex).getName());
        fc.setChecked(configs.get(editIndex).getFlipCoin());
        timer.setChecked(configs.get(editIndex).getTimeoutTimer());
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}