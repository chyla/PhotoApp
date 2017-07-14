package org.chyla.photoapp.Main.DependencyInjection;

import android.content.Context;

import org.chyla.photoapp.Main.Configuration.detail.CloudinaryConfig;
import org.chyla.photoapp.Main.MainView;
import org.chyla.photoapp.Main.Model.InspectPhotosInteractor;
import org.chyla.photoapp.Main.Model.InspectPhotosInteractorImpl;
import org.chyla.photoapp.Main.Model.LastPhotoInteractor;
import org.chyla.photoapp.Main.Model.LastPhotoInteractorImpl;
import org.chyla.photoapp.Main.Model.NewPhotoInteractor;
import org.chyla.photoapp.Main.Model.NewPhotoInteractorImpl;
import org.chyla.photoapp.Main.Model.UserGalleryInteractor;
import org.chyla.photoapp.Main.Model.UserGalleryInteractorImpl;
import org.chyla.photoapp.Main.Presenter.MainPresenter;
import org.chyla.photoapp.Main.Presenter.MainPresenterImpl;
import org.chyla.photoapp.Main.Repository.CloudImageStorage.CloudStorageRepository;
import org.chyla.photoapp.Main.Repository.CloudImageStorage.Cloudinary.CloudinaryRepository;
import org.chyla.photoapp.Main.Repository.CloudPhotosExplorer.CloudPhotosExplorerRepository;
import org.chyla.photoapp.Main.Repository.CloudPhotosExplorer.Flickr.FlickrRepository;
import org.chyla.photoapp.Model.Authenticator.Authenticator;
import org.chyla.photoapp.Model.Authenticator.AuthenticatorImpl;
import org.chyla.photoapp.Repository.CloudDatabase.CloudDatabaseRepository;
import org.chyla.photoapp.Repository.CloudDatabase.Firebase.FirebaseRepository;
import org.chyla.photoapp.Repository.LocalDatabase.DatabaseRepository;
import org.chyla.photoapp.Repository.LocalDatabase.GreenDao.GreenDaoRepository;
import org.chyla.photoapp.Repository.Login.LoginRepository;
import org.chyla.photoapp.Repository.Login.LoginRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {

    private final MainView view;
    private final Context context;
    private final CloudinaryConfig cloudinaryConfig;
    private final String flickrApiKey;

    public MainModule(MainView view, Context context, CloudinaryConfig cloudinaryConfig, String flickrApiKey) {
        this.view = view;
        this.context = context;
        this.cloudinaryConfig = cloudinaryConfig;
        this.flickrApiKey = flickrApiKey;
    }

    @Provides
    @Singleton
    public MainPresenter providesMainPresenter(MainView view,
                                               Authenticator authenticator,
                                               LastPhotoInteractor lastPhotoInteractor,
                                               NewPhotoInteractor newPhotoInteractor,
                                               InspectPhotosInteractor inspectPhotosInteractor,
                                               UserGalleryInteractor userGalleryInteractor) {
        return new MainPresenterImpl(view, authenticator, lastPhotoInteractor, newPhotoInteractor, inspectPhotosInteractor, userGalleryInteractor);
    }

    @Provides
    @Singleton
    public Authenticator providesAuthenticator(LoginRepository loginRepository) {
        return new AuthenticatorImpl(loginRepository);
    }

    @Provides
    @Singleton
    public LastPhotoInteractor providesLastPhotoInteractor(Authenticator authenticator,
                                                           DatabaseRepository databaseRepository) {
        return new LastPhotoInteractorImpl(authenticator, databaseRepository);
    }

    @Provides
    @Singleton
    public NewPhotoInteractor providesNewPhotoInteractor(CloudStorageRepository cloudStorageRepository,
                                                         CloudDatabaseRepository cloudDatabaseRepository,
                                                         DatabaseRepository databaseRepository,
                                                         Authenticator authenticator) {
        return new NewPhotoInteractorImpl(cloudStorageRepository, cloudDatabaseRepository, databaseRepository, authenticator);
    }

    @Provides
    @Singleton
    public UserGalleryInteractor providesUserGalleryInteractor(Authenticator authenticator,
                                                               CloudDatabaseRepository cloudDatabaseRepository,
                                                               DatabaseRepository databaseRepository) {
        return new UserGalleryInteractorImpl(authenticator, cloudDatabaseRepository, databaseRepository);
    }

    @Provides
    @Singleton
    public InspectPhotosInteractor providesInspectPhotosInteractor(CloudPhotosExplorerRepository cloudPhotosExplorerRepository) {
        return new InspectPhotosInteractorImpl(cloudPhotosExplorerRepository);
    }

    @Provides
    @Singleton
    public LoginRepository providesLoginRepository() {
        return new LoginRepositoryImpl();
    }

    @Provides
    @Singleton
    public CloudStorageRepository providesCloudStorageRepository(CloudinaryConfig cloudinaryConfig) {
        return new CloudinaryRepository(cloudinaryConfig);
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
    public CloudPhotosExplorerRepository providesCloudPhotosExplorerRepository(String flickrApiKey) {
        return new FlickrRepository(flickrApiKey);
    }

    @Provides
    @Singleton
    public MainView providesMainView() {
        return view;
    }

    @Provides
    @Singleton
    public Context providesContext() {
        return context;
    }

    @Provides
    @Singleton
    public CloudinaryConfig providesCloudinaryConfig() {
        return cloudinaryConfig;
    }

    @Provides
    @Singleton
    public String providesFlickrApiKey() {
        return flickrApiKey;
    }

}
