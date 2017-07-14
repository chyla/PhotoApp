package org.chyla.photoapp.Synchronization;

import android.content.ComponentName;
import android.content.Intent;

import org.chyla.photoapp.BaseTest;
import org.chyla.photoapp.BuildConfig;
import org.chyla.photoapp.Main.MainActivity;
import org.chyla.photoapp.R;
import org.chyla.photoapp.Synchronization.Presenter.SynchronizationPresenter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class SynchronizationActivityTest extends BaseTest {

    @Mock
    private SynchronizationPresenter presenter;

    private ActivityController<SynchronizationActivity> controller;
    private SynchronizationActivity activity;
    private ShadowActivity shadowActivity;

    @Override
    public void setUp() {
        super.setUp();

        SynchronizationActivity synchronizationActivity = new SynchronizationActivity() {
            @Override
            public void setTheme(int resid) {
                super.setTheme(R.style.AppTheme_NoActionBar);
            }

            public SynchronizationPresenter getPresenter() {
                return presenter;
            }
        };

        controller = ActivityController.of(Robolectric.getShadowsAdapter(), synchronizationActivity).create().visible();
        activity = controller.get();
        shadowActivity = shadowOf(activity);
    }

    @Test
    public void testOnCreateShouldDoNothing() {
    }

    @Test
    public void testStartMainActivity() {
        activity.startMainActivity();

        Intent intent = shadowActivity.peekNextStartedActivity();
        assertEquals(new ComponentName(activity, MainActivity.class), intent.getComponent());
        assertTrue(shadowActivity.isFinishing());
    }

    @Test
    public void testOnStart() {
        activity.onStart();

        verify(presenter, times(1)).onStart();
    }

    @Test
    public void testOnStop() {
        presenter.onStop();

        verify(presenter, times(1)).onStop();
    }

}
