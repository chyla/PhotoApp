package org.chyla.photoapp.Main.Model.objects;

public class User {

    private final String googleUserId;

    public User(String googleUserId) {
        this.googleUserId = googleUserId;
    }

    public String getGoogleUserId() {
        return googleUserId;
    }

}
