package org.emobile.myitmarket.sporedimk;

import org.emobile.myitmarket.base.scheduler.ImmediateSchedulerProvider;
import org.emobile.myitmarket.manager.network_manager.RemoteRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/*
 * Created by elvedin on 11/30/17.
 */

public class MainPresenterTest {

    @Mock
    private RemoteRepository repository;

    @Mock
    private MainContract.View view;

    private ImmediateSchedulerProvider immediateSchedulerProvider;

    private MainPresenter presenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        immediateSchedulerProvider = new ImmediateSchedulerProvider();
        presenter = new MainPresenter(repository, view, immediateSchedulerProvider);
    }

    @Test
    public void categoryButtonPressed(){

    }
}
