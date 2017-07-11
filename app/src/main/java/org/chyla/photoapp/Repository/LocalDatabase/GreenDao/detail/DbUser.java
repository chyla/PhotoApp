package org.chyla.photoapp.Repository.LocalDatabase.GreenDao.detail;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class DbUser {

    @Id
    private Long id;

    @NotNull
    @Unique
    private String googleId;

    @Generated(hash = 533456508)
    public DbUser(Long id, @NotNull String googleId) {
        this.id = id;
        this.googleId = googleId;
    }

    @Generated(hash = 762027100)
    public DbUser() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGoogleId() {
        return this.googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

}
