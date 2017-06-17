package org.chyla.photoapp.Model.Authenticator;

public interface Authenticator {
    void loginUser(String username, String password);
    void logoutUser();

    void register(String username, String password);

    void checkUserLoggedIn();
}
