package pl.polak.circle;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

public class BorderCircleProgressBar extends View {

    private static Handler uiHandler = new Handler();

    private float circleCenterPointX;
    private float circleCenterPointY;
    private float circleRadius;

    private float innerCircleStrokeWidth;
    private int innerCircleColor;

    private float outerCircleStrokeWidth;
    private int outerCircleColor;

    private float circleStrokeWidth;
    private int circleColor;
    private float circleStartAngle;
    private int circlePercent;


    private float outerCircleRadius;
    private float innerCircleRadius;

    public BorderCircleProgressBar(Context context) {
        this(context, null);
    }

    public BorderCircleProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BorderCircleProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        loadViewAttribiutes(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    private void loadViewAttribiutes(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressBarWidget);
        circleStartAngle = typedArray.getFloat(R.styleable.CircleProgressBarWidget_circleStartAngle, 270);
        circleStrokeWidth = typedArray.getFloat(R.styleable.CircleProgressBarWidget_circleStrokeWidth, 20);
        circleColor = typedArray.getColor(R.styleable.CircleProgressBarWidget_circleColor, Color.DKGRAY);

        innerCircleStrokeWidth = typedArray.getFloat(R.styleable.CircleProgressBarWidget_innerCircleStrokeWidth, 2);
        innerCircleColor = typedArray.getColor(R.styleable.CircleProgressBarWidget_innerCircleColor, Color.BLACK);

        outerCircleStrokeWidth = typedArray.getFloat(R.styleable.CircleProgressBarWidget_outerCircleStrokeWidth, 2);
        outerCircleColor = typedArray.getColor(R.styleable.CircleProgressBarWidget_outerCircleColor, Color.BLACK);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        circleCenterPointX = w / 2;
        circleCenterPointY = h / 2;

        circleRadius = (w / 3) - circleStrokeWidth / 2;

        innerCircleRadius = (w / 3) - (circleStrokeWidth / 2) * 2.5f ;//+ (innerCircleStrokeWidth);
        outerCircleRadius = w / 3 + circleStrokeWidth / 3;

    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG | Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);

        drawInnerCircle(paint, canvas);
        drawOuterCircle(paint, canvas);
        drawCircleProgress(paint, canvas);
    }

    private void drawInnerCircle(Paint paint, Canvas canvas) {
        float delta = circleCenterPointX - innerCircleRadius;
        float circleSize = (circleCenterPointX - (delta / 2f)) * 2f;
        RectF circleBox = new RectF(delta, delta, circleSize, circleSize);
        paint.setStrokeWidth(innerCircleStrokeWidth);
        paint.setColor(innerCircleColor);
        canvas.drawArc(circleBox, circleStartAngle, 360, false, paint);
    }

    private void drawOuterCircle(Paint paint, Canvas canvas) {
        float delta = circleCenterPointX - outerCircleRadius;
        float circleSize = (circleCenterPointX - (delta / 2f)) * 2f;
        RectF circleBox = new RectF(delta, delta, circleSize, circleSize);
        paint.setStrokeWidth(outerCircleStrokeWidth);
        paint.setColor(outerCircleColor);
        canvas.drawArc(circleBox, circleStartAngle, 360, false, paint);
    }

    private void drawCircleProgress(Paint paint, Canvas canvas){
        float sweepAngle = 360 * circlePercent * 0.01f;
        float delta = circleCenterPointX - circleRadius;
        float circleSize = (circleCenterPointX - (delta / 2f)) * 2f;
        RectF circleBox = new RectF(delta, delta, circleSize, circleSize);
        paint.setStrokeWidth(circleStrokeWidth);
//        paint.setColor(circleColor);
        paint.setShader(new LinearGradient(0, 0, getWidth(), 0, new int[]{Color.GREEN, Color.YELLOW, Color.RED},new float[]{0f,0.5f,1f} , Shader.TileMode.CLAMP));
        canvas.drawArc(circleBox, circleStartAngle, sweepAngle, false, paint);
    }

    public void changePercentage(int percent){
        this.circlePercent = percent;
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        });
    }
}