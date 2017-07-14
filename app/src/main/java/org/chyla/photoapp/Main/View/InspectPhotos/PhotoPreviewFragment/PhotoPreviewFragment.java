package org.chyla.photoapp.Main.View.InspectPhotos.PhotoPreviewFragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.chyla.photoapp.Main.View.InspectPhotos.PhotoPreviewFragment.detail.SwipeActionDetector;
import org.chyla.photoapp.Main.View.InspectPhotos.PhotoPreviewFragment.detail.SwipeActionListener;
import org.chyla.photoapp.Main.Model.objects.Photo;
import org.chyla.photoapp.R;

public class PhotoPreviewFragment extends Fragment implements SwipeActionListener {

    public final static String TAG = "PhotoPreviewFragment";

    private Context mContext;
    private GestureDetectorCompat mDetector;
    private PhotoPreviewActionListener mPhotoActionListener;
    private TextView titleView;
    private TextView descriptionView;
    private ImageView imageView;
    private Photo photo;

    public void setPhotoActionListener(PhotoPreviewActionListener listener) {
        mPhotoActionListener = listener;
    }

    public void setPhoto(final Photo photo) {
        this.photo = photo;

        updatePhotoView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_inspected_photo_preview, container, false);
        v.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (mDetector != null)
                    mDetector.onTouchEvent(event);

                return true;
            }
        });

        titleView = (TextView) v.findViewById(R.id.text_title);
        descriptionView = (TextView) v.findViewById(R.id.text_description);
        imageView = (ImageView) v.findViewById(R.id.imageView);
        updatePhotoView();

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
        if (mContext != null) {
            mDetector = new GestureDetectorCompat(mContext.getApplicationContext(), new SwipeActionDetector(this));
        }
    }

    private void updatePhotoView() {
        if (imageView != null && photo != null) {
            Log.d(TAG, "Updating photo view...");
            titleView.setText(photo.getTitle());
            descriptionView.setText(photo.getDescription());
            Picasso.with(imageView.getContext()).load(photo.getUrl().toString()).into(imageView);
        }
    }

    @Override
    public void onSwipeUp() {
        Log.d(TAG, "onSwipeUp call");
        mPhotoActionListener.onPhotoDismiss();
    }

    @Override
    public void onSwipeDown() {
        Log.d(TAG, "onSwipeDown call");
        mPhotoActionListener.onPhotoDismiss();
    }

    @Override
    public void onSwipeLeft() {
        Log.d(TAG, "onSwipeLeft call");
        mPhotoActionListener.onPhotoSave(photo);
    }

    @Override
    public void onSwipeRight() {
        Log.d(TAG, "onSwipeRight call");
        mPhotoActionListener.onPhotoSave(photo);
    }

}
