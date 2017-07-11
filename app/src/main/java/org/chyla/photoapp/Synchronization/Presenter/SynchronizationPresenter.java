package org.chyla.photoapp.Synchronization.Presenter;

import org.chyla.photoapp.Synchronization.Model.detail.SynchronizationFinishedEvent;

public interface SynchronizationPresenter {

    void synchronize();
    void onSynchronizeFinished(final SynchronizationFinishedEvent event);

    void onStart();
    void onStop();

}
