package org.chyla.photoapp.Main.InspectPhotos.GalleryFragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.chyla.photoapp.Main.Model.objects.Photo;
import org.chyla.photoapp.Main.InspectPhotos.GalleryFragment.detail.GalleryPhotoAdapter;
import org.chyla.photoapp.Main.Presenter.MainPresenter;
import org.chyla.photoapp.R;

import java.util.LinkedList;
import java.util.List;

public class GalleryFragment extends Fragment {

    private final List<Photo> photos;
    private RecyclerView.LayoutManager layoutManager;
    private GalleryPhotoAdapter adapter;
    private RecyclerView recyclerView;
    private MainPresenter presenter;

    public GalleryFragment() {
        photos = new LinkedList<>();
    }

    public void addPhotos(final List<Photo> newPhotos) {
        photos.addAll(newPhotos);

        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    public void setPresenter(MainPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.images_view);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        layoutManager = new GridLayoutManager(context.getApplicationContext(), 3);
        adapter = new GalleryPhotoAdapter(presenter, photos);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}