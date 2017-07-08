package org.chyla.photoapp.Main.Model;

import org.chyla.photoapp.Main.Presenter.MainPresenter;

public interface NewPhotoInteractor {

    void setPresenter(final MainPresenter presenter);

    void addNewPhoto(final String title, final String description, final String path);

}
