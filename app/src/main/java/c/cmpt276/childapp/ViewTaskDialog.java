package c.cmpt276.childapp;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import c.cmpt276.childapp.model.Task.ChildrenTask;
import c.cmpt276.childapp.model.Task.ChildrenTaskManager;
import c.cmpt276.childapp.model.config.ChildrenConfigCollection;
/**
 * Code Reviews: add class description.
 *
 * 1.class for pop up a dialog when task been clicked. And show the related information
 * 2. the "cancel" feature not refresh properly. 
 */
public class ViewTaskDialog extends Dialog implements android.view.View.OnClickListener {
    private ChildrenConfigCollection configs = ChildrenConfigCollection.getInstance();
    private ChildrenTaskManager allTasks = ChildrenConfigCollection.getInstance().getTaskList();
    /**
     * Code Review
     * assigned activity and d, but not yet used.
     */
    private Activity activity;
    private Dialog d;
    private Button confirm, cancel;
    private int selected;

    public ViewTaskDialog(Activity activityPassed, int selection) {
        super(activityPassed);
        this.activity = activityPassed;
        selected = selection;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.view_task_dialog);
        confirm = findViewById(R.id.btn_confirm_done_child_view);
        cancel = findViewById(R.id.btn_cancel_task_view);
        confirm.setOnClickListener(ViewTaskDialog.this);
        cancel.setOnClickListener(ViewTaskDialog.this);
        updateFields();

    }

    private void updateFields() {
        ChildrenTask task = allTasks.getTask(selected);
        TextView ch = findViewById(R.id.txt_view_task_child_name);
        ImageView img = findViewById(R.id.img_view_task_child);
        if (task.getNextChild() == null) {
            confirm.setVisibility(View.INVISIBLE);
            confirm.setEnabled(false);
            ch.setText(R.string.warning_nochild_taskend);
            img.setImageResource(R.drawable.ic_baseline_face_24);
        } else {
            ch.setText(task.getNextChild());
            // img.setImageBitmap(configs.get(task.getNextChild()).getPortrait());//TODO ADD PORTRAIT
        }
        TextView txt = findViewById(R.id.txt_view_task_title);
        txt.setText(task.getTaskDescription());


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm_done_child_view:
                if (allTasks.getTask(selected).popNextChild() == null) {
                    Toast.makeText(getContext(), R.string.last_child_info_view, Toast.LENGTH_SHORT).show();
                }
                dismiss();
                break;
            case R.id.btn_cancel_task_view:
                dismiss();
                break;
            default:
                break;
        }
    }


}