package org.chyla.photoapp.Main;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.chyla.photoapp.Login.LoginActivity;
import org.chyla.photoapp.Main.Configuration.CloudinaryPropertyReader;
import org.chyla.photoapp.Main.Configuration.FlickrPropertyReader;
import org.chyla.photoapp.Main.DependencyInjection.DaggerMainComponent;
import org.chyla.photoapp.Main.DependencyInjection.MainComponent;
import org.chyla.photoapp.Main.DependencyInjection.MainModule;
import org.chyla.photoapp.Main.View.Gallery.GalleryCallback;
import org.chyla.photoapp.Main.View.Gallery.GalleryFragment;
import org.chyla.photoapp.Main.View.InspectPhotos.PhotoPreviewFragment.PhotoPreviewActionListener;
import org.chyla.photoapp.Main.View.InspectPhotos.PhotoPreviewFragment.PhotoPreviewFragment;
import org.chyla.photoapp.Main.View.InspectPhotos.SearchPhotosFragment;
import org.chyla.photoapp.Main.Model.InspectPhotosInteractor;
import org.chyla.photoapp.Main.Model.InspectPhotosInteractorImpl;
import org.chyla.photoapp.Main.Model.LastPhotoInteractor;
import org.chyla.photoapp.Main.Model.LastPhotoInteractorImpl;
import org.chyla.photoapp.Main.Model.NewPhotoInteractor;
import org.chyla.photoapp.Main.Model.NewPhotoInteractorImpl;
import org.chyla.photoapp.Main.Model.UserGalleryInteractor;
import org.chyla.photoapp.Main.Model.UserGalleryInteractorImpl;
import org.chyla.photoapp.Main.Model.objects.Photo;
import org.chyla.photoapp.Main.View.NewPhoto.NewPhotoCallback;
import org.chyla.photoapp.Main.View.NewPhoto.NewPhotoDetailsFragment;
import org.chyla.photoapp.Main.View.PhotoView.PhotoViewFragment;
import org.chyla.photoapp.Main.Presenter.MainPresenter;
import org.chyla.photoapp.Main.Presenter.MainPresenterImpl;
import org.chyla.photoapp.Repository.CloudDatabase.CloudDatabaseRepository;
import org.chyla.photoapp.Repository.CloudDatabase.Firebase.FirebaseRepository;
import org.chyla.photoapp.Main.Repository.CloudImageStorage.CloudStorageRepository;
import org.chyla.photoapp.Main.Repository.CloudImageStorage.Cloudinary.CloudinaryRepository;
import org.chyla.photoapp.Main.Repository.CloudPhotosExplorer.CloudPhotosExplorerRepository;
import org.chyla.photoapp.Main.Repository.CloudPhotosExplorer.Flickr.FlickrRepository;
import org.chyla.photoapp.Repository.LocalDatabase.DatabaseRepository;
import org.chyla.photoapp.Repository.LocalDatabase.GreenDao.GreenDaoRepository;
import org.chyla.photoapp.Model.Authenticator.Authenticator;
import org.chyla.photoapp.Model.Authenticator.AuthenticatorImpl;
import org.chyla.photoapp.R;
import org.chyla.photoapp.Repository.Login.LoginRepository;
import org.chyla.photoapp.Repository.Login.LoginRepositoryImpl;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,MainView,
        ActivityCompat.OnRequestPermissionsResultCallback {

    private final static String LOG_TAG = "MainActivity";
    private final static int REQUEST_IMAGE_CAPTURE = 10;
    private final static int PERMISSIONS_REQUEST_PHOTO = 20;

    MainPresenter presenter;

    private String newPhotoPath;

    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    private final FragmentManager fragmentManager = getFragmentManager();
    private Fragment currentFragment;
    private List<Photo> inspectedPhotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        String flickrApiKey = null;

        try {
            setupInjection();

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });

            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            presenter.showLastPhoto();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupInjection() throws IOException {
        presenter =  getPresenter();
    }

    public MainPresenter getPresenter() throws IOException {
        CloudinaryPropertyReader cloudinaryPropertyReader = new CloudinaryPropertyReader(this);
        FlickrPropertyReader flickrPropertyReader = new FlickrPropertyReader(this);

        MainComponent component = DaggerMainComponent
                .builder()
                .mainModule(new MainModule(
                        this,
                        getApplicationContext(),
                        cloudinaryPropertyReader.getConfig(),
                        flickrPropertyReader.getApiKey()))
                .build();

        return component.getPresenter();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_main:
                presenter.showLastPhoto();
                break;

            case R.id.nav_gallery:
                presenter.showUserGallery();
                break;

            case R.id.nav_camera:
                takePhoto();
                break;

            case R.id.nav_inspect_photos:
                showSearchPhotosFragment();
                break;

            case R.id.nav_share:
                shareApp();
                break;

            case R.id.nav_logout:
                presenter.logoutUser();
                break;
        }

        fragmentManager.executePendingTransactions();
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void startLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void showPhoto(final Photo photo) {
        PhotoViewFragment fragment = new PhotoViewFragment();
        fragment.setPhoto(photo);
        showFragment(fragment);
    }

    @Override
    public void showUserGallery(List<Photo> photos) {
        Log.d(LOG_TAG, "Received " + photos.size() + " photos to show.");

        GalleryFragment fragment = new GalleryFragment();
        fragment.setCallback(new GalleryCallback() {
            @Override
            public void onGalleryPhotoClicked(final Photo photo) {
                presenter.showPhoto(photo);
            }
        });
        fragment.addPhotos(photos);

        showFragment(fragment);

    }

    @Override
    public void showInspectedPhotosGallery(final List<Photo> photos) {
        Log.d(LOG_TAG, "Received " + photos.size() + " photos to show.");

        inspectedPhotos = photos;

        GalleryFragment inspectedPhotosGallery = new GalleryFragment();
        inspectedPhotosGallery.setCallback(new GalleryCallback() {
            @Override
            public void onGalleryPhotoClicked(final Photo photo) {
                presenter.showInspectedPhoto(photo);
            }
        });
        inspectedPhotosGallery.addPhotos(photos);

        showFragment(inspectedPhotosGallery);
    }

    @Override
    public void showInspectedPhoto(final Photo photo) {
        Log.d(LOG_TAG, "Inspecting photo: " + photo.getUrl().toString());

        PhotoPreviewFragment fragment = new PhotoPreviewFragment();
        fragment.setPhoto(photo);
        fragment.setPhotoActionListener(new PhotoPreviewActionListener() {

            @Override
            public void onPhotoSave(final Photo photo) {
                Log.d(LOG_TAG, "onPhotoSave call with: " + photo.getUrl());
                presenter.addPhotoToGallery(photo);
            }

            @Override
            public void onPhotoDismiss() {
                Log.d(LOG_TAG, "onPhotoDismiss call");
                int index = inspectedPhotos.indexOf(photo);
                if (index == -1 || index + 1 >= inspectedPhotos.size()) {
                    Snackbar.make(drawer, "No more photos to view.", Snackbar.LENGTH_LONG).show();
                }
                else {
                    showInspectedPhoto(inspectedPhotos.get(index + 1));
                }
            }

        });
        showFragment(fragment);
    }

    @Override
    public void setUserMail(final String mail) {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        TextView mailView = (TextView) header.findViewById(R.id.text_nav_mail);
        mailView.setText(mail);
    }

    private void takePhoto() {
        Log.i(LOG_TAG, "SDK Version: " + Build.VERSION.SDK_INT);

        if (Build.VERSION.SDK_INT >= 23) {
            int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                Log.i(LOG_TAG, "Requesting for permission: WRITE_EXTERNAL_STORAGE");

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_PHOTO);
            } else {
                Log.i(LOG_TAG, "Permission WRITE_EXTERNAL_STORAGE granted");
                dispatchPhotoIntent();
            }
        }
        else {
            dispatchPhotoIntent();
        }
    }

    private void dispatchPhotoIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) == null) {
            Log.e(LOG_TAG, "Can't take a photo. App not found.");
            Log.e(LOG_TAG, "Please install photo app.");
        }
        else {
            try {
                Log.i(LOG_TAG, "Photo app found, starting activity...");
                File photoFile = getPhotoFile();
                newPhotoPath = photoFile.getPath();

                Uri photoUri = FileProvider.getUriForFile(this, "org.chyla.photoapp.fileprovider", photoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);

                Log.d(LOG_TAG, "Starting photo activity with uri: " + photoUri.toString());

                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            } catch (IOException ex) {
                Log.e(LOG_TAG, "Error while creating photo file: " + ex.getMessage());
            }
        }
    }

    private File getPhotoFile() throws IOException {
        final String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        final String imageFileName = "PhotoApp_" + timeStamp + "_";

        final File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        final File photoFile = File.createTempFile(imageFileName, ".jpg", storageDir);

        Log.d(LOG_TAG, "Temporary photo file: " + photoFile.toString());

        return photoFile;
    }

    private void shareApp() {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_SUBJECT, "PhotoApp");

        String sAux = "\nLet me recommend you this application\n\nmarket://details?id=org.chyla.photoapp\n\n";
        i.putExtra(Intent.EXTRA_TEXT, sAux);

        startActivity(Intent.createChooser(i, "choose one"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(LOG_TAG, "onActivityResult called...");

        if (resultCode == RESULT_OK && requestCode == REQUEST_IMAGE_CAPTURE) {
            Log.d(LOG_TAG, "Succesfully returned from Photo app.");

            NewPhotoDetailsFragment fragment = new NewPhotoDetailsFragment();
            fragment.setNewPhotoCallback(new NewPhotoCallback() {
                @Override
                public void onCreateNewPhotoCallback(final String title, final String description) {
                    presenter.addNewPhoto(title, description, newPhotoPath);
                }
            });
            showFragment(fragment);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_PHOTO:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED))
                    dispatchPhotoIntent();
                break;

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

    private void showSearchPhotosFragment() {
        SearchPhotosFragment fragment = new SearchPhotosFragment();
        fragment.setPresenter(presenter);
        showFragment(fragment);
    }

    private void showFragment(Fragment fragment) {
        currentFragment = fragment;

        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.fragment_container, currentFragment);
        ft.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    protected void onStop() {
        presenter.onStop();
        super.onStop();
    }

}
