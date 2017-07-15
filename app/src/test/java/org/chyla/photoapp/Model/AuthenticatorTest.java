package org.chyla.photoapp.Model;

import org.chyla.photoapp.BaseTest;
import org.chyla.photoapp.Model.Authenticator.Authenticator;
import org.chyla.photoapp.Model.Authenticator.AuthenticatorImpl;
import org.chyla.photoapp.Model.Authenticator.Event.ErrorEvent;
import org.chyla.photoapp.Repository.Login.LoginRepository;
import org.greenrobot.eventbus.EventBus;
import org.junit.After;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class AuthenticatorTest extends BaseTest {

    @Mock
    LoginRepository repository;

    @Mock
    EventBus eventBus;

    Authenticator authenticator;

    @Override
    public void setUp() {
        super.setUp();

        authenticator = new AuthenticatorImpl(repository) {
            @Override
            protected EventBus getEventBus() {
                return eventBus;
            }
        };
    }

    @After
    public void tearDown() {
        verifyNoMoreInteractions(repository);
        verifyNoMoreInteractions(eventBus);
    }

    @Test
    public void testLoginUser() {
        final String userMail = "user1@example.com";
        final String password = "PASSWORD1";

        authenticator.loginUser(userMail, password);

        verify(repository, times(1)).login(userMail, password);
    }

    @Test
    public void testLoginWrongUserName() {
        final String userMail = "user1";
        final String password = "PASSWORD1";

        authenticator.loginUser(userMail, password);

        final ArgumentCaptor<ErrorEvent> error = ArgumentCaptor.forClass(ErrorEvent.class);
        verify(eventBus, times(1)).post(error.capture());
        assertEquals(ErrorEvent.Type.MAIL_ERROR, error.getValue().getType());
    }

    @Test
    public void testLoginWrongPassword() {
        final String userMail = "user1@example.com";
        final String password = "pswd";

        authenticator.loginUser(userMail, password);

        final ArgumentCaptor<ErrorEvent> error = ArgumentCaptor.forClass(ErrorEvent.class);
        verify(eventBus, times(1)).post(error.capture());
        assertEquals(ErrorEvent.Type.PASSWORD_ERROR, error.getValue().getType());
    }

    @Test
    public void testRegisterUser() {
        final String userMail = "user1@example.com";
        final String password = "PASSWORD1";

        authenticator.register(userMail, password);

        verify(repository, times(1)).register(userMail, password);
    }

    @Test
    public void testRegisterWrongUserName() {
        final String userMail = "user1";
        final String password = "PASSWORD1";

        authenticator.register(userMail, password);

        final ArgumentCaptor<ErrorEvent> error = ArgumentCaptor.forClass(ErrorEvent.class);
        verify(eventBus, times(1)).post(error.capture());
        assertEquals(ErrorEvent.Type.MAIL_ERROR, error.getValue().getType());
    }

    @Test
    public void testRegisterWrongPassword() {
        final String userMail = "user1@example.com";
        final String password = "pswd";

        authenticator.register(userMail, password);

        final ArgumentCaptor<ErrorEvent> error = ArgumentCaptor.forClass(ErrorEvent.class);
        verify(eventBus, times(1)).post(error.capture());
        assertEquals(ErrorEvent.Type.PASSWORD_ERROR, error.getValue().getType());
    }

    @Test
    public void testLogout() {
        authenticator.logoutUser();

        verify(repository, times(1)).logout();
    }

}
