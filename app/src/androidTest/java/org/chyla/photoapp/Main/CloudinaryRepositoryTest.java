package org.chyla.photoapp.Main;

import android.support.test.runner.AndroidJUnit4;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;

import org.chyla.photoapp.BaseTest;
import org.chyla.photoapp.Main.Configuration.detail.CloudinaryConfig;
import org.chyla.photoapp.Main.Repository.CloudImageStorage.CloudStorageRepository;
import org.chyla.photoapp.Main.Repository.CloudImageStorage.Cloudinary.CloudinaryRepository;
import org.chyla.photoapp.Main.Repository.CloudImageStorage.UploadImageCallback;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class CloudinaryRepositoryTest extends BaseTest {

    @Mock
    Cloudinary cloudinary;

    @Mock
    Uploader uploader;

    @Mock
    UploadImageCallback callback;

    CloudStorageRepository repository;

    @Override
    public void setUp() throws IOException {
        super.setUp();

        CloudinaryConfig config = new CloudinaryConfig("NAME", "KEY", "SECRET");

        repository = new CloudinaryRepository(config) {
            @Override
            protected Cloudinary getCloudinary(CloudinaryConfig config) {
                return cloudinary;
            }
        };
    }

    @After
    public void tearDown() {
        verifyNoMoreInteractions(cloudinary);
        verifyNoMoreInteractions(uploader);
        verifyNoMoreInteractions(callback);
    }

    @Test
    public void testConstructorShouldDoNothing() {
        // empty test
    }

    @Test
    public void testUploadPhotoWithSecureAddress() throws InterruptedException, IOException {
        final Object syncObject = new Object();
        final String filePath = "FILE_PATH";
        final String secureAddress = "https://secure.connection.com";

        when(cloudinary.uploader()).thenAnswer(new Answer<Uploader>() {
            @Override
            public Uploader answer(InvocationOnMock invocationOnMock) throws Throwable {
                return uploader;
            }
        });

        when(uploader.upload(filePath, Cloudinary.asMap())).thenAnswer(new Answer<Map>() {
            @Override
            public Map answer(InvocationOnMock invocationOnMock) throws Throwable {
                Map result = new HashMap();
                result.put("secure_url", secureAddress);
                return result;
            }
        });

        final ArgumentCaptor<URL> successCaptor = ArgumentCaptor.forClass(URL.class);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                synchronized (syncObject){
                    syncObject.notify();
                }
                return null;
            }
        }).when(callback).onSuccess(successCaptor.capture());


        repository.uploadPhoto(filePath, callback);


        synchronized (syncObject){
            syncObject.wait();
        }

        verify(cloudinary, times(1)).uploader();
        verify(uploader, times(1)).upload(filePath, Cloudinary.asMap());
        verify(callback, times(1)).onSuccess(new URL(secureAddress));
    }

    @Test
    public void testUploadPhotoWithUnencryptedAddress() throws InterruptedException, IOException {
        final Object syncObject = new Object();
        final String filePath = "FILE_PATH";
        final String standardAddress = "http://secure.connection.com";

        when(cloudinary.uploader()).thenAnswer(new Answer<Uploader>() {
            @Override
            public Uploader answer(InvocationOnMock invocationOnMock) throws Throwable {
                return uploader;
            }
        });

        when(uploader.upload(filePath, Cloudinary.asMap())).thenAnswer(new Answer<Map>() {
            @Override
            public Map answer(InvocationOnMock invocationOnMock) throws Throwable {
                Map result = new HashMap();
                result.put("secure_url", standardAddress);
                return result;
            }
        });

        final ArgumentCaptor<URL> successCaptor = ArgumentCaptor.forClass(URL.class);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                synchronized (syncObject){
                    syncObject.notify();
                }
                return null;
            }
        }).when(callback).onSuccess(successCaptor.capture());


        repository.uploadPhoto(filePath, callback);


        synchronized (syncObject){
            syncObject.wait();
        }

        verify(cloudinary, times(1)).uploader();
        verify(uploader, times(1)).upload(filePath, Cloudinary.asMap());
        verify(callback, times(1)).onSuccess(new URL(standardAddress));
    }

    @Test
    public void testUploadPhotoWhenNoAddressIsAvailable() throws InterruptedException, IOException {
        final Object syncObject = new Object();
        final String filePath = "FILE_PATH";
        final String errorMessage = "Unable to find image url.";

        when(cloudinary.uploader()).thenAnswer(new Answer<Uploader>() {
            @Override
            public Uploader answer(InvocationOnMock invocationOnMock) throws Throwable {
                return uploader;
            }
        });

        when(uploader.upload(filePath, Cloudinary.asMap())).thenAnswer(new Answer<Map>() {
            @Override
            public Map answer(InvocationOnMock invocationOnMock) throws Throwable {
                Map result = new HashMap();
                return result;
            }
        });

        final ArgumentCaptor<String> failureCaptor = ArgumentCaptor.forClass(String.class);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                synchronized (syncObject){
                    syncObject.notify();
                }
                return null;
            }
        }).when(callback).onFailure(failureCaptor.capture());


        repository.uploadPhoto(filePath, callback);


        synchronized (syncObject){
            syncObject.wait();
        }

        verify(cloudinary, times(1)).uploader();
        verify(uploader, times(1)).upload(filePath, Cloudinary.asMap());
        verify(callback, times(1)).onFailure(errorMessage);
    }

    @Test
    public void testUploadPhotoWhenAddressIsIncorrect() throws InterruptedException, IOException {
        final Object syncObject = new Object();
        final String filePath = "FILE_PATH";
        final String incorrectAddress = "example";
        final String errorMessage = "no protocol: example";

        when(cloudinary.uploader()).thenAnswer(new Answer<Uploader>() {
            @Override
            public Uploader answer(InvocationOnMock invocationOnMock) throws Throwable {
                return uploader;
            }
        });

        when(uploader.upload(filePath, Cloudinary.asMap())).thenAnswer(new Answer<Map>() {
            @Override
            public Map answer(InvocationOnMock invocationOnMock) throws Throwable {
                Map result = new HashMap();
                result.put("secure_url", incorrectAddress);
                return result;
            }
        });

        final ArgumentCaptor<String> failureCaptor = ArgumentCaptor.forClass(String.class);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                synchronized (syncObject){
                    syncObject.notify();
                }
                return null;
            }
        }).when(callback).onFailure(failureCaptor.capture());


        repository.uploadPhoto(filePath, callback);


        synchronized (syncObject){
            syncObject.wait();
        }

        verify(cloudinary, times(1)).uploader();
        verify(uploader, times(1)).upload(filePath, Cloudinary.asMap());
        verify(callback, times(1)).onFailure(failureCaptor.getValue());
        assertEquals(errorMessage, failureCaptor.getValue());
    }

}
