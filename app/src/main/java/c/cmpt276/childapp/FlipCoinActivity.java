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

import java.util.Calendar;
import java.util.Random;

import c.cmpt276.childapp.model.FlipCoinHistory.FlipCoinRecord;
import c.cmpt276.childapp.model.config.ChildrenConfigCollection;

public class FlipCoinActivity extends AppCompatActivity {
    ChildrenConfigCollection configs = ChildrenConfigCollection.getInstance();
    Button btnFlip;
    ImageView imgCoin;
    Random r = new Random();
    int side;
    int child, rival;
    boolean headWin, win, guestMode = false;
    MediaPlayer mediaPlayer;

    public static Intent createIntent(Context context, int firstChild, int secondChild, int winHead) {
        Intent in = new Intent(context, FlipCoinActivity.class);
        in.putExtra("FirstChild", firstChild);
        in.putExtra("SecondChild", secondChild);
        in.putExtra("WinHead", winHead);

        return in;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setContentView(R.layout.activity_flip_coin);

        btnFlip = findViewById(R.id.flip_button);
        imgCoin = findViewById(R.id.iv_coin);

        child = getIntent().getIntExtra("FirstChild", -1);
        rival = getIntent().getIntExtra("SecondChild", -1);
        int winHead = getIntent().getIntExtra("WinHead", 1);
        headWin = winHead == 1;

        if (child < 0 || rival < 0) guestMode = true;

        mediaPlayer = MediaPlayer.create(this, R.raw.sound);

        btnFlip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
                side = r.nextInt(2);

                if (side == 0) {
                    imgCoin.setImageResource(R.drawable.head);
                    Toast.makeText(FlipCoinActivity.this, "Heads!", Toast.LENGTH_LONG).show();
                    win = headWin;

                } else if (side == 1) {
                    imgCoin.setImageResource(R.drawable.tail);
                    Toast.makeText(FlipCoinActivity.this, "Tails!", Toast.LENGTH_LONG).show();
                    win = !headWin;
                }

                RotateAnimation rotate = new RotateAnimation(0, 999999999,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(1800);
                imgCoin.startAnimation(rotate);

                if (!guestMode) {
                    FlipCoinRecord record;
                    if (headWin) {
                        record = new FlipCoinRecord(configs.get(child).getName(), configs.get(rival).getName());
                    } else {
                        record = new FlipCoinRecord(configs.get(rival).getName(), configs.get(child).getName());
                    }
                    record.setResult(headWin, Calendar.getInstance().getTime().toString());
                    if (win) {
                        configs.setLastResultCandidate(configs.get(child).getName(), configs.get(rival).getName());
                    } else {
                        configs.setLastResultCandidate(configs.get(rival).getName(), configs.get(child).getName());
                    }

                    configs.getFlipCoinHistory().add(record);
                }
            }
        });
    }

    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    protected void onPause() {
        SharedPreferences sp = getSharedPreferences("USER_CHILDREN", MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.clear();
        ed.putString("CHILDREN_INFO", configs.getJSON());
        ed.apply();
        super.onPause();
    }
}
