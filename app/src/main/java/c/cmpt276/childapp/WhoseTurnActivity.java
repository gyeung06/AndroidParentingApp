package c.cmpt276.childapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import c.cmpt276.childapp.model.Task.ChildrenTask;
import c.cmpt276.childapp.model.Task.ChildrenTaskManager;

public class WhoseTurnActivity extends AppCompatActivity {

    Button btnAdd, btnRemove, btnEdit;

    public static Intent createIntent(Context context) {
        return new Intent(context, WhoseTurnActivity.class);
    }

    private ChildrenTaskManager tasks;
    private BaseAdapter adapter;

    private List<String> initialList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whose_turn);

        btnAdd = findViewById(R.id.buttonAdd);
        btnRemove = findViewById(R.id.buttonRemove);
        btnEdit = findViewById(R.id.buttonEdit);

        setOnclick();

        tasks = ChildrenTaskManager.getInstance();
        tasks.addTask(new ChildrenTask("initial task","initial child"));


    }

    private void setOnclick() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAdd = OperationTaskActivity.makeIntentForOperation(WhoseTurnActivity.this);
                startActivity(intentAdd);
            }
        });

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRemove = OperationTaskActivity.makeIntentForOperation(WhoseTurnActivity.this);
                startActivity(intentRemove);
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEdit = EditTaskActivity.makeIntentForEdit(WhoseTurnActivity.this);
                startActivity(intentEdit);
            }
        });
    }


}