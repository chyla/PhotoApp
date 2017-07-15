package org.chyla.photoapp.Repository;

import android.support.test.runner.AndroidJUnit4;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.chyla.photoapp.BaseTest;
import org.chyla.photoapp.Main.Model.objects.Photo;
import org.chyla.photoapp.Main.Model.objects.User;
import org.chyla.photoapp.Repository.CloudDatabase.CloudDatabaseRepository;
import org.chyla.photoapp.Repository.CloudDatabase.Firebase.FirebaseRepository;
import org.chyla.photoapp.Repository.CloudDatabase.Firebase.detail.DbPhoto;
import org.chyla.photoapp.Repository.CloudDatabase.LastPhotoCallback;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class FirebaseRepositoryTest extends BaseTest {

    @Mock
    DatabaseReference databaseReference;

    @Mock
    DatabaseReference child1;

    @Mock
    DatabaseReference child2;

    @Mock
    DatabaseReference child3;

    @Mock
    DataSnapshot snapshot;

    @Mock
    LastPhotoCallback lastPhotoCallback;

    CloudDatabaseRepository repository;

    @Override
    public void setUp() throws IOException {
        super.setUp();

        repository = new FirebaseRepository() {
            @Override
            protected DatabaseReference getDatabaseReference() {
                return databaseReference;
            }
        };

    }

    @After
    public void tearDown() throws IOException {
        verifyNoMoreInteractions(databaseReference);
        verifyNoMoreInteractions(child1);
        verifyNoMoreInteractions(child2);
        verifyNoMoreInteractions(child3);
    }

    @Test
    public void testShouldDoNothing() {
        // empty body
    }

    @Test
    public void testSavePhoto() throws MalformedURLException, InterruptedException {
        final User exampleUser = new User("USER_ID1", "mail@example.com");
        final Photo examplePhoto = new Photo("TITLE1", "DESCRIPTION1", new URL("http://example.com/"));

        when(databaseReference.child(exampleUser.getGoogleUserId())).thenAnswer(new Answer<DatabaseReference>() {
            @Override
            public DatabaseReference answer(InvocationOnMock invocationOnMock) throws Throwable {
                return child1;
            }
        });
        when(child1.child("gallery")).thenAnswer(new Answer<DatabaseReference>() {
            @Override
            public DatabaseReference answer(InvocationOnMock invocationOnMock) throws Throwable {
                return child2;
            }
        });
        when(child2.push()).thenAnswer(new Answer<DatabaseReference>() {
            @Override
            public DatabaseReference answer(InvocationOnMock invocationOnMock) throws Throwable {
                return child3;
            }
        });
        final ArgumentCaptor<DbPhoto> child3Captor = ArgumentCaptor.forClass(DbPhoto.class);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                return null;
            }
        }).when(child3).setValue(child3Captor.capture());


        repository.savePhoto(exampleUser, examplePhoto);


        Thread.sleep(1000);

        verify(databaseReference, times(1)).child(exampleUser.getGoogleUserId());
        verify(child1, times(1)).child("gallery");
        verify(child2, times(1)).push();
        verify(child3, times(1)).setValue(child3Captor.getValue());
        assertEquals(examplePhoto.getTitle(), child3Captor.getValue().title);
        assertEquals(examplePhoto.getDescription(), child3Captor.getValue().description);
        assertEquals(examplePhoto.getUrl().toString(), child3Captor.getValue().url);
    }

    @Test
    public void testSaveLastPhoto() throws MalformedURLException, InterruptedException {
        final User exampleUser = new User("USER_ID1", "mail@example.com");
        final Photo examplePhoto = new Photo("TITLE1", "DESCRIPTION1", new URL("http://example.com/"));

        when(databaseReference.child(exampleUser.getGoogleUserId())).thenAnswer(new Answer<DatabaseReference>() {
            @Override
            public DatabaseReference answer(InvocationOnMock invocationOnMock) throws Throwable {
                return child1;
            }
        });
        when(child1.child("lastPhoto")).thenAnswer(new Answer<DatabaseReference>() {
            @Override
            public DatabaseReference answer(InvocationOnMock invocationOnMock) throws Throwable {
                return child2;
            }
        });
        final ArgumentCaptor<DbPhoto> child2Captor = ArgumentCaptor.forClass(DbPhoto.class);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                return null;
            }
        }).when(child2).setValue(child2Captor.capture());


        repository.saveLastPhoto(exampleUser, examplePhoto);


        Thread.sleep(1000);

        verify(databaseReference, times(1)).child(exampleUser.getGoogleUserId());
        verify(child1, times(1)).child("lastPhoto");
        verify(child2, times(1)).setValue(child2Captor.getValue());
        assertEquals(examplePhoto.getTitle(), child2Captor.getValue().title);
        assertEquals(examplePhoto.getDescription(), child2Captor.getValue().description);
        assertEquals(examplePhoto.getUrl().toString(), child2Captor.getValue().url);
    }

}
