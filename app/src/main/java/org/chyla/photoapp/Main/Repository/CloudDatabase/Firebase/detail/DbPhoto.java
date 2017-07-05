package org.chyla.photoapp.Main.Repository.CloudDatabase.Firebase.detail;

public class DbPhoto {

    public String title;
    public String description;
    public String url;

    public DbPhoto() {
    }

    public DbPhoto(String title, String description, String url) {
        this.title = title;
        this.description = description;
        this.url = url;
    }

}
