package org.chyla.photoapp.Model.Authenticator;

import org.chyla.photoapp.Main.Model.objects.User;
import org.chyla.photoapp.Model.Authenticator.Event.ErrorEvent;
import org.chyla.photoapp.Model.Authenticator.Event.SuccessEvent;
import org.chyla.photoapp.Repository.Login.LoginRepository;
import org.chyla.photoapp.Repository.Login.LoginRepositoryImpl;
import org.greenrobot.eventbus.EventBus;

public class AuthenticatorImpl implements Authenticator {

    private LoginRepository repository;

    public AuthenticatorImpl() {
        repository = new LoginRepositoryImpl();
    }

    @Override
    public User getLoggedUser() {
        return repository.getLoggedUserInfo();
    }

    @Override
    public void loginUser(String username, String password) {
        if (!isEmailValid(username)) {
            EventBus.getDefault().post(new ErrorEvent(ErrorEvent.Type.MAIL_ERROR));
            return;
        }

        if (!isPasswordValid(password)) {
            EventBus.getDefault().post(new ErrorEvent(ErrorEvent.Type.PASSWORD_ERROR));
            return;
        }

        repository.login(username, password);
    }

    @Override
    public void logoutUser() {
        repository.logout();
    }

    @Override
    public void register(String username, String password) {
        if (!isEmailValid(username)) {
            EventBus.getDefault().post(new ErrorEvent(ErrorEvent.Type.MAIL_ERROR));
            return;
        }

        if (!isPasswordValid(password)) {
            EventBus.getDefault().post(new ErrorEvent(ErrorEvent.Type.PASSWORD_ERROR));
            return;
        }

        repository.register(username, password);
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
