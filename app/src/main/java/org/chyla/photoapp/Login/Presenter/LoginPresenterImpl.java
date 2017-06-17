package org.chyla.photoapp.Login.Presenter;


import org.chyla.photoapp.Login.LoginView;
import org.chyla.photoapp.Model.Authenticator.Authenticator;
import org.chyla.photoapp.Model.Authenticator.AuthenticatorImpl;
import org.chyla.photoapp.Model.Authenticator.Event.CancelledEvent;
import org.chyla.photoapp.Model.Authenticator.Event.ErrorEvent;
import org.chyla.photoapp.Model.Authenticator.Event.SuccessEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class LoginPresenterImpl implements LoginPresenter {

    private Authenticator authenticator;
    private LoginView view;

    public LoginPresenterImpl(LoginView view) {
        this.view = view;
        authenticator = new AuthenticatorImpl();
    }

    @Override
    public void authenticate(String username, String password) {
        view.hideLoginForm();
        view.showProgress();
        authenticator.loginUser(username, password);
    }

    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
        authenticator.checkUserLoggedIn();
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
    }

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessEvent(SuccessEvent event) {
        view.startMainActivity();
    }

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorEvent(ErrorEvent event) {
        view.showLoginForm();
        view.hideProgress();
        view.clearErrors();

        if (event.getType() == ErrorEvent.Type.MAIL_ERROR) {
            view.handleMailError();
        } else {
            view.handlePasswordError();
        }
    }

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCancellEvent(CancelledEvent event) {
        view.showLoginForm();
        view.hideProgress();
    }

}
