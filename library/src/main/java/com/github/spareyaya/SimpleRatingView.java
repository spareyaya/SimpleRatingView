package com.github.spareyaya;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * 功能描述：
 *
 * @author ONCEsama.M
 * Created on 2019/7/30.
 */
public class SimpleRatingView extends View {

    private static final int STAR = 1;
    private static final int HEART = 2;
    private final int DEFAULT_STAR_SIZE = 36; //dp
    private final int DEFAULT_NUM_STARS = 5;
    private final int DEFAULT_RATING = 0;
    private final int DEFAULT_PRIMARY_COLOR_STAR = Color.parseColor("#FFEA00");
    private final int DEFAULT_PRIMARY_COLOR_HEART = Color.parseColor("#DD2C00");
    private final int DEFAULT_SECONDARY_COLOR = Color.parseColor("#E0E0E0");

    private Paint paint;
    private Path path;

    private int starType;
    private float starSize;
    private float starSpacing;
    private int numStars;
    private int rating;
    private int primaryColor;
    private int secondaryColor;

    private OnRatingChangeListener onRatingChangeListener;

    public interface OnRatingChangeListener {
        void onRatingChange(int oldRating, int newRating);
    }

    public void setOnRatingChangeListener(OnRatingChangeListener onRatingChangeListener) {
        this.onRatingChangeListener = onRatingChangeListener;
    }

    public SimpleRatingView(Context context) {
        this(context, null);
    }

    public SimpleRatingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleRatingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SimpleRatingView);
        starType = a.getInteger(R.styleable.SimpleRatingView_starType, STAR);
        float defaultStarSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_STAR_SIZE, getResources().getDisplayMetrics());
        starSize = a.getDimension(R.styleable.SimpleRatingView_starSize, defaultStarSize);
        starSpacing = a.getDimension(R.styleable.SimpleRatingView_starSpacing, defaultStarSize / 3);
        numStars = a.getInteger(R.styleable.SimpleRatingView_numStars, DEFAULT_NUM_STARS);
        rating = a.getInteger(R.styleable.SimpleRatingView_rating, DEFAULT_RATING);
        int defaultPrimaryColor = starType == STAR ? DEFAULT_PRIMARY_COLOR_STAR : DEFAULT_PRIMARY_COLOR_HEART;
        primaryColor = a.getColor(R.styleable.SimpleRatingView_primaryColor, defaultPrimaryColor);
        secondaryColor = a.getColor(R.styleable.SimpleRatingView_secondaryColor, DEFAULT_SECONDARY_COLOR);
        a.recycle();

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL);

        path = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        int width = (int) (paddingLeft + paddingRight + starSize * numStars + starSpacing * (numStars - 1));
        int height = (int) (paddingTop + paddingBottom + starSize);

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();

        // 以24为基准做缩放
        float scale = starSize / 24;

        // 校正参数
        if (numStars <= 0) {
            throw new IllegalArgumentException("numStars不能小于等于0");
        }
        if (rating < 0) {
            rating = 0;
        }
        if (rating > numStars) {
            rating = numStars;
        }

        // 先绘制primaryColor的星星
        for (int i = 0; i < numStars; i++) {
            if (i < rating) {
                paint.setColor(primaryColor);
            } else {
                paint.setColor(secondaryColor);
            }

            canvas.save();
            canvas.translate(paddingLeft + (starSize + starSpacing) * i, paddingTop);

            path.reset();

            if (starType == STAR) {
                // 星星
                path.moveTo(scale * 12f, scale * 17.27f);
                path.lineTo(scale * 18.18f, scale * 21f);
                path.rLineTo(scale * -1.64f, scale * -7.03f);
                path.lineTo(scale * 22f, scale * 9.24f);
                path.rLineTo(scale * -7.19f, scale * -0.61f);
                path.lineTo(scale * 12f, scale * 2f);
                path.lineTo(scale * 9.19f, scale * 8.63f);
                path.lineTo(scale * 2f, scale * 9.24f);
                path.rLineTo(scale * 5.46f, scale * 4.73f);
                path.lineTo(scale * 5.82f, scale * 21f);
                path.close();
            } else if (starType == HEART) {
                // 心形
                path.moveTo(scale * 12f,scale * 21.35f);
                path.rLineTo(scale * -1.45f,scale * -1.32f);
                path.cubicTo(scale * 5.4f,scale * 15.36f,scale * 2f,scale * 12.28f,scale * 2f,scale * 8.5f);
                path.cubicTo(scale * 2f,scale * 5.42f, scale * 4.42f,scale * 3f, scale * 7.5f,scale * 3f);
                path.rCubicTo(scale * 1.74f,scale * 0f, scale * 3.41f,scale * 0.81f, scale * 4.5f,scale * 2.09f);
                path.cubicTo(scale * 13.09f,scale * 3.81f, scale * 14.76f,scale * 3f, scale * 16.5f,scale * 3f);
                path.cubicTo(scale * 19.58f,scale * 3f, scale * 22f,scale * 5.42f, scale * 22f,scale * 8.5f);
                path.rCubicTo(scale * 0f,scale * 3.78f, scale *  -3.4f,scale * 6.86f, scale *-8.55f,scale * 11.54f);
                path.lineTo(scale * 12f,scale * 21.35f);
                path.close();
            } else {

            }

            canvas.drawPath(path, paint);
            canvas.restore();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float dx = event.getX();
        float dy = event.getY();

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();

        int oldRating = rating;
        int newRating = rating;
        float dxx;
        boolean isClick = false;

        // 计算点击事件产生在哪个星星上
        if (dy > paddingTop && dy <= paddingTop + starSize) {
            for (int i = 1; i <= numStars; i++) {
                dxx = dx - paddingLeft - (starSize + starSpacing) * (i - 1);
                if (dxx > 0 && dxx < starSize) {
                    newRating = i;
                    isClick = true;
                    break;
                }
            }
        }

        int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (isClick) {
                    setRating(newRating);
                    if (onRatingChangeListener != null) {
                        onRatingChangeListener.onRatingChange(oldRating, newRating);
                    }
                }
                break;

            case MotionEvent.ACTION_UP:
                performClick();
                break;

        }

        return true;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    public void setStarType(int starType) {
        this.starType = starType;
        invalidate();
    }

    public void setStarSize(float starSize) {
        this.starSize = starSize;
        invalidate();
    }

    public void setStarSpacing(float starSpacing) {
        this.starSpacing = starSpacing;
        invalidate();
    }

    public void setNumStars(int numStars) {
        this.numStars = numStars;
        invalidate();
    }

    public void setRating(int rating) {
        this.rating = rating;
        invalidate();
    }

    public void setPrimaryColor(int primaryColor) {
        this.primaryColor = primaryColor;
        invalidate();
    }

    public void setSecondaryColor(int secondaryColor) {
        this.secondaryColor = secondaryColor;
        invalidate();
    }
}
