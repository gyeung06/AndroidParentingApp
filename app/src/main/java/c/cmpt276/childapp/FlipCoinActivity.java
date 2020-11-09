package c.cmpt276.childapp;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
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

/**
 * Main screen for flipping coins
 */
public class FlipCoinActivity extends AppCompatActivity {
    ChildrenConfigCollection configs = ChildrenConfigCollection.getInstance();
    Button btnFlip;
    ImageView imgCoin;
    Random r = new Random();
    int side;
    String child, rival;
    boolean headWin, guestMode = false;
    MediaPlayer mediaPlayer;

    public static Intent createIntent(Context context, String firstChild, String secondChild, boolean winHead) {
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

        child = getIntent().getStringExtra("FirstChild");
        rival = getIntent().getStringExtra("SecondChild");
        headWin = getIntent().getBooleanExtra("WinHead", true);
        Log.d("headwin?", String.valueOf(headWin));

        if (child == null || child.isEmpty() || rival == null || rival.isEmpty()) {
            guestMode = true;
        }

        mediaPlayer = MediaPlayer.create(this, R.raw.sound);

        btnFlip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
                side = r.nextInt(2);
                boolean result = side == 0;

                if (side == 0) {
                    imgCoin.setImageResource(R.drawable.head);
                    Toast.makeText(FlipCoinActivity.this, "Heads!", Toast.LENGTH_LONG).show();
                } else if (side == 1) {
                    imgCoin.setImageResource(R.drawable.tail);
                    Toast.makeText(FlipCoinActivity.this, "Tails!", Toast.LENGTH_LONG).show();
                }

                RotateAnimation rotate = new RotateAnimation(0, 999999999,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(1800);
                imgCoin.startAnimation(rotate);

                if (!guestMode) {
                    FlipCoinRecord record = new FlipCoinRecord(child, rival, headWin);
                    record.setResult(result, Calendar.getInstance().getTime().toString());
                    configs.getFlipCoinHistory().add(record);

                    configs.setLastResultCandidate(child, rival);
                }
            }
        });
    }

    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    protected void onPause() {
        configs.save(this);
        super.onPause();
    }
}
