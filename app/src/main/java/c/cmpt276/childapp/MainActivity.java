package c.cmpt276.childapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeButtons();
    }

    void initializeButtons () {
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
                startActivity(ConfigureActivity.createIntent(MainActivity.this, -1));
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


