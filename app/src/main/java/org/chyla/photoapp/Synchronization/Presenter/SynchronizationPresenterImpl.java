package org.chyla.photoapp.Synchronization.Presenter;

import org.chyla.photoapp.Synchronization.Model.SynchronizeDataInteractor;
import org.chyla.photoapp.Synchronization.Model.detail.SynchronizationFinishedEvent;
import org.chyla.photoapp.Synchronization.View;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class SynchronizationPresenterImpl implements SynchronizationPresenter {

    private final SynchronizeDataInteractor interactor;
    private final View view;

    public SynchronizationPresenterImpl(final SynchronizeDataInteractor interactor, final View view) {
        this.interactor = interactor;
        this.view = view;
    }

    @Override
    public void synchronize() {
        interactor.synchronizeData();
    }

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSynchronizeFinished(final SynchronizationFinishedEvent event) {
        view.startMainActivity();
    }

    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
    }

}
