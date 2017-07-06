package org.chyla.photoapp.Repository.Login;

import org.chyla.photoapp.Main.Model.objects.User;

public interface LoginRepository {
    void login(String username, String password);
    void logout();

    void register(String username, String password);

    boolean isUserLoggedIn();

    User getLoggedUserInfo();
}
