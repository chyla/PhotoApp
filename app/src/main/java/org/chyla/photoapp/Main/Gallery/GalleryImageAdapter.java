package org.chyla.photoapp.Main.Gallery;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import org.chyla.photoapp.Main.Model.Photo;
import org.chyla.photoapp.R;

import java.util.List;

public class GalleryImageAdapter extends RecyclerView.Adapter<GalleryImageAdapter.MyViewHolder> {

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageButton imageButton;

        public MyViewHolder(View view) {
            super(view);
            imageButton = (ImageButton) view.findViewById(R.id.image_button);
        }

        void setPhoto(Photo photo) {
        }
    }

    public GalleryImageAdapter(List<Photo> photos) {
        this.photos = photos;
    }

    private List<Photo> photos;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_gallery, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Photo photo = photos.get(position);
        holder.setPhoto(photo);
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

}
