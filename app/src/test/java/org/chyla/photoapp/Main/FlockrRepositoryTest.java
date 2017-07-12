package org.chyla.photoapp.Main;

import org.chyla.photoapp.BaseTest;
import org.chyla.photoapp.BuildConfig;
import org.chyla.photoapp.Main.Model.objects.Photo;
import org.chyla.photoapp.Main.Repository.CloudPhotosExplorer.Flickr.FlickrRepository;
import org.chyla.photoapp.Main.Repository.CloudPhotosExplorer.GetPhotosCallback;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class FlockrRepositoryTest extends BaseTest {

    private final static String SERVER_CALL1 = "/?method=flickr.photos.search&extras=description&api_key=API_KEY_1&tags=tag1,tag2";
    private final static String RESPONSE1 =
"<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n" +
"<rsp stat=\"ok\">\n" +
"<photos page=\"1\" pages=\"2719\" perpage=\"100\" total=\"271897\">\n" +
"\t<photo id=\"35870323665\" owner=\"137981303@N02\" secret=\"025b538074\" server=\"4284\" farm=\"5\" title=\"@wankity\" ispublic=\"1\" isfriend=\"0\" isfamily=\"0\" />\n" +
"\t<photo id=\"35030822204\" owner=\"142034446@N03\" secret=\"41bd1033d5\" server=\"4240\" farm=\"5\" title=\"dfgd5rt5y\" ispublic=\"1\" isfriend=\"0\" isfamily=\"0\" />\n" +
"</photos>\n"+
"</rsp>";

    private final static String API_KEY = "API_KEY_1";
    private String BASE_URL;
    private MockWebServer server;

    @Mock
    private GetPhotosCallback callback;

    FlickrRepository repository;

    @Override
    public void setUp() {
        super.setUp();
    }

    private void setupServer(String serverCall, String response) throws IOException {
        server = new MockWebServer();
        server.enqueue(new MockResponse().setBody(response));
        server.start();

        HttpUrl url = server.url(serverCall);
        BASE_URL = url.toString();
    }

    @After
    public void tearDown() {
        try {
            server.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetPhotosByTags() throws InterruptedException {
        try {
            setupServer(SERVER_CALL1, RESPONSE1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        repository = new FlickrRepository(API_KEY, BASE_URL);
        List<String> tags = new ArrayList<>();
        tags.add("tag1");
        tags.add("tag2");


        repository.getPhotosByTags(tags, callback);


        RecordedRequest request1 = server.takeRequest();
        assertEquals(SERVER_CALL1, request1.getPath());

        final ArgumentCaptor<List<Photo>> captor = ArgumentCaptor.forClass(List.class);
        verify(callback, times(1)).getPhotosCallback(captor.capture());

        final List<Photo> results = captor.getValue();
        assertEquals(2, results.size());
    }

}
