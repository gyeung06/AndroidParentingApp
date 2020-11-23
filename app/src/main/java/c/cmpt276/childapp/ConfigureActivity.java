package c.cmpt276.childapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import c.cmpt276.childapp.model.config.ChildrenConfigCollection;
import c.cmpt276.childapp.model.config.IndividualConfig;

import static android.media.MediaRecorder.VideoSource.CAMERA;

/**
 * UI element when configuring a child
 */
public class ConfigureActivity extends AppCompatActivity {
    Button btnSave, btnDelete, btnSaveClose, btnPhoto;
    EditText txtName;
    CheckBox chkFlipCoin;
    ImageView preview;

    private ChildrenConfigCollection configs = ChildrenConfigCollection.getInstance();
    private boolean editorMode = false;
    private String editingChild;
    private boolean flipCoinEnable;

    /**
     * create intent
     *
     * @param context   context of the origin
     * @param childName if empty then create a new IndividualConfig, otherwise load from ChildrenConfigCollection.
     * @return the intent to be started
     */
    public static Intent createIntent(Context context, String childName) {
        Intent i = new Intent(context, ConfigureActivity.class);
        i.putExtra("CHILD_SELECTED", childName);
        return i;
    }

    protected void onPause() {
        configs.save(this);
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        editingChild = getIntent().getStringExtra("CHILD_SELECTED");

        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);
        btnSaveClose = findViewById(R.id.btnSaveAndClose);
        chkFlipCoin = findViewById(R.id.chkFlipCoin);
        btnPhoto = findViewById(R.id.config_photobtn);


        txtName = findViewById(R.id.edtName);
        preview = findViewById(R.id.config_thumbnail);

        setListeners();

        if (editingChild != null && !editingChild.isEmpty()) {
            editorMode = true;
            autoPopulateFields();
        }
    }

    private void saveData(boolean close) {
        String name = txtName.getText().toString().trim();
        if (name.isEmpty()) {
            Toast.makeText(ConfigureActivity.this, "Cannot save because name is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (editorMode) {
            configs.get(editingChild).set(name, flipCoinEnable);
        } else {
            if (configs.contains(name)) {
                Toast.makeText(ConfigureActivity.this, "Cannot save because there is already a same name", Toast.LENGTH_SHORT).show();
                return;
            }
            configs.add(new IndividualConfig(name, flipCoinEnable));
        }

        configs.save(this);
        Toast.makeText(ConfigureActivity.this, "Saved", Toast.LENGTH_SHORT).show();

        if (close) {
            finish();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void autoPopulateFields() {
        //CheckBox timer = findViewById(R.id.chkTimer);
        CheckBox fc = findViewById(R.id.chkFlipCoin);
        EditText edtName = findViewById(R.id.edtName);
        edtName.setText(editingChild);
        fc.setChecked(configs.get(editingChild).getFlipCoin());
        //timer.setChecked(configs.get(editIndex).getTimeoutTimer());
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
                    configs.delete(editingChild);
                    Toast.makeText(ConfigureActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(ConfigureActivity.this, "Cannot delete because you are creating a new one", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnSaveClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData(true);
            }
        });

        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap thumb = (Bitmap) data.getExtras().get("data");
        preview.setImageBitmap(thumb);
    }
}
