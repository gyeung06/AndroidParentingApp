package c.cmpt276.childapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

import c.cmpt276.childapp.model.config.ChildrenConfigCollection;

public class FlipCoinActivity extends AppCompatActivity {
    ChildrenConfigCollection configs = ChildrenConfigCollection.getInstance();
    Button b;
    ImageView iv;
    Random r;
    int side;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setContentView(R.layout.activity_flip_coin);
        b= findViewById(R.id.flip_button);
        iv = findViewById(R.id.iv_coin);
        r = new Random();
        final MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.sound);

        b.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mediaPlayer.start();
                side = r.nextInt(2);

                if (side == 0){
                    iv.setImageResource(R.drawable.head);
                    Toast.makeText(FlipCoinActivity.this,"Heads!",Toast.LENGTH_SHORT).show();
                }
                else if (side == 1){
                    iv.setImageResource(R.drawable.tail);
                    Toast.makeText(FlipCoinActivity.this,"Tails!",Toast.LENGTH_SHORT).show();
                }

                RotateAnimation rotate = new RotateAnimation(0,999999999,
                        RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
                rotate.setDuration(800);
                iv.startAnimation(rotate);
            }

        });
    }
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
    protected void onPause(){
        SharedPreferences sp = getSharedPreferences("USER_CHILDREN", MODE_PRIVATE);;
        SharedPreferences.Editor ed = sp.edit();
        ed.clear();
        ed.putString("CHILDREN_INFO",configs.getJSON());
        ed.commit();
        super.onPause();
    }
    public static Intent createIntent(Context context, int firstChild, int secondChild, int winHead){
        Intent in = new Intent(context, FlipCoinActivity.class);
        in.putExtra("FirstChild",firstChild);
        in.putExtra("SecondChild",secondChild);
        in.putExtra("WinHead",winHead);

        return in;
    }


}