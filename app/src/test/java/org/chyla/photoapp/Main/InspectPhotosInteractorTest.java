package org.chyla.photoapp.Main;

import org.chyla.photoapp.BaseTest;
import org.chyla.photoapp.BuildConfig;
import org.chyla.photoapp.Main.Model.InspectPhotosInteractor;
import org.chyla.photoapp.Main.Model.InspectPhotosInteractorImpl;
import org.chyla.photoapp.Main.Repository.CloudPhotosExplorer.CloudPhotosExplorerRepository;
import org.chyla.photoapp.Main.Repository.CloudPhotosExplorer.GetPhotosCallback;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.LinkedList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class InspectPhotosInteractorTest extends BaseTest {

    @Mock
    CloudPhotosExplorerRepository photosExplorerRepository;

    InspectPhotosInteractor interactor;

    @Override
    public void setUp() {
        super.setUp();

        interactor = new InspectPhotosInteractorImpl(photosExplorerRepository);
    }

    @After
    public void tearDown() {
        verifyNoMoreInteractions(photosExplorerRepository);
    }

    @Test
    public void testConstructor() {
        // empty body
    }

    @Test
    public void testInspectPhotos() {
        final List<String> tags = new LinkedList<>();
        tags.add("tag1");

        interactor.inspectPhotos(tags);


        final ArgumentCaptor<List<String>> tagsCaptor = ArgumentCaptor.forClass(List.class);
        final ArgumentCaptor<GetPhotosCallback> callbackCaptor = ArgumentCaptor.forClass(GetPhotosCallback.class);
        verify(photosExplorerRepository, times(1)).getPhotosByTags(tagsCaptor.capture(), callbackCaptor.capture());
        assertEquals(1, tagsCaptor.getValue().size());
        assertEquals("tag1", tagsCaptor.getValue().get(0));
    }

}
