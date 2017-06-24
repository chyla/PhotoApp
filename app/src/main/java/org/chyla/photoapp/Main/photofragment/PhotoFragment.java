package org.chyla.photoapp.Main.photofragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import org.chyla.photoapp.Main.photofragment.detail.SwipeActionDetector;
import org.chyla.photoapp.R;

public class PhotoFragment extends Fragment {

    private Context mContext;
    private GestureDetectorCompat mDetector;
    private PhotoActionListener mSwipeListener;

    public void setPhotoActionListener(PhotoActionListener listener) {
        mSwipeListener = listener;

        createGestureDetectorCompat();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo, container, false);
        v.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (mDetector != null)
                    mDetector.onTouchEvent(event);

                return true;
            }
        });
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;

        createGestureDetectorCompat();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void createGestureDetectorCompat() {
        if (mContext != null && mSwipeListener != null) {
            mDetector = new GestureDetectorCompat(mContext.getApplicationContext(), new SwipeActionDetector(mSwipeListener));
        }
    }

}
