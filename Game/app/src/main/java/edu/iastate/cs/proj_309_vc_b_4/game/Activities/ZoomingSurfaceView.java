package edu.iastate.cs.proj_309_vc_b_4.game.Activities;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Joe on 11/19/2017.
 *
 * This abstract class represents a surface view that supports panning and zooming.
 * To implement this class, call preDraw() in the draw method before making any drawing calls and call postDraw() after making the drawing calls.
 * If overriding the OnTouchEvent() method, be sure to call and return the super method.
 */
public abstract class ZoomingSurfaceView extends SurfaceView implements SurfaceHolder.Callback{

    private float zoomScale = 1;
    private ScaleGestureDetector mScaleDetector;
    private int mode;
    private final int NONE = 0;
    private final int ZOOM = 1;
    private final int PAN = 2;
    private Matrix mMatrix = new Matrix();
    private float mTouchX, mTouchY, mTouchBackupX, mTouchBackupY, mTouchDownX, mTouchDownY;
    private Rect boundingBox = new Rect();
    private int saveCount;

    public ZoomingSurfaceView(Context context,AttributeSet attributeSet){
        super(context,attributeSet);
        boundingBox.set(0,0,getWidth(),getHeight());
        setFocusable(true);
        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        mTouchX = metrics.widthPixels/2;
        mTouchY = metrics.heightPixels/2;
    }

    protected void preDraw(Canvas canvas){
        //zooming and panning stuff
        saveCount = canvas.getSaveCount();
        canvas.save();
        canvas.concat(mMatrix);
    }

    protected void postDraw(Canvas canvas){
        //zooming and panning stuff
        canvas.restoreToCount(saveCount);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mScaleDetector.onTouchEvent(event);

        if (!this.mScaleDetector.isInProgress()) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_UP:
                    // similar to ScaleListener.onScaleEnd (as long as we don't
                    // handle indices of touch events)
                    mode = NONE;

                case MotionEvent.ACTION_DOWN:

                    mTouchDownX = event.getX();
                    mTouchDownY = event.getY();
                    mTouchBackupX = mTouchX;
                    mTouchBackupY = mTouchY;

                    // pan/move started
                    mode = PAN;
                    break;
                case MotionEvent.ACTION_MOVE:
                    // make sure we don't handle the last move event when the first
                    // finger is still down and the second finger is lifted up
                    // already after a zoom/scale interaction. see
                    // ScaleListener.onScaleEnd
                    if (mode == PAN) {

                        // get current location
                        final float x = event.getX();
                        final float y = event.getY();

                        // get distance vector from where the finger touched down to
                        // current location
                        final float diffX = x - mTouchDownX;
                        final float diffY = y - mTouchDownY;

                        mTouchX = mTouchBackupX + diffX;
                        mTouchY = mTouchBackupY + diffY;

                        CalculateMatrix(true);
                    }

                    break;
            }
        }

        return true;
    }

    private void CalculateMatrix(boolean invalidate) {
        float sizeX = this.getWidth() / 2;
        float sizeY = this.getHeight() / 2;

        mMatrix.reset();

        // move the view so that it's center point is located in 0,0
        mMatrix.postTranslate(-sizeX, -sizeY);

        // scale the view
        mMatrix.postScale(zoomScale, zoomScale);

        // re-move the view to it's desired location
        mMatrix.postTranslate(mTouchX, mTouchY);
        if (invalidate)
            invalidate(); // re-draw
    }

    /**
     * Returns the matrix for this surface view
     * @return the matrix for this surface view
     */
    public Matrix getmMatrix() {
        return mMatrix;
    }

    private class ScaleListener extends
            ScaleGestureDetector.SimpleOnScaleGestureListener {

        float mFocusStartX;
        float mFocusStartY;
        float mZoomBackupX;
        float mZoomBackupY;

        public ScaleListener() {
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {

            mode = ZOOM;

            mFocusStartX = detector.getFocusX();
            mFocusStartY = detector.getFocusY();
            mZoomBackupX = mTouchX;
            mZoomBackupY = mTouchY;

            return super.onScaleBegin(detector);
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {

            mode = NONE;

            super.onScaleEnd(detector);
        }

        @Override
        public boolean onScale(ScaleGestureDetector detector) {

            if (mode != ZOOM)
                return true;


            // get current scale and fix its value
            float scale = detector.getScaleFactor();
            zoomScale *= scale;
            zoomScale = Math.max(0.1f, Math.min(zoomScale, 5.0f));

            // get current focal point between both fingers (changes due to
            // movement)
            float focusX = detector.getFocusX();
            float focusY = detector.getFocusY();

            // get distance vector from initial event (onScaleBegin) to current
            float diffX = focusX - mFocusStartX;
            float diffY = focusY - mFocusStartY;

            // scale the distance vector accordingly
            diffX *= scale;
            diffY *= scale;

            // set new touch position
            mTouchX = mZoomBackupX + diffX;
            mTouchY = mZoomBackupY + diffY;

            CalculateMatrix(true);

            return true;
        }

    }

}
