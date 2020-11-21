package c.cmpt276.childapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import c.cmpt276.childapp.model.Task.ChildrenTask;
import c.cmpt276.childapp.model.Task.ChildrenTaskManager;
import c.cmpt276.childapp.model.config.ChildrenConfigCollection;

public class WhoseTurnActivity extends AppCompatActivity {
    ChildrenConfigCollection configs;
    ListView listView;
    Button btnAdd, btnRemove, btnEdit;

    public static Intent createIntent(Context context) {
        return new Intent(context, WhoseTurnActivity.class);
    }


    private BaseAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setContentView(R.layout.activity_configure_chooser);
        FloatingActionButton fab = findViewById(R.id.fabNew);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditTaskDialog ed = new EditTaskDialog(WhoseTurnActivity.this, -1);
                ed.show();
            }
        });

        configs = ChildrenConfigCollection.getInstance();

        listView = findViewById(R.id.LsChildren);

        updateList();

    }

    protected void onPause() {
        configs.save(this);
        super.onPause();
    }

    protected void onResume() {
        updateList();
        super.onResume();
    }


    private void updateList() {
        //todo populate list items
        ArrayAdapter<ChildrenTask> ada = new ChildNameAdapter(configs.getTaskList());
        listView.setAdapter(ada);
    }

    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


    private class ChildNameAdapter extends ArrayAdapter<ChildrenTask> {
        ChildrenTaskManager taskManager;

        public ChildNameAdapter(ChildrenTaskManager taskManager) {
            super(WhoseTurnActivity.this, R.layout.task_list_item, taskManager.getList());
            this.taskManager = taskManager;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // Make sure we have a view to work with (may have been given null)
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.task_list_item, parent, false);
            }

            ChildrenTask currentTask = taskManager.getTask(position);
            ImageView portrait = itemView.findViewById(R.id.imgChildPicture_TaskItem);
            portrait.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewItem(position);
                }
            });
            TextView nameText = itemView.findViewById(R.id.txtName_Child);
            nameText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewItem(position);
                }
            });
            String nextChild = currentTask.getNextChild();
            if (nextChild != null) {
                nameText.setText(nextChild);

//                portrait.setImageBitmap(configs.get(nextChild).getPortrait());//TODO might change
            } else {
                nameText.setText(R.string.warning_nochild_taskend);
            }
            TextView title = itemView.findViewById(R.id.txtTaskTitle);
            title.setText(currentTask.getTaskDescription());
            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewItem(position);
                }
            });
            Button edit = itemView.findViewById(R.id.btnEditTaskItem);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new EditTaskDialog(WhoseTurnActivity.this, position).show();
                }
            });

            return itemView;
        }

        private void viewItem(int position) {
            //Toast.makeText(getContext(),String.valueOf(position),Toast.LENGTH_SHORT).show();
            Log.d(String.valueOf(position), String.valueOf(position));
        }
    }

}
