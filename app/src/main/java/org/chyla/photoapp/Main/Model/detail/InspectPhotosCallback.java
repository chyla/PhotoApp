package org.chyla.photoapp.Main.Model.detail;

import org.chyla.photoapp.Main.Model.Photo;
import org.chyla.photoapp.Main.Model.detail.Event.ShowInspectedPhotosEvent;
import org.chyla.photoapp.Main.Repository.GetPhotosCallback;
import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class InspectPhotosCallback implements GetPhotosCallback {

    @Override
    public void getPhotosCallback(final List<Photo> photos) {
        EventBus.getDefault().post(new ShowInspectedPhotosEvent(photos));
    }

}
