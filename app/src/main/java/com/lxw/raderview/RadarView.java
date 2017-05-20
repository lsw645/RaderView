package com.lxw.raderview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * <pre>
 *     author : lxw
 *     e-mail : lsw@tairunmh.com
 *     time   : 2017/05/20
 *     desc   :
 * </pre>
 */

public class RadarView extends View {

    private final String TAG = "RadarView";

    private static final int MSG_WHAT = 1;

    private static final int DELAY_TIME = 20;

    //设置默认宽高，雷达一般都是圆形，所以我们下面取宽高会去Math.min(宽,高)
    private final int DEFAULT_WIDTH = 200;

    private final int DEFAULT_HEIGHT = 200;
    //雷达的半径
    private int mRadarRadius;
    //雷达画笔
    private Paint mRadarPaint;
    //雷达底色画笔
    private Paint mRadarBg;
    //雷达圆圈的个数，默认4个
    private int mCircleNum = 4;
    //雷达线条的颜色，默认为白色
    private int mCircleColor = Color.WHITE;
    //雷达圆圈背景色
    private int mRadarBgColor = Color.BLACK;
    //paintShader
    private Shader mRadarShader;

    //雷达扫描时候的起始和终止颜色
    private int mStartColor = 0xaa0000ff;


    private int mEndColor = 0xaaff0000;

    private Matrix mMatrix;

    //旋转的角度
    private int mRotate = 0;

    public RadarView(Context context) {
        this(context, null);
    }

    public RadarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        initPaint();
    }

    public RadarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mRotate += 3;
            postInvalidate();
            mMatrix.reset();
            mMatrix.preRotate(mRotate, 0, 0);
            sendEmptyMessageDelayed(MSG_WHAT, DELAY_TIME);

        }
    };


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRadarRadius = Math.min(w / 2, h / 2);

    }

    private void initPaint() {
        //背景的画笔
        mRadarBg = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRadarBg.setStyle(Paint.Style.FILL);
        mRadarBg.setColor(mRadarBgColor);

        //雷达的画笔
        mRadarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRadarPaint.setStyle(Paint.Style.STROKE);
        mRadarPaint.setStrokeWidth(1);
        mRadarPaint.setColor(mCircleColor);

        mRadarShader = new SweepGradient(0, 0, mStartColor, mEndColor);

        mMatrix = new Matrix();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(mRadarRadius, mRadarRadius);

        canvas.drawCircle(0, 0, mRadarRadius, mRadarBg);

        for (int i = 0; i < mCircleNum; i++) {
            canvas.drawCircle(0, 0, mRadarRadius / mCircleNum * (1 + i), mRadarPaint);
        }

        canvas.drawLine(-mRadarRadius, 0, mRadarRadius, 0, mRadarPaint);
        canvas.drawLine(0, -mRadarRadius, 0, mRadarRadius, mRadarPaint);

        mRadarBg.setShader(mRadarShader);
        canvas.concat(mMatrix);
        canvas.drawCircle(0, 0, mRadarRadius, mRadarBg);
    }

    public void start() {
        mHandler.removeMessages(MSG_WHAT);
        mHandler.sendEmptyMessage(MSG_WHAT);
    }

    public void stop() {
        mHandler.removeMessages(MSG_WHAT);
    }


}
