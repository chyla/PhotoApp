package org.chyla.photoapp.Main.InspectPhotos.PhotoPreviewFragment;

import org.chyla.photoapp.Main.Model.objects.Photo;

public interface PhotoPreviewActionListener {
    void onPhotoSave(final Photo photo);
    void onPhotoDismiss();
}
