package org.chyla.photoapp.Main.photofragment.detail;

import android.view.GestureDetector;
import android.view.MotionEvent;

import org.chyla.photoapp.Main.photofragment.PhotoActionListener;

public class SwipeActionDetector implements GestureDetector.OnGestureListener {

    PhotoActionListener mListener;

    public SwipeActionDetector(PhotoActionListener listener) {
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
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

}
