package org.chyla.photoapp.Main;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v7.view.menu.ActionMenuItem;
import android.support.v7.view.menu.MenuItemImpl;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

import org.chyla.photoapp.BaseTest;
import org.chyla.photoapp.BuildConfig;
import org.chyla.photoapp.Main.Model.objects.Photo;
import org.chyla.photoapp.Main.Presenter.MainPresenter;
import org.chyla.photoapp.Main.View.Gallery.GalleryFragment;
import org.chyla.photoapp.Main.View.InspectPhotos.PhotoPreviewFragment.PhotoPreviewFragment;
import org.chyla.photoapp.Main.View.PhotoView.PhotoViewFragment;
import org.chyla.photoapp.R;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MainActivityTest extends BaseTest {

    @Mock
    private MainPresenter mainPresenter;

    private ActivityController<MainActivity> controller;
    MainActivity activity;
    private ShadowActivity shadowActivity;

    @Override
    public void setUp() {
        super.setUp();

        MainActivity mainActivity = new MainActivity() {
            @Override
            public void setTheme(int resid) {
                super.setTheme(R.style.AppTheme_NoActionBar);
            }

            @Override
            public MainPresenter getPresenter() {
                return mainPresenter;
            }
        };

        controller = ActivityController.of(Robolectric.getShadowsAdapter(), mainActivity).create().visible();
        activity = controller.get();
        shadowActivity = shadowOf(activity);
    }

    @After
    public void tearDown() {
        verify(mainPresenter, times(1)).showLastPhoto();

        verifyNoMoreInteractions(mainPresenter);
    }

    @Test
    public void testOnCreate() {
        // empty body
    }

    @Test
    public void testPhotoFragmentIsVisible() throws MalformedURLException {
        final Photo examplePhoto = new Photo("TITLE1", "DESCRIPTION1", new URL("http://example.com/"));

        activity.showPhoto(examplePhoto);

        PhotoViewFragment fragment = (PhotoViewFragment) activity.getFragmentManager().findFragmentByTag(PhotoViewFragment.TAG);
        assertNotNull(fragment);
    }

    @Test
    public void testUserGalleryFragmentIsVisible() throws MalformedURLException {
        final Photo examplePhoto = new Photo("TITLE1", "DESCRIPTION1", new URL("http://example.com/"));
        final Photo examplePhoto2 = new Photo("TITLE2", "DESCRIPTION2", new URL("http://example2.com/"));
        final List<Photo> gallery = new ArrayList<>();
        gallery.add(examplePhoto);
        gallery.add(examplePhoto2);

        activity.showUserGallery(gallery);

        GalleryFragment fragment = (GalleryFragment) activity.getFragmentManager().findFragmentByTag(GalleryFragment.TAG);
        assertNotNull(fragment);
    }

    @Test
    public void testInspectedPhotosGalleryFragmentIsVisible() throws MalformedURLException {
        final Photo examplePhoto = new Photo("TITLE1", "DESCRIPTION1", new URL("http://example.com/"));
        final Photo examplePhoto2 = new Photo("TITLE2", "DESCRIPTION2", new URL("http://example2.com/"));
        final List<Photo> gallery = new ArrayList<>();
        gallery.add(examplePhoto);
        gallery.add(examplePhoto2);

        activity.showUserGallery(gallery);

        GalleryFragment fragment = (GalleryFragment) activity.getFragmentManager().findFragmentByTag(GalleryFragment.TAG);
        assertNotNull(fragment);
    }

    @Test
    public void testPhotoPreviewFragmentIsVisible() throws MalformedURLException {
        final Photo examplePhoto = new Photo("TITLE1", "DESCRIPTION1", new URL("http://example.com/"));

        activity.showInspectedPhoto(examplePhoto);

        PhotoPreviewFragment fragment = (PhotoPreviewFragment) activity.getFragmentManager().findFragmentByTag(PhotoPreviewFragment.TAG);
        assertNotNull(fragment);
    }

    @Test
    public void testShareApp() {
        MenuItem item = new MenuItem() {
            @Override
            public int getItemId() {
                return R.id.nav_share;
            }

            @Override
            public int getGroupId() {
                return 0;
            }

            @Override
            public int getOrder() {
                return 0;
            }

            @Override
            public MenuItem setTitle(CharSequence title) {
                return null;
            }

            @Override
            public MenuItem setTitle(@StringRes int title) {
                return null;
            }

            @Override
            public CharSequence getTitle() {
                return null;
            }

            @Override
            public MenuItem setTitleCondensed(CharSequence title) {
                return null;
            }

            @Override
            public CharSequence getTitleCondensed() {
                return null;
            }

            @Override
            public MenuItem setIcon(Drawable icon) {
                return null;
            }

            @Override
            public MenuItem setIcon(@DrawableRes int iconRes) {
                return null;
            }

            @Override
            public Drawable getIcon() {
                return null;
            }

            @Override
            public MenuItem setIntent(Intent intent) {
                return null;
            }

            @Override
            public Intent getIntent() {
                return null;
            }

            @Override
            public MenuItem setShortcut(char numericChar, char alphaChar) {
                return null;
            }

            @Override
            public MenuItem setNumericShortcut(char numericChar) {
                return null;
            }

            @Override
            public char getNumericShortcut() {
                return 0;
            }

            @Override
            public MenuItem setAlphabeticShortcut(char alphaChar) {
                return null;
            }

            @Override
            public char getAlphabeticShortcut() {
                return 0;
            }

            @Override
            public MenuItem setCheckable(boolean checkable) {
                return null;
            }

            @Override
            public boolean isCheckable() {
                return false;
            }

            @Override
            public MenuItem setChecked(boolean checked) {
                return null;
            }

            @Override
            public boolean isChecked() {
                return false;
            }

            @Override
            public MenuItem setVisible(boolean visible) {
                return null;
            }

            @Override
            public boolean isVisible() {
                return false;
            }

            @Override
            public MenuItem setEnabled(boolean enabled) {
                return null;
            }

            @Override
            public boolean isEnabled() {
                return false;
            }

            @Override
            public boolean hasSubMenu() {
                return false;
            }

            @Override
            public SubMenu getSubMenu() {
                return null;
            }

            @Override
            public MenuItem setOnMenuItemClickListener(OnMenuItemClickListener menuItemClickListener) {
                return null;
            }

            @Override
            public ContextMenu.ContextMenuInfo getMenuInfo() {
                return null;
            }

            @Override
            public void setShowAsAction(int actionEnum) {

            }

            @Override
            public MenuItem setShowAsActionFlags(int actionEnum) {
                return null;
            }

            @Override
            public MenuItem setActionView(View view) {
                return null;
            }

            @Override
            public MenuItem setActionView(@LayoutRes int resId) {
                return null;
            }

            @Override
            public View getActionView() {
                return null;
            }

            @Override
            public MenuItem setActionProvider(ActionProvider actionProvider) {
                return null;
            }

            @Override
            public ActionProvider getActionProvider() {
                return null;
            }

            @Override
            public boolean expandActionView() {
                return false;
            }

            @Override
            public boolean collapseActionView() {
                return false;
            }

            @Override
            public boolean isActionViewExpanded() {
                return false;
            }

            @Override
            public MenuItem setOnActionExpandListener(OnActionExpandListener listener) {
                return null;
            }
        };

        activity.onNavigationItemSelected(item);

        Intent intent = shadowActivity.peekNextStartedActivity();
        assertEquals(Intent.ACTION_CHOOSER, intent.getAction());
    }

}
