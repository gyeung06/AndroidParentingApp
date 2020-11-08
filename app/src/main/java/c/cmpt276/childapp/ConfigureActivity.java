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
    private boolean flipCoinEnable;

    Button btnSave, btnDelete, btnSaveClose;
    CheckBox  chkFlipCoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int position = getIntent().getIntExtra("CHILD_SELECTED", -9);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setContentView(R.layout.activity_configure);

        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);
        btnSaveClose = findViewById(R.id.btnSaveAndClose);
        //chkTimer = findViewById(R.id.chkTimer);
        chkFlipCoin = findViewById(R.id.chkFlipCoin);

        setListeners();

        if (position >= 0) {
            // Log.d("loading","loading");
            editIndex = position;
            editorMode = true;
            autoPopulateFields();
        }
    }

    /**
     * create intent
     *
     * @param context            context of the origin
     * @param indexOfChildConfig if less than 0 then means this is creating a new IndividualConfig. if greater than 0, it will access and load info from ChildrenConfigCollection using the index provided.
     * @return the intent to be started
     */
    public static Intent createIntent(Context context, int indexOfChildConfig) {
        Intent i = new Intent(context, ConfigureActivity.class);
        i.putExtra("CHILD_SELECTED", indexOfChildConfig);
        return i;
    }

    protected void onPause() {
        configs.save(this);
        super.onPause();
    }

    private void saveData(boolean close) {
        String name = ((EditText) findViewById(R.id.edtName)).getText().toString().trim();
        if (name.isEmpty()) {
            Toast.makeText(ConfigureActivity.this, "Cannot save because name is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (editorMode) {
            configs.get(editIndex).set(name, flipCoinEnable);
        } else {
            configs.add(new IndividualConfig(name, flipCoinEnable));
            editorMode = true;
            editIndex = configs.size() - 1;
        }

        configs.save(this);
        Toast.makeText(ConfigureActivity.this, "Saved", Toast.LENGTH_SHORT).show();

        if (close) {
            finish();
        }
    }

    private void autoPopulateFields() {
        //CheckBox timer = findViewById(R.id.chkTimer);
        CheckBox fc = findViewById(R.id.chkFlipCoin);
        EditText edtName = findViewById(R.id.edtName);
        edtName.setText(configs.get(editIndex).getName());
        fc.setChecked(configs.get(editIndex).getFlipCoin());
        //timer.setChecked(configs.get(editIndex).getTimeoutTimer());
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void setListeners() {
//        chkTimer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                timerEnable = b;
//            }
//        });

        chkFlipCoin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                flipCoinEnable = b;
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData(false);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editorMode) {
                    configs.delete(editIndex);
                    Toast.makeText(ConfigureActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(ConfigureActivity.this, "Cannot delete because your are creating a new one", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnSaveClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData(true);
            }
        });
    }
}
