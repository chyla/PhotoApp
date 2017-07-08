package org.chyla.photoapp.Model.Authenticator;

import org.chyla.photoapp.Main.Model.objects.User;

public interface Authenticator {

    User getLoggedUser();

    void loginUser(String username, String password);
    void logoutUser();

    void register(String username, String password);

    void checkUserLoggedIn();
}
