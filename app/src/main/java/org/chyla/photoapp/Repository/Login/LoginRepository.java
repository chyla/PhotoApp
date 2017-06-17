package org.chyla.photoapp.Repository.Login;

public interface LoginRepository {
    void login(String username, String password);

    boolean isUserLoggedIn();
}
