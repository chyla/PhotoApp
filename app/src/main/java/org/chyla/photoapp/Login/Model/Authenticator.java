package org.chyla.photoapp.Login.Model;

public interface Authenticator {
    void loginUser(String username, String password);
    void checkUserLoggedIn();
}
