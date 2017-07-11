package org.chyla.photoapp.Login;

import android.content.ComponentName;
import android.content.Intent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import org.chyla.photoapp.BaseTest;
import org.chyla.photoapp.BuildConfig;
import org.chyla.photoapp.Login.Presenter.LoginPresenter;
import org.chyla.photoapp.R;
import org.chyla.photoapp.Synchronization.SynchronizationActivity;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class LoginActivityTest extends BaseTest {

    private final static String user = "user1";
    private final static String password = "password1";

    @Mock
    private LoginPresenter presenter;

    LoginActivity activity;

    private ShadowActivity shadowActivity;
    private ActivityController<LoginActivity> controller;

    private Button signInButton;
    private Button registerButton;
    private AutoCompleteTextView emailEdit;
    private EditText passwordEdit;
    private View loginForm;
    private View progressView;

    @Override
    public void setUp() {
        super.setUp();

        LoginActivity loginActivity = new LoginActivity() {

            @Override
            public void setTheme(int resid) {
                super.setTheme(R.style.AppTheme_NoActionBar);
            }

            public LoginPresenter getPresenter() {
                return presenter;
            }
        };

        controller = ActivityController.of(Robolectric.getShadowsAdapter(), loginActivity).create().visible();
        activity = controller.get();
        shadowActivity = shadowOf(activity);


        signInButton = (Button) shadowActivity.findViewById(R.id.email_sign_in_button);
        registerButton = (Button) shadowActivity.findViewById(R.id.email_register_button);
        emailEdit = (AutoCompleteTextView) shadowActivity.findViewById(R.id.email);
        passwordEdit = (EditText) shadowActivity.findViewById(R.id.password);
        loginForm = (View) shadowActivity.findViewById(R.id.login_form);
        progressView = (View) shadowActivity.findViewById(R.id.login_progress);
    }

    @After
    public void tearDown() {
        verifyNoMoreInteractions(presenter);
    }

    @Test
    public void testOnCreate_ShouldDoNothing() {
    }

    @Test
    public void testSignInButtonClicked_schouldAuthenticateUser() {
        emailEdit.setText(user);
        passwordEdit.setText(password);

        signInButton.performClick();

        verify(presenter, times(1)).authenticate(user, password);
    }

    @Test
    public void testRegisterButtonClicked_schouldRegisterUser() {
        emailEdit.setText(user);
        passwordEdit.setText(password);

        registerButton.performClick();

        verify(presenter, times(1)).register(user, password);
    }

    @Test
    public void testStartSynchronizeActivity() {
        activity.startSynchronizeActivity();

        Intent intent = shadowActivity.peekNextStartedActivity();
        assertEquals(new ComponentName(activity, SynchronizationActivity.class), intent.getComponent());
        assertTrue(shadowActivity.isFinishing());
    }

    @Test
    public void testShowProgress_schouldShowAnimation() {
        activity.showProgress();

        assertEquals(View.VISIBLE, progressView.getVisibility());
    }

    @Test
    public void testHideProgress_schouldHideAnimation() {
        activity.hideProgress();

        assertEquals(View.GONE, progressView.getVisibility());
    }

}
