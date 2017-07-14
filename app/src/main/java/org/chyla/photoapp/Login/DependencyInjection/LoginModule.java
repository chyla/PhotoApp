package org.chyla.photoapp.Login.DependencyInjection;

import org.chyla.photoapp.Login.LoginView;
import org.chyla.photoapp.Login.Presenter.LoginPresenter;
import org.chyla.photoapp.Login.Presenter.LoginPresenterImpl;
import org.chyla.photoapp.Model.Authenticator.Authenticator;
import org.chyla.photoapp.Model.Authenticator.AuthenticatorImpl;
import org.chyla.photoapp.Repository.Login.LoginRepository;
import org.chyla.photoapp.Repository.Login.LoginRepositoryImpl;
import org.greenrobot.eventbus.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {

    private final LoginView view;
    private final EventBus eventBus;

    public LoginModule(LoginView view, EventBus eventBus) {
        this.view = view;
        this.eventBus = eventBus;
    }

    @Provides
    @Singleton
    public LoginView proviedsLoginView() {
        return view;
    }

    @Provides
    @Singleton
    public EventBus proviedsEventBus() {
        return eventBus;
    }

    @Provides
    @Singleton
    public LoginPresenter providesLoginPresenter(EventBus eventBus, LoginView view, Authenticator authenticator) {
        return new LoginPresenterImpl(eventBus, view, authenticator);
    }

    @Provides
    @Singleton
    public Authenticator providesAuthenticator(LoginRepository loginRepository) {
        return new AuthenticatorImpl(loginRepository);
    }

    @Provides
    @Singleton
    public LoginRepository providesLoginRepository() {
        return new LoginRepositoryImpl();
    }

}
