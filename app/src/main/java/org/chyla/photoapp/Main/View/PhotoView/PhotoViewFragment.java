package org.chyla.photoapp.Main.View.PhotoView;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.chyla.photoapp.Main.Model.objects.Photo;
import org.chyla.photoapp.R;

public class PhotoViewFragment extends Fragment {

    public final static String TAG = "PhotoViewFragment";

    private Photo photo;
    private Context context;
    private TextView titleText;
    private TextView descriptionText;
    private ImageView imageView;
    private View noPhotoInfo;
    private View photoContent;

    public PhotoViewFragment() {
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
        View view = inflater.inflate(R.layout.fragment_photo_view, container, false);

        noPhotoInfo = (View) view.findViewById(R.id.no_photo);
        photoContent = (View) view.findViewById(R.id.photo_content);

        titleText = (TextView) view.findViewById(R.id.text_title);
        descriptionText = (TextView) view.findViewById(R.id.text_description);
        imageView = (ImageView) view.findViewById(R.id.image_photo);

        updatePhotoView();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void updatePhotoView() {
        if (imageView != null && photo != null) {
            Log.d(TAG, "Updating photo view...");

            titleText.setText(photo.getTitle());
            descriptionText.setText(photo.getDescription());
            Picasso.with(imageView.getContext()).load(photo.getUrl().toString()).into(imageView);

            showPhotoContent();
        }
        else {
            Log.d(TAG, "No photo to view...");

            showNoPhotoInfo();
        }
    }

    private void showNoPhotoInfo() {
        if (photoContent != null && noPhotoInfo != null) {
            photoContent.setVisibility(View.GONE);
            noPhotoInfo.setVisibility(View.VISIBLE);
        }
    }

    private void showPhotoContent() {
        if (photoContent != null && noPhotoInfo != null) {
            noPhotoInfo.setVisibility(View.GONE);
            photoContent.setVisibility(View.VISIBLE);
        }
    }

}
