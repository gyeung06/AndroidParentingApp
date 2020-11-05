package c.cmpt276.childapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseCoinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_coin);

        initializeButtons();
    }

    void initializeButtons () {

        Button Continue =findViewById(R.id.continuebutton);
        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(ChooseCoinActivity.this, FlipCoinActivity.class);
                startActivity(in);
            }

        });

    }

    public static Intent createIntent(Context context){
        Intent i = new Intent(context, ChooseCoinActivity.class);
        return i;
    }

}