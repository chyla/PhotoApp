package org.chyla.photoapp;

import org.junit.Before;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

public class BaseTest {

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);
    }

}
