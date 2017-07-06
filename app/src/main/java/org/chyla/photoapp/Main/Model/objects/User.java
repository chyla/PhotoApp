package org.chyla.photoapp.Main.Model.objects;

public class User {

    private final String googleUserId;
    private final String email;

    public User(final String googleUserId, final String email) {
        this.googleUserId = googleUserId;
        this.email = email;
    }

    public String getGoogleUserId() {
        return googleUserId;
    }

    public String getEmail() {
        return email;
    }

}
