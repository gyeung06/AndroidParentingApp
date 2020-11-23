package c.cmpt276.childapp.model.config;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Struct for every config for each child
 */
public class IndividualConfig {
    private String name;
    private boolean flipCoin;
    private String base64Img;
    //  private boolean timeoutTimer;

    public IndividualConfig(String name, boolean flipCoin, String base64Img) {
        set(name, flipCoin, base64Img);
    }

    public String getBase64Img() {
        return base64Img;
    }

    public Bitmap getBase64Bitmap(){
        Bitmap bitmap;
        if (base64Img == null || base64Img.isEmpty()){
            return null;
        } else {
            InputStream stream = new ByteArrayInputStream(Base64.decode(base64Img.getBytes(), Base64.DEFAULT));
            bitmap = BitmapFactory.decodeStream(stream);
        }
        return bitmap;
    }

    public String getName() {
        return name;
    }

    public boolean getFlipCoin() {
        return flipCoin;
    }

//    public boolean getTimeoutTimer() {
//        return timeoutTimer;
//    }

    public void set(String name, boolean flipCoin, String base64Img) {
        this.name = name;
        this.flipCoin = flipCoin;
        this.base64Img = base64Img;
    }
}
