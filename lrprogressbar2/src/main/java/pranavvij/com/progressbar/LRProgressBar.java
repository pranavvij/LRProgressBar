package pranavvij.com.progressbar;

/**
 * Created by pranav vij on 11/26/2015.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;


public class LRProgressBar extends View {

        private Paint mPrimaryPaint;
        private RectF mRectF;

        private Paint shadowPaint;
        private RectF indideRectF;
        private RectF outsideRectF;

        private RectF mCircleRectF;

        private int mPrimaryProgressColor;
        private int mShadowWidth=2;
        private int mStrokeWidth;

        private int mProgress;

        Context context;
        private Paint mCirclePaint;
        private int x;
        private int y;
        private int mWidth = 0, mHeight = 0;


        public LRProgressBar(Context context) {
            super(context);
            init(context, null);
        }

        public LRProgressBar(Context context, AttributeSet attrs) {
            super(context, attrs);
            init(context, attrs);
        }

        public LRProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init(context, attrs);
        }

        void init(Context context, AttributeSet attrs) {
            TypedArray a;
            this.context=context;
            if (attrs != null) {
                a = context.getTheme().obtainStyledAttributes(
                        attrs,
                        R.styleable.MyProgress,
                        0, 0);
            } else {
                throw new IllegalArgumentException("Must have to pass the attributes");
            }

            try {

                mPrimaryProgressColor = a.getColor(R.styleable.MyProgress_progressColor, android.R.color.darker_gray);

                mProgress = a.getInt(R.styleable.MyProgress_progress, 0);

                mShadowWidth = a.getDimensionPixelSize(R.styleable.MyProgress_shadowWidth, 2);

                mStrokeWidth = a.getDimensionPixelSize(R.styleable.MyProgress_strokeWidth, 20);
            } finally {
                a.recycle();
            }

            mPrimaryPaint = new Paint();
            mPrimaryPaint.setAntiAlias(true);
            mPrimaryPaint.setStyle(Paint.Style.STROKE);
            mPrimaryPaint.setStrokeWidth(mStrokeWidth);
            mPrimaryPaint.setColor(mPrimaryProgressColor);

            mCirclePaint = new Paint();
            mCirclePaint.setAntiAlias(true);
            mCirclePaint.setStyle(Paint.Style.FILL);
            mCirclePaint.setColor(context.getResources().getColor(R.color.transparent));

            shadowPaint=new Paint();
            shadowPaint.setAntiAlias(true);
            shadowPaint.setStyle(Paint.Style.STROKE);
            shadowPaint.setStrokeWidth(mShadowWidth);
            shadowPaint.setColor(context.getResources().getColor(R.color.transparent_black_light));

            mRectF = new RectF();
            mCircleRectF=new RectF();
            indideRectF=new RectF();
            outsideRectF=new RectF();
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            mRectF.set(getPaddingLeft(), getPaddingTop(), w - getPaddingRight(), h - getPaddingBottom());
            indideRectF.set(getPaddingLeft()+mStrokeWidth, getPaddingTop()+mStrokeWidth, w - getPaddingRight()-mStrokeWidth, h - getPaddingBottom()-mStrokeWidth);
            outsideRectF.set(getPaddingLeft()-mStrokeWidth, getPaddingTop()-mStrokeWidth, w - getPaddingRight()+mStrokeWidth, h - getPaddingBottom()+mStrokeWidth);
            x = w / 2 ;
            y = h / 2;
            mWidth = w;
            mHeight = h;
            invalidate();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            SweepGradient shader=new SweepGradient( mWidth / 2, mHeight / 2, new int[]{context.getResources().getColor(R.color.pure_white),context.getResources().getColor(R.color.transparent) }, new float[]{0f, 1f});
            mPrimaryPaint.setShader(shader);
            int primarySwipeangle = (mProgress * 360) / 100;
            canvas.drawArc(mRectF, 0, primarySwipeangle, false, mPrimaryPaint);


            int r = (getHeight() - getPaddingLeft() * 2) / 2;
            mCircleRectF.set(x + r - mStrokeWidth / 2, y - mStrokeWidth / 2, x + r + mStrokeWidth / 2, y + mStrokeWidth / 2);
            canvas.drawArc(mCircleRectF, 180, 180, false, mCirclePaint);

            RectF mCircleRectF1=new RectF();
            mCircleRectF1.set((x+r-mStrokeWidth/2)-mShadowWidth/2,(y-mStrokeWidth/2)-mShadowWidth/2,mShadowWidth/2+x+r+mStrokeWidth/2,(y+mStrokeWidth/2)+mShadowWidth);
            canvas.drawArc(mCircleRectF1, 180, 180, false, shadowPaint);

            SweepGradient shader1=new SweepGradient( mWidth / 2, mHeight / 2, new int[]{context.getResources().getColor(R.color.transparent_black),context.getResources().getColor(R.color.transparent) }, new float[]{0f, 1f});
            shadowPaint.setShader(shader1);

            canvas.drawArc(indideRectF, 0, primarySwipeangle, false, shadowPaint);
            canvas.drawArc(outsideRectF, 0, primarySwipeangle, false, shadowPaint);

        }




        public void setPrimaryProgressColor(int mPrimaryProgressColor) {
            this.mPrimaryProgressColor = mPrimaryProgressColor;
            invalidate();
        }

        public void setStrokeWidth(int mStrokeWidth) {
            this.mStrokeWidth = mStrokeWidth;
            invalidate();
        }

        public void setProgress(int mProgress) {
            this.mProgress = mProgress;
            invalidate();
        }

        public int getPrimaryProgressColor() {
            return mPrimaryProgressColor;
        }

        public int getProgress() {
            return mProgress;
        }
}
