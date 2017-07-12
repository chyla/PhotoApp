package org.chyla.photoapp.Main;

import android.support.test.runner.AndroidJUnit4;

import org.chyla.photoapp.BaseTest;
import org.chyla.photoapp.Main.Model.objects.Photo;
import org.chyla.photoapp.Main.Repository.CloudPhotosExplorer.Flickr.FlickrRepository;
import org.chyla.photoapp.Main.Repository.CloudPhotosExplorer.GetPhotosCallback;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(AndroidJUnit4.class)
public class FlickrRepositoryTest extends BaseTest {

    private final static String SERVER_CALL1 = "/?method=flickr.photos.search&extras=description&api_key=API_KEY_1&tags=tag1,tag2";
    private final static String RESPONSE1 =
            "<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n" +
            "<rsp stat=\"ok\">\n" +
            "<photos page=\"1\" pages=\"2719\" perpage=\"100\" total=\"271897\">\n" +
              "<photo id=\"101\" owner=\"OWNER1\" secret=\"SECRET1\" server=\"102\" farm=\"1\" title=\"TITLE1\" ispublic=\"1\" isfriend=\"0\" isfamily=\"0\" >\n" +
                "<description>DESCRIPTION1</description>\n" +
              "</photo>\n" +
            "<photo id=\"201\" owner=\"OWNER2\" secret=\"SECRET2\" server=\"202\" farm=\"2\" title=\"TITLE2\" ispublic=\"1\" isfriend=\"0\" isfamily=\"0\" />\n" +
            "</photos>\n" +
            "</rsp>";

    private final static String API_KEY = "API_KEY_1";
    private String BASE_URL;
    private MockWebServer server;

    @Mock
    private GetPhotosCallback callback;

    FlickrRepository repository;

    @Override
    public void setUp() throws IOException {
        super.setUp();

        setupServer(SERVER_CALL1, RESPONSE1);
        repository = new FlickrRepository(API_KEY, BASE_URL);
    }

    private void setupServer(String serverCall, String response) throws IOException {
        server = new MockWebServer();
        server.enqueue(new MockResponse().setBody(response));
        server.start();

        HttpUrl url = server.url(serverCall);
        BASE_URL = url.toString();
    }

    @After
    public void tearDown() throws IOException {
        verifyNoMoreInteractions(callback);
        server.shutdown();
    }

    @Test
    public void testGetPhotosByTags() throws InterruptedException, IOException {
        final Object syncObject = new Object();

        List<String> tags = new ArrayList<>();
        tags.add("tag1");
        tags.add("tag2");

        final ArgumentCaptor<List<Photo>> captor = ArgumentCaptor.forClass(List.class);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                synchronized (syncObject){
                    syncObject.notify();
                }
                return null;
            }
        }).when(callback).getPhotosCallback(captor.capture());


        repository.getPhotosByTags(tags, callback);


        synchronized (syncObject){
            syncObject.wait();
        }

        RecordedRequest request1 = server.takeRequest();
        Assert.assertEquals(SERVER_CALL1, request1.getPath());

        verify(callback, times(1)).getPhotosCallback(captor.capture());

        final List<Photo> results = captor.getValue();
        assertEquals(2, results.size());

        assertEquals("TITLE1", results.get(0).getTitle());
        assertEquals("DESCRIPTION1", results.get(0).getDescription());
        assertEquals("https://farm1.staticflickr.com/102/101_SECRET1.jpg", results.get(0).getUrl().toString());

        assertEquals("TITLE2", results.get(1).getTitle());
        assertEquals("", results.get(1).getDescription());
        assertEquals("https://farm2.staticflickr.com/202/201_SECRET2.jpg", results.get(1).getUrl().toString());
    }

}