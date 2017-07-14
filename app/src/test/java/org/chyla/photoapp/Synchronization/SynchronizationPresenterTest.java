package org.chyla.photoapp.Synchronization;

import org.chyla.photoapp.BaseTest;
import org.chyla.photoapp.Synchronization.Model.SynchronizeDataInteractor;
import org.chyla.photoapp.Synchronization.Model.detail.SynchronizationFinishedEvent;
import org.chyla.photoapp.Synchronization.Presenter.SynchronizationPresenter;
import org.chyla.photoapp.Synchronization.Presenter.SynchronizationPresenterImpl;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class SynchronizationPresenterTest extends BaseTest {

    @Mock
    private View view;

    @Mock
    private SynchronizeDataInteractor synchronizeDataInteractor;

    private SynchronizationPresenter presenter;

    @Override
    public void setUp() {
        super.setUp();

        presenter = new SynchronizationPresenterImpl(synchronizeDataInteractor, view);
    }

    @Test
    public void testSynchronize() {
        presenter.synchronize();

        verify(synchronizeDataInteractor, times(1)).synchronizeData();
    }

    @Test
    public void testOnSynchronizeFinished() {
        final SynchronizationFinishedEvent event = new SynchronizationFinishedEvent();

        presenter.onSynchronizeFinished(event);

        verify(view, times(1)).startMainActivity();
    }

}
