package com.rongteng.base.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.rongteng.base.R;


/**
 * @author xiaanming
 */
public class RoundProgressBar extends View {

    /**
     * 画笔
     */
    private Paint paint;

    /**
     * 圆环的颜色
     */
    private int roundColor;

    /**
     * 圆环进度颜色
     */
    private int roundProgressColor;

    /**
     * 文字颜色
     */
    private int textColor;

    /**
     * 文字大小
     */
    private float textSize;

    /**
     * 圆环宽度
     */
    private float roundWidth;

    /**
     * 最大进度
     */
    private int max;

    /**
     * 当前进度
     */
    private int progress;

    /**
     * 文字是否显示
     */
    private boolean textIsDisplayable;

    private int style;
    private int percent;//显示的文字进度
    public static final int STROKE = 0;
    public static final int FILL = 1;
    private int curProgress = 1;
    private int maxProgress;
    private static final int INTERVAL = 1;
    private Handler mHandler = new Handler();
    private Paint mTextPaint;

    public RoundProgressBar(Context context) {
        this(context, null);
    }

    public RoundProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);


        TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
                R.styleable.RoundProgressBar);

        roundColor = mTypedArray.getColor(
                R.styleable.RoundProgressBar_roundColor, getResources().getColor(R.color.gray_line_default));
        roundProgressColor = mTypedArray.getColor(
                R.styleable.RoundProgressBar_roundProgressColor, getResources().getColor(R.color.colorPrimary));
        textColor = mTypedArray.getColor(
                R.styleable.RoundProgressBar_textColor, getResources().getColor(R.color.colorPrimary));
        textSize = mTypedArray.getDimension(
                R.styleable.RoundProgressBar_textSize, 24);
        roundWidth = mTypedArray.getDimension(
                R.styleable.RoundProgressBar_roundWidth, 5);
        max = mTypedArray.getInteger(R.styleable.RoundProgressBar_max, 100);
        textIsDisplayable = mTypedArray.getBoolean(
                R.styleable.RoundProgressBar_textIsDisplayable, false);
        style = mTypedArray.getInt(R.styleable.RoundProgressBar_style, 0);

        paint = new Paint();
        paint.setColor(roundColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(roundWidth);
        paint.setAntiAlias(true);

        mTextPaint = new Paint();
        mTextPaint.setColor(textColor);
        mTextPaint.setAntiAlias(true);

        mTypedArray.recycle();
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        /**
         * 绘制圆环
         */
        int centre = getWidth() / 2;  // 宽度的中心
        int radius = (int) (centre - roundWidth / 2);  // 内圆半径
        if (progress == 100) {
            paint.setColor(Color.parseColor("#BCBCBC"));
        } else {
            paint.setColor(roundColor);
        }
        canvas.drawCircle(centre, centre, radius, paint);
        /**
         * 画圆弧
         */
        if (progress == 100) {
            paint.setColor(Color.parseColor("#BCBCBC"));
        } else {
            paint.setColor(roundProgressColor);
        }
        paint.setStrokeWidth(roundWidth); //外圆的宽度
        RectF oval = new RectF(centre - radius, centre - radius, centre
                + radius, centre + radius);
        switch (style) {
            case STROKE: {
                paint.setStyle(Paint.Style.STROKE);
                if (progress != 0)
                    canvas.drawArc(oval, 270, 360 * progress / max, false, paint);
                break;
            }
            case FILL: {
                paint.setStyle(Paint.Style.FILL_AND_STROKE);
                if (progress != 0)
                    canvas.drawArc(oval, 270, 360 * progress / max, true, paint);
                break;
            }
        }
        if (progress == 100) {
            //当前的进度
            mTextPaint.setColor(getResources().getColor(R.color.colorPrimary));//蓝色
        } else {
            mTextPaint.setColor(textColor);
        }
        mTextPaint.setTextSize(textSize);
        mTextPaint.setTypeface(Typeface.DEFAULT_BOLD);  // 设置字体样式
        float textWidth = mTextPaint.measureText(percent + "%");// 获取文字的宽度值
        if (textIsDisplayable && style == STROKE) {
            canvas.drawText(percent + "%", (getWidth() - textWidth) / 2f,
                    //y公式： float baselineY = centerY + (fontMetrics.bottom-fontMetrics.top)/2 - fontMetrics.bottom
                    getWidth() / 2f - (mTextPaint.descent() + mTextPaint.ascent()) / 2f,
                    mTextPaint);
        }
    }

    public synchronized int getMax() {
        return max;
    }

    public synchronized void setMax(int max) {
        if (max < 0) {
            throw new IllegalArgumentException("max not less than 0");
        }
        this.max = max;
    }

    /**
     * 获取进度值
     *
     * @return
     */
    public synchronized int getProgress() {
        return progress;
    }

    /**
     * 正常设置圆环进度
     *
     * @param progress 当前进度
     */
    public void setProgress(int progress) {
        if (progress < 0) {
            throw new IllegalArgumentException("progress not less than 0");
        }
        if (progress > max) {
            progress = max;
        }
        if (progress <= max) {
            this.progress = progress;
            this.percent = progress;
            postInvalidate();
        }
    }

    /**
     * 开启动画 使用系统动画效果
     *
     * @param progress 当前进度
     */
    public synchronized void startAnimProgress(int progress) {
        //4.0以上，在AnimationSet基础上封装的，遗憾的是没有Repeat
        if (progress < 0) {
            throw new IllegalArgumentException("progress not less than 0");
        }
        if (progress > max) {
            progress = max;
        }
        this.progress = progress;
        this.percent = progress;
        AnimatorSet animation = new AnimatorSet();
        ObjectAnimator progressAnimation = ObjectAnimator.ofInt(this, "progress", 0, progress);
        progressAnimation.setDuration(700);// 动画执行时间
        /*
         * AccelerateInterpolator　　　　　                  加速，开始时慢中间加速
         * DecelerateInterpolator　　　 　　                 减速，开始时快然后减速
         * AccelerateDecelerateInterolator　                     先加速后减速，开始结束时慢，中间加速
         * AnticipateInterpolator　　　　　　                 反向 ，先向相反方向改变一段再加速播放
         * AnticipateOvershootInterpolator　                 反向加超越，先向相反方向改变，再加速播放，会超出目的值然后缓慢移动至目的值
         * BounceInterpolator　　　　　　　                        跳跃，快到目的值时值会跳跃，如目的值100，后面的值可能依次为85，77，70，80，90，100
         * CycleIinterpolator　　　　　　　　                   循环，动画循环一定次数，值的改变为一正弦函数：Math.sin(2 *
         * mCycles * Math.PI * input) LinearInterpolator　　　 线性，线性均匀改变
         * OvershottInterpolator　　　　　　                  超越，最后超出目的值然后缓慢改变到目的值
         * TimeInterpolator　　　　　　　　　                        一个接口，允许你自定义interpolator，以上几个都是实现了这个接口
         */
        progressAnimation.setInterpolator(new AccelerateDecelerateInterpolator());

        animation.playTogether(progressAnimation);//动画同时执行,可以做多个动画
        animation.start();
    }

    /**
     * 开启动画 使用handler 模拟动画效果
     *
     * @param progress 当前进度
     */
    public synchronized void setAnimProgress(int progress) {
        maxProgress = progress;
        curProgress = 0;
        mHandler1.sendEmptyMessageDelayed(INTERVAL, 1);
    }


    private Handler mHandler1 = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case INTERVAL:
                    if (curProgress < maxProgress) {
                        setProgress(curProgress);
                        curProgress += 1;
                        mHandler1.sendEmptyMessageDelayed(INTERVAL, 1);
                    } else {
                        setProgress(curProgress);
                    }
                    break;
                default:
                    setProgress(curProgress);
                    break;
            }
        }

    };

    public boolean isTextIsDisplayable() {
        return textIsDisplayable;
    }

    public void setTextIsDisplayable(boolean textIsDisplayable) {
        this.textIsDisplayable = textIsDisplayable;
        postInvalidate();
    }

    public synchronized void showText(boolean show) {
        textIsDisplayable = show;
    }

    public int getCricleColor() {
        return roundColor;
    }

    public void setCricleColor(int cricleColor) {
        this.roundColor = cricleColor;
    }

    public int getCricleProgressColor() {
        return roundProgressColor;
    }

    public void setCricleProgressColor(int cricleProgressColor) {
        this.roundProgressColor = cricleProgressColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public float getRoundWidth() {
        return roundWidth;
    }

    public void setRoundWidth(float roundWidth) {
        this.roundWidth = roundWidth;
    }

}
