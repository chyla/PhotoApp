package org.chyla.photoapp.Main.Repository.LocalDatabase.GreenDao.detail;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;

@Entity(
        indexes = {
                @Index(value = "dbUserId,title,description,url", unique = true)
        }
)
public class DbPhoto {

    @Id
    private Long id;

    @NotNull
    private Long dbUserId;

    @ToOne(joinProperty = "dbUserId")
    private DbUser dbUser;

    @NotNull
    private String title;

    @NotNull
    private String description;

    @NotNull
    private String url;

/** Used to resolve relations */
@Generated(hash = 2040040024)
private transient DaoSession daoSession;

/** Used for active entity operations. */
@Generated(hash = 1256174471)
private transient DbPhotoDao myDao;

@Generated(hash = 1342142122)
public DbPhoto(Long id, @NotNull Long dbUserId, @NotNull String title,
        @NotNull String description, @NotNull String url) {
    this.id = id;
    this.dbUserId = dbUserId;
    this.title = title;
    this.description = description;
    this.url = url;
}

@Generated(hash = 95134667)
public DbPhoto() {
}

public Long getId() {
    return this.id;
}

public void setId(Long id) {
    this.id = id;
}

public String getTitle() {
    return this.title;
}

public void setTitle(String title) {
    this.title = title;
}

public String getDescription() {
    return this.description;
}

public void setDescription(String description) {
    this.description = description;
}

public String getUrl() {
    return this.url;
}

public void setUrl(String url) {
    this.url = url;
}

@Generated(hash = 2087091035)
private transient Long dbUser__resolvedKey;

/** To-one relationship, resolved on first access. */
@Generated(hash = 634038330)
public DbUser getDbUser() {
    Long __key = this.dbUserId;
    if (dbUser__resolvedKey == null || !dbUser__resolvedKey.equals(__key)) {
        final DaoSession daoSession = this.daoSession;
        if (daoSession == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        DbUserDao targetDao = daoSession.getDbUserDao();
        DbUser dbUserNew = targetDao.load(__key);
        synchronized (this) {
            dbUser = dbUserNew;
            dbUser__resolvedKey = __key;
        }
    }
    return dbUser;
}

/** called by internal mechanisms, do not call yourself. */
@Generated(hash = 1401125552)
public void setDbUser(@NotNull DbUser dbUser) {
    if (dbUser == null) {
        throw new DaoException(
                "To-one property 'dbUserId' has not-null constraint; cannot set to-one to null");
    }
    synchronized (this) {
        this.dbUser = dbUser;
        dbUserId = dbUser.getId();
        dbUser__resolvedKey = dbUserId;
    }
}

/**
 * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
 * Entity must attached to an entity context.
 */
@Generated(hash = 128553479)
public void delete() {
    if (myDao == null) {
        throw new DaoException("Entity is detached from DAO context");
    }
    myDao.delete(this);
}

/**
 * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
 * Entity must attached to an entity context.
 */
@Generated(hash = 1942392019)
public void refresh() {
    if (myDao == null) {
        throw new DaoException("Entity is detached from DAO context");
    }
    myDao.refresh(this);
}

/**
 * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
 * Entity must attached to an entity context.
 */
@Generated(hash = 713229351)
public void update() {
    if (myDao == null) {
        throw new DaoException("Entity is detached from DAO context");
    }
    myDao.update(this);
}

public Long getDbUserId() {
    return this.dbUserId;
}

public void setDbUserId(Long dbUserId) {
    this.dbUserId = dbUserId;
}

/** called by internal mechanisms, do not call yourself. */
@Generated(hash = 893296397)
public void __setDaoSession(DaoSession daoSession) {
    this.daoSession = daoSession;
    myDao = daoSession != null ? daoSession.getDbPhotoDao() : null;
}

}