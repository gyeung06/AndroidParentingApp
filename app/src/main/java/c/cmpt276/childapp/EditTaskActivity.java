package c.cmpt276.childapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import c.cmpt276.childapp.model.Task.ChildrenTask;
import c.cmpt276.childapp.model.Task.ChildrenTaskManager;

public class EditTaskActivity extends AppCompatActivity {

    private EditText originalTask;
    private EditText originalChild;
    private EditText newTask;
    private EditText newChild;

    public static Intent makeIntentForEdit(Context context) {
        return new Intent(context, EditTaskActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        originalTask = findViewById(R.id.originalTask);
        originalChild =findViewById(R.id.originalChild);
        newTask = findViewById(R.id.newTask);
        newChild =findViewById(R.id.newChild);

        setupButtonCancel();
        setupButtonSave();
    }

    private void setupButtonCancel() {
        Button btn = findViewById(R.id.btnCancel2);
        btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EditTaskActivity.this.finish();
                    }
                }
        );
    }

    private void setupButtonSave() {
        Button btn = findViewById(R.id.btnSave2);
        btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String taskName = newTask.getText().toString();
                        String childName = newChild.getText().toString();

                        ChildrenTask task = new ChildrenTask(taskName, childName);
                        ChildrenTaskManager tasks = ChildrenTaskManager.getInstance();
                        tasks.addTask(task);
                        EditTaskActivity.this.finish();
                    }
                }
        );
    }
}