package c.cmpt276.childapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

public class BreathCircle extends View { //TODO unfinished
    public static final float SCALE_AMOUNT = 1.0f;
    public RectF boundingBox;
    private Paint paint;
    private float circleCenterX, circleCenterY, circleRadius;
    private float x, y;

    public BreathCircle(Context context) {
        super(context);

        // Create paint
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);

        // Set circle start radius
        circleRadius = 50.0f;

        // Set start x and y (this is the upper left hand corner)
        x = 100.0f;
        y = 100.0f;

        // Create boundingBox
        boundingBox = new RectF();
        boundingBox.left = x;
        boundingBox.top = y;
        boundingBox.right = x + (circleRadius * 2);
        boundingBox.bottom = y + (circleRadius * 2);

        // Set circleCenterX and circleCenterY (the center of the bounding box and circle)
        circleCenterX = x + circleRadius;
        circleCenterY = y + circleRadius;
    }

    public void scale(boolean scaleUp) {
        float scaleBy = (scaleUp) ? SCALE_AMOUNT : -SCALE_AMOUNT;

        // Update circleRadius
        circleRadius += scaleBy;

        // Update the bounding box
        boundingBox.left = x;
        boundingBox.top = y;
        boundingBox.right = x + (circleRadius * 2);
        boundingBox.bottom = y + (circleRadius * 2);

        // Update the circle center positions
        circleCenterX = x + circleRadius;
        circleCenterY = y + circleRadius;
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawCircle(circleCenterX, circleCenterY, circleRadius, paint);
    }
}
