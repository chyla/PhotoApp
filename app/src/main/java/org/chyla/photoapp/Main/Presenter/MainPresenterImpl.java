package org.chyla.photoapp.Main.Presenter;

import org.chyla.photoapp.Main.MainView;
import org.chyla.photoapp.Model.Authenticator.Authenticator;
import org.chyla.photoapp.Model.Authenticator.AuthenticatorImpl;

public class MainPresenterImpl implements MainPresenter {

    Authenticator authenticator;
    MainView view;

    public MainPresenterImpl(MainView view) {
        authenticator = new AuthenticatorImpl();
        this.view = view;
    }

    @Override
    public void logoutUser() {
        authenticator.logoutUser();
        view.startLoginActivity();
    }

}
