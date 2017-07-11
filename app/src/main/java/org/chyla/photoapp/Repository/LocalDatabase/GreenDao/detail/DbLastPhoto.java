package org.chyla.photoapp.Repository.LocalDatabase.GreenDao.detail;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

@Entity(
        indexes = {
                @Index(value = "dbUserId", unique = true)
        }
)
public class DbLastPhoto {

    @Id
    private Long id;

    @NotNull
    private Long dbUserId;

    @ToOne(joinProperty ="dbUserId")
    private DbUser dbUser;

    @NotNull
    private Long dbPhotoId;

    @ToOne(joinProperty = "dbPhotoId")
    private DbPhoto dbPhoto;

/** Used to resolve relations */
@Generated(hash = 2040040024)
private transient DaoSession daoSession;

/** Used for active entity operations. */
@Generated(hash = 824648777)
private transient DbLastPhotoDao myDao;

@Generated(hash = 852254455)
public DbLastPhoto(Long id, @NotNull Long dbUserId, @NotNull Long dbPhotoId) {
    this.id = id;
    this.dbUserId = dbUserId;
    this.dbPhotoId = dbPhotoId;
}

@Generated(hash = 80732192)
public DbLastPhoto() {
}

public Long getId() {
    return this.id;
}

public void setId(Long id) {
    this.id = id;
}

public Long getDbUserId() {
    return this.dbUserId;
}

public void setDbUserId(Long dbUserId) {
    this.dbUserId = dbUserId;
}

public Long getDbPhotoId() {
    return this.dbPhotoId;
}

public void setDbPhotoId(Long dbPhotoId) {
    this.dbPhotoId = dbPhotoId;
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

@Generated(hash = 1021904136)
private transient Long dbPhoto__resolvedKey;

/** To-one relationship, resolved on first access. */
@Generated(hash = 72061986)
public DbPhoto getDbPhoto() {
    Long __key = this.dbPhotoId;
    if (dbPhoto__resolvedKey == null || !dbPhoto__resolvedKey.equals(__key)) {
        final DaoSession daoSession = this.daoSession;
        if (daoSession == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        DbPhotoDao targetDao = daoSession.getDbPhotoDao();
        DbPhoto dbPhotoNew = targetDao.load(__key);
        synchronized (this) {
            dbPhoto = dbPhotoNew;
            dbPhoto__resolvedKey = __key;
        }
    }
    return dbPhoto;
}

/** called by internal mechanisms, do not call yourself. */
@Generated(hash = 1375547681)
public void setDbPhoto(@NotNull DbPhoto dbPhoto) {
    if (dbPhoto == null) {
        throw new DaoException(
                "To-one property 'dbPhotoId' has not-null constraint; cannot set to-one to null");
    }
    synchronized (this) {
        this.dbPhoto = dbPhoto;
        dbPhotoId = dbPhoto.getId();
        dbPhoto__resolvedKey = dbPhotoId;
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

/** called by internal mechanisms, do not call yourself. */
@Generated(hash = 881012768)
public void __setDaoSession(DaoSession daoSession) {
    this.daoSession = daoSession;
    myDao = daoSession != null ? daoSession.getDbLastPhotoDao() : null;
}

}
