package c.cmpt276.childapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import c.cmpt276.childapp.model.config.ChildrenConfigCollection;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ChildrenConfigCollection configs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sp = getSharedPreferences("USER_CHILDREN", MODE_PRIVATE);
        String jsonObj = sp.getString("CHILDREN_INFO", "");
        if (jsonObj == null) jsonObj = "";
        configs = ChildrenConfigCollection.loadWithJSONObject(jsonObj);

        initializeButtons();
    }

    protected void onPause() {
        configs.save(this);
        super.onPause();
    }

    void initializeButtons() {
        Button mainConfigBtn = findViewById(R.id.main_config);
        mainConfigBtn.setOnClickListener(this);

        Button flipBtn = findViewById(R.id.main_flip);
        flipBtn.setOnClickListener(this);

        Button timeBtn = findViewById(R.id.main_time);
        timeBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_config:
                startActivity(ConfigureChooserActivity.createIntent(MainActivity.this));
                break;

            case R.id.main_flip:
                startActivity(ChooseCoinActivity.createIntent(MainActivity.this));
                break;

            case R.id.main_time:
                startActivity(TimeoutActivity.createIntent(MainActivity.this));
                break;
        }
    }
}
