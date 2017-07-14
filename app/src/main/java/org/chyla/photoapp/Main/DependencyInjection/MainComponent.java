package org.chyla.photoapp.Main.DependencyInjection;

import org.chyla.photoapp.Main.Presenter.MainPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Component(
    modules = {
        MainModule.class
    }
)
@Singleton
public interface MainComponent {

    MainPresenter getPresenter();

}
