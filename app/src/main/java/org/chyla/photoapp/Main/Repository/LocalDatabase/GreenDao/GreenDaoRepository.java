package org.chyla.photoapp.Main.Repository.LocalDatabase.GreenDao;

import android.content.Context;
import android.util.Log;

import org.chyla.photoapp.Main.Model.objects.Photo;
import org.chyla.photoapp.Main.Model.objects.User;
import org.chyla.photoapp.Main.Repository.LocalDatabase.DatabaseRepository;
import org.chyla.photoapp.Main.Repository.LocalDatabase.GreenDao.detail.DaoMaster;
import org.chyla.photoapp.Main.Repository.LocalDatabase.GreenDao.detail.DaoSession;
import org.chyla.photoapp.Main.Repository.LocalDatabase.GreenDao.detail.DbPhoto;
import org.chyla.photoapp.Main.Repository.LocalDatabase.GreenDao.detail.DbPhotoDao;
import org.chyla.photoapp.Main.Repository.LocalDatabase.GreenDao.detail.DbUser;
import org.chyla.photoapp.Main.Repository.LocalDatabase.GreenDao.detail.DbUserDao;
import org.greenrobot.greendao.database.Database;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class GreenDaoRepository implements DatabaseRepository {

    private final static String LOG_TAG = "GreenDaoRepository";
    private final static String DB_FILE_NAME = "pictures.db";

    private final DaoMaster.DevOpenHelper helper;
    private final Database database;
    private final DaoMaster daoMaster;
    private final DaoSession daoSession;

    private final DbUserDao userDao;
    private final DbPhotoDao photoDao;

    public GreenDaoRepository(final Context globalContext) {
        Log.d(LOG_TAG, "Creating database...");

        helper = new DaoMaster.DevOpenHelper(globalContext, DB_FILE_NAME, null);
        database = helper.getWritableDb();
        daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();

        userDao = daoSession.getDbUserDao();
        photoDao = daoSession.getDbPhotoDao();
    }

    @Override
    public void savePhoto(final User user, final Photo photo) {
        Log.d(LOG_TAG, "Adding photo to database...");

        try {
            database.beginTransaction();

            DbUser dbUser = toDbUser(user);
            userDao.insertOrReplace(dbUser);

            DbPhoto dbPhoto = toDbPhoto(photo);
            dbPhoto.setDbUser(dbUser);
            photoDao.insertOrReplace(dbPhoto);

            database.setTransactionSuccessful();
        }
        finally {
            database.endTransaction();
        }
    }

    @Override
    public List<Photo> getPhotosByUser(final User user) {
        final List<Photo> photos = new LinkedList<>();

        DbUser dbUser = getUserFromDb(user);
        if (dbUser != null) {
            final List<DbPhoto> dbPhotos = photoDao.queryBuilder()
                    .where(DbPhotoDao.Properties.DbUserId.eq(dbUser))
                    .list();

            for (final DbPhoto dbPhoto : dbPhotos) {
                try {
                    photos.add(toPhoto(dbPhoto));
                } catch (MalformedURLException e) {
                    Log.e(LOG_TAG, "Found photo with corrupted url: '" + dbPhoto.getUrl() + "' ignoring...");
                    e.printStackTrace();
                }
            }
        }

        return photos;
    }

    private DbUser toDbUser(final User user) {
        final DbUser dbUser = new DbUser();
        dbUser.setGoogleId(user.getGoogleUserId());

        return dbUser;
    }

    private DbPhoto toDbPhoto(final Photo photo) {
        final DbPhoto dbPhoto = new DbPhoto();
        dbPhoto.setTitle(photo.getTitle());
        dbPhoto.setDescription(photo.getDescription());
        dbPhoto.setUrl(photo.getUrl().toString());

        return dbPhoto;
    }

    private DbUser getUserFromDb(final User user) {
        final List<DbUser> users = userDao.queryBuilder()
                .where(DbUserDao.Properties.GoogleId.eq(user.getGoogleUserId()))
                .list();

        DbUser dbUser = null;

        if (users.isEmpty()) {
            Log.i(LOG_TAG, "DbUser not found in database, it's ok.");
        }
        else {
            Log.i(LOG_TAG, "Found " + users.size() + " user(s)");

            dbUser = users.get(0);
            Log.i(LOG_TAG, "Using first with ID=" + dbUser.getId());
        }

        return dbUser;
    }

    private Photo toPhoto(final DbPhoto dbPhoto) throws MalformedURLException {
        return new Photo(dbPhoto.getTitle(), dbPhoto.getDescription(), new URL(dbPhoto.getUrl()));
    }

}
