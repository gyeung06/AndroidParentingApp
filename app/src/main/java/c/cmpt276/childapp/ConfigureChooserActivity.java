package c.cmpt276.childapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import c.cmpt276.childapp.model.config.ChildrenConfigCollection;
import c.cmpt276.childapp.model.config.IndividualConfig;

/**
 * Choose which child to config
 */
public class ConfigureChooserActivity extends AppCompatActivity {
    ChildrenConfigCollection configs;
    ListView listView;

    public static Intent createIntent(Context context) {
        return new Intent(context, ConfigureChooserActivity.class);
    }

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
                startActivity(ConfigureActivity.createIntent(getApplicationContext(), ""));
                //finish();
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

    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void updateList() {
        ArrayAdapter<IndividualConfig> adapter = new ChildNameAdapter(configs.getArray());
        listView.setAdapter(adapter);
    }

    private class ChildNameAdapter extends ArrayAdapter<IndividualConfig> {
        List<IndividualConfig> list;

        public ChildNameAdapter(List<IndividualConfig> list) {
            super(ConfigureChooserActivity.this, R.layout.child_list_item, list);
            this.list = list;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // Make sure we have a view to work with (may have been given null)
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.child_list_item, parent, false);
            }

            IndividualConfig currentConfig = list.get(position);

            TextView nameText = itemView.findViewById(R.id.txtNameItem);
            nameText.setText(currentConfig.getName());
            ImageView img = itemView.findViewById(R.id.chooserImg);
            String image = currentConfig.getBase64Img();
            if (image == null || image.isEmpty()){
                img.setImageResource(R.drawable.ic_baseline_sentiment_satisfied_24);
            } else {
                InputStream stream = new ByteArrayInputStream(Base64.decode(image.getBytes(), Base64.DEFAULT));
                Bitmap bitmap = BitmapFactory.decodeStream(stream);
                img.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 200, 200,false));
            }



            Button edit = itemView.findViewById(R.id.btnEditItem);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(ConfigureActivity.createIntent(getApplicationContext(), list.get(position).getName()));
                }
            });

            return itemView;
        }
    }
}
