package org.chyla.photoapp.Main.View.InspectPhotos.PhotoPreviewFragment.detail;

import android.view.GestureDetector;
import android.view.MotionEvent;

public class SwipeActionDetector implements GestureDetector.OnGestureListener {

    private final static float SWIPE_THRESHOLD = 100;
    private final SwipeActionListener mListener;

    public SwipeActionDetector(SwipeActionListener listener) {
        mListener = listener;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
    }

    @Override
    public boolean onFling(final MotionEvent e1, final MotionEvent e2, final float velocityX, final float velocityY) {
        if (isHorizontalSwipe(e1, e2)) {
            if (isLeftToRight(e1, e2)) {
                mListener.onSwipeRight();
            }
            else {
                mListener.onSwipeLeft();
            }
        }
        else if (isVerticalSwipe(e1, e2)) {
            if (isDownToUp(e1, e2)) {
                mListener.onSwipeUp();
            }
            else {
                mListener.onSwipeDown();
            }
        }
        return false;
    }

    private boolean isHorizontalSwipe(final MotionEvent e1, final MotionEvent e2) {
        return Math.abs(diffX(e1, e2)) > Math.abs(diffY(e1, e2))
                && Math.abs(diffX(e1, e2)) > SWIPE_THRESHOLD;
    }

    private boolean isLeftToRight(final MotionEvent e1, final MotionEvent e2) {
        return diffX(e1, e2) > 0;
    }

    private boolean isVerticalSwipe(final MotionEvent e1, final MotionEvent e2) {
        return Math.abs(diffY(e1, e2)) > Math.abs(diffX(e1, e2))
                && Math.abs(diffY(e1, e2)) > SWIPE_THRESHOLD;
    }

    private boolean isDownToUp(final MotionEvent e1, final MotionEvent e2) {
        return diffY(e1, e2) < 0;
    }

    private float diffX(final MotionEvent e1, final MotionEvent e2) {
        return e2.getX() - e1.getX();
    }

    private float diffY(final MotionEvent e1, final MotionEvent e2) {
        return e2.getY() - e1.getY();
    }

}
