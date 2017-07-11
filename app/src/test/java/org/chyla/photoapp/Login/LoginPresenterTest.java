package org.chyla.photoapp.Login;

import org.chyla.photoapp.BaseTest;
import org.chyla.photoapp.Login.Presenter.LoginPresenter;
import org.chyla.photoapp.Login.Presenter.LoginPresenterImpl;
import org.chyla.photoapp.Model.Authenticator.Authenticator;
import org.chyla.photoapp.Model.Authenticator.Event.SuccessEvent;
import org.greenrobot.eventbus.EventBus;
import org.junit.After;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class LoginPresenterTest extends BaseTest {

    private final static String user = "user1";
    private final static String password = "password1";

    @Mock
    private EventBus eventBus;

    @Mock
    private Authenticator authenticator;

    @Mock
    private LoginView view;

    private LoginPresenter presenter;

    @Override
    public void setUp() {
        super.setUp();

        presenter = new LoginPresenterImpl(eventBus, view, authenticator);
    }

    @After
    public void tearDown() {
        verifyNoMoreInteractions(eventBus);
        verifyNoMoreInteractions(authenticator);
        verifyNoMoreInteractions(view);
    }

    @Test
    public void testAuthenticate() {
        presenter.authenticate(user, password);

        verify(view, times(1)).hideLoginForm();
        verify(view, times(1)).showProgress();
        verify(authenticator, times(1)).loginUser(user, password);
    }

    @Test
    public void testRegister() {
        presenter.register(user, password);

        verify(view, times(1)).hideLoginForm();
        verify(view, times(1)).showProgress();
        verify(authenticator, times(1)).register(user, password);
    }

    @Test
    public void testOnStart() {
        presenter.onStart();

        verify(eventBus, times(1)).register(presenter);
        verify(authenticator, times(1)).checkUserLoggedIn();
    }

    @Test
    public void testOnStart_OnSuccess() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                presenter.onSuccessEvent(new SuccessEvent());
                return null;
            }
        }).when(authenticator).checkUserLoggedIn();

        presenter.onStart();

        verify(eventBus, times(1)).register(presenter);
        verify(authenticator, times(1)).checkUserLoggedIn();
        verify(view, times(1)).startSynchronizeActivity();
    }

}
