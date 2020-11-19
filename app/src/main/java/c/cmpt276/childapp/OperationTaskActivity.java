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

public class OperationTaskActivity extends AppCompatActivity {

    private EditText etTask;
    private EditText etChild;

    public static Intent makeIntentForOperation(Context context) {
        return new Intent(context, OperationTaskActivity.class);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation_task);

        etTask = findViewById(R.id.etTask);
        etChild =findViewById(R.id.etChild);

        setupButtonCancel();
        setupButtonSave();

    }


    private void setupButtonCancel() {
        Button btn = findViewById(R.id.btnCancel);
        btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        OperationTaskActivity.this.finish();
                    }
                }
        );
    }


    private void setupButtonSave() {
        Button btn = findViewById(R.id.btnSave);
        btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String taskName = etTask.getText().toString();
                        String childName = etChild.getText().toString();

                        ChildrenTask task = new ChildrenTask(taskName, childName);
                        ChildrenTaskManager tasks = ChildrenTaskManager.getInstance();
                        tasks.addTask(task);
                        OperationTaskActivity.this.finish();
                    }
                }
        );
    }
}