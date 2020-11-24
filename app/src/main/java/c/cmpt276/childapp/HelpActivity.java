package c.cmpt276.childapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import c.cmpt276.childapp.model.config.ChildrenConfigCollection;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setContentView(R.layout.activity_help);
    }

    public static Intent createIntent(Context context) {
        return new Intent(context, HelpActivity.class);
    }

    protected void onPause() {
        ChildrenConfigCollection.getInstance().save(this);
        super.onPause();
    }

    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}