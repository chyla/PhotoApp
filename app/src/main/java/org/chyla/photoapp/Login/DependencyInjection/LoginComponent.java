package org.chyla.photoapp.Login.DependencyInjection;

import org.chyla.photoapp.Login.Presenter.LoginPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Component(
    modules = {
        LoginModule.class
    }
)
@Singleton
public interface LoginComponent {

    LoginPresenter getPresenter();

}
