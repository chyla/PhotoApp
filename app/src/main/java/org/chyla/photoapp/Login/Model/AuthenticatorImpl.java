package org.chyla.photoapp.Login.Model;

import android.text.TextUtils;

import org.chyla.photoapp.Login.Model.Event.ErrorEvent;
import org.chyla.photoapp.Login.Model.Event.SuccessEvent;
import org.chyla.photoapp.Login.Repository.LoginRepository;
import org.chyla.photoapp.Login.Repository.LoginRepositoryImpl;
import org.greenrobot.eventbus.EventBus;

public class AuthenticatorImpl implements Authenticator {

    private LoginRepository repository;

    public AuthenticatorImpl() {
        repository = new LoginRepositoryImpl();
    }

    @Override
    public void loginUser(String username, String password) {
        if (TextUtils.isEmpty(username) || !isEmailValid(username)) {
            EventBus.getDefault().post(new ErrorEvent(ErrorEvent.Type.MAIL_ERROR));
            return;
        }

        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            EventBus.getDefault().post(new ErrorEvent(ErrorEvent.Type.PASSWORD_ERROR));
            return;
        }

        repository.login(username, password);
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    @Override
    public void checkUserLoggedIn() {
        if (repository.isUserLoggedIn()) {
            EventBus.getDefault().post(new SuccessEvent());
        }
    }
}
