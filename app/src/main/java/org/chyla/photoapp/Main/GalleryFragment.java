package org.chyla.photoapp.Main;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.chyla.photoapp.Main.Gallery.GalleryImageAdapter;
import org.chyla.photoapp.Main.Gallery.ImageData;
import org.chyla.photoapp.R;

import java.util.LinkedList;
import java.util.List;

public class GalleryFragment extends Fragment {

    private RecyclerView.LayoutManager layoutManager;
    private GalleryImageAdapter adapter;
    private RecyclerView recyclerView;
    private List<ImageData> images;

    public GalleryFragment() {
        images = new LinkedList<>();
        images.add(new ImageData());
        images.add(new ImageData());
        images.add(new ImageData());
        images.add(new ImageData());
        images.add(new ImageData());
        images.add(new ImageData());
        images.add(new ImageData());
        images.add(new ImageData());
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
        adapter = new GalleryImageAdapter(images);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
