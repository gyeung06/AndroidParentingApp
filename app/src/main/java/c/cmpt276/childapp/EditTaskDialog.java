package c.cmpt276.childapp;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import c.cmpt276.childapp.model.Task.ChildrenTask;
import c.cmpt276.childapp.model.Task.ChildrenTaskManager;
import c.cmpt276.childapp.model.config.ChildrenConfigCollection;
/**
 *
 *
 * class enable user to edit exist task, enable to add remove and edit.
 *
 */
public class EditTaskDialog extends Dialog implements android.view.View.OnClickListener {
    private ChildrenTaskManager allTasks = ChildrenConfigCollection.getInstance().getTaskList();
    /**
     * Code Review
     * assigned activity and d, but not yet used.
     */
    private Activity activity;
    private Dialog d;
    private Button save, delete, cancel;
    private int selected;

    public EditTaskDialog(Activity activityPassed, int selection) {
        super(activityPassed);
        this.activity = activityPassed;
        selected = selection;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.edit_task_dialog);
        save = (Button) findViewById(R.id.btn_save_close_task);
        delete = (Button) findViewById(R.id.btn_delete_task);
        cancel = (Button) findViewById(R.id.btn_cancel_task);
        save.setOnClickListener(EditTaskDialog.this);
        if (selected > -1) {
            populateFields();
            delete.setOnClickListener(EditTaskDialog.this);
        } else {

            delete.setEnabled(false);
            delete.setVisibility(View.INVISIBLE);
        }

        cancel.setOnClickListener(EditTaskDialog.this);

    }

    private void populateFields() {
        EditText edt = findViewById(R.id.edtTaskTitle);
        edt.setText(allTasks.getTask(selected).getTaskDescription());
    }

    @Override
    public void onClick(View v) {
        EditText edt = findViewById(R.id.edtTaskTitle);
        switch (v.getId()) {
            case R.id.btn_save_close_task:
                if (allTasks.contains(edt.getText().toString(), selected)) {
                    Toast.makeText(getContext(), R.string.info_warn_create_title, Toast.LENGTH_SHORT).show();
                    break;
                }
                saveTask();
                dismiss();
                break;
            case R.id.btn_cancel_task:
                dismiss();
                break;
            case R.id.btn_delete_task:
                deleteTask();
                dismiss();
                break;
            default:
                break;
        }
    }

    private void deleteTask() {
        if (selected > -1) {
            allTasks.removeTask(selected);
        }
    }

    private void saveTask() {
        EditText edt = findViewById(R.id.edtTaskTitle);
        if (selected > -1) {
            allTasks.getTask(selected).setTaskDescription(edt.getText().toString());
        } else {
            allTasks.addTask(new ChildrenTask(edt.getText().toString()));
        }
    }
}