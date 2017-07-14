package org.chyla.photoapp.Synchronization.DependencyInjection;

import android.content.Context;

import org.chyla.photoapp.Main.Configuration.detail.CloudinaryConfig;
import org.chyla.photoapp.Main.Repository.CloudImageStorage.CloudStorageRepository;
import org.chyla.photoapp.Main.Repository.CloudImageStorage.Cloudinary.CloudinaryRepository;
import org.chyla.photoapp.Model.Authenticator.Authenticator;
import org.chyla.photoapp.Model.Authenticator.AuthenticatorImpl;
import org.chyla.photoapp.Repository.CloudDatabase.CloudDatabaseRepository;
import org.chyla.photoapp.Repository.CloudDatabase.Firebase.FirebaseRepository;
import org.chyla.photoapp.Repository.LocalDatabase.DatabaseRepository;
import org.chyla.photoapp.Repository.LocalDatabase.GreenDao.GreenDaoRepository;
import org.chyla.photoapp.Repository.Login.LoginRepository;
import org.chyla.photoapp.Repository.Login.LoginRepositoryImpl;
import org.chyla.photoapp.Synchronization.Model.SynchronizeDataInteractor;
import org.chyla.photoapp.Synchronization.Model.SynchronizeDataInteractorImpl;
import org.chyla.photoapp.Synchronization.Presenter.SynchronizationPresenter;
import org.chyla.photoapp.Synchronization.Presenter.SynchronizationPresenterImpl;
import org.chyla.photoapp.Synchronization.View;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class SynchronizationModule {

    private final View view;
    private final Context context;

    public SynchronizationModule(View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Provides
    @Singleton
    public SynchronizationPresenter providesSynchronizationPresenter(SynchronizeDataInteractor synchronizeDataInteractor, View view) {
        return new SynchronizationPresenterImpl(synchronizeDataInteractor, view);
    }

    @Provides
    @Singleton
    public SynchronizeDataInteractor providesSynchronizeDataInteractor(final CloudDatabaseRepository cloudDatabase, final DatabaseRepository localDatabase, final Authenticator authenticator) {
        return new SynchronizeDataInteractorImpl(cloudDatabase, localDatabase, authenticator);
    }

    @Provides
    @Singleton
    public Authenticator providesAuthenticator(LoginRepository loginRepository) {
        return new AuthenticatorImpl(loginRepository);
    }

    @Provides
    @Singleton
    public LoginRepository providesLoginRepository() {
        return new LoginRepositoryImpl();
    }

    @Provides
    @Singleton
    public DatabaseRepository providesDatabaseRepository(Context context) {
        return new GreenDaoRepository(context);
    }

    @Provides
    @Singleton
    public CloudDatabaseRepository providesCloudDatabaseRepository() {
        return new FirebaseRepository();
    }

    @Provides
    @Singleton
    public View providesView() {
        return view;
    }

    @Provides
    @Singleton
    public Context providesContext() {
        return context;
    }

}
