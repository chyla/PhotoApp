package org.chyla.photoapp.Synchronization.DependencyInjection;

import org.chyla.photoapp.Synchronization.Presenter.SynchronizationPresenter;
import org.chyla.photoapp.Synchronization.SynchronizationActivity;

import javax.inject.Singleton;

import dagger.Component;

@Component(
    modules = {
        SynchronizationModule.class
    }
)
@Singleton
public interface SynchronizationComponent {

    SynchronizationPresenter getPresenter();
}
