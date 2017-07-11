package org.chyla.photoapp.Login;

public interface LoginView {
    void showLoginForm();
    void hideLoginForm();

    void showProgress();
    void hideProgress();

    void clearErrors();

    void handleMailError();
    void handlePasswordError();

    void startSynchronizeActivity();
}
