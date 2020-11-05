package c.cmpt276.childapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

public class FlipCoinActivity extends AppCompatActivity {

    Button b;
    ImageView iv;

    Random r;

    int side;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flip_coin);

        b= findViewById(R.id.flip_button);
        iv = findViewById(R.id.iv_coin);

        r = new Random();

        b.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
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

    public static Intent createIntent(Context context){
        Intent i = new Intent(context, FlipCoinActivity.class);
        return i;
    }


}