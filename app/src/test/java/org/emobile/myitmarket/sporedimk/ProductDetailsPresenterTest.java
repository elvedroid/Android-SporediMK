package org.emobile.myitmarket.sporedimk;

import org.emobile.myitmarket.model.Product;
import org.emobile.myitmarket.model.ProductOffers;
import org.emobile.myitmarket.base.products.productdetails.ProductDetailsContract;
import org.emobile.myitmarket.base.products.productdetails.ProductDetailsPresenter;
import org.emobile.myitmarket.base.scheduler.ImmediateSchedulerProvider;
import org.emobile.myitmarket.manager.network_manager.RemoteRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by elvedin on 11/29/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class ProductDetailsPresenterTest {

    @Mock
    private RemoteRepository repository;

    @Mock
    private ProductDetailsContract.View view;

    private ImmediateSchedulerProvider immediateSchedulerProvider;

    private ProductDetailsPresenter presenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        immediateSchedulerProvider = new ImmediateSchedulerProvider();
        presenter = new ProductDetailsPresenter(repository, view, immediateSchedulerProvider);
    }

    @Test
    public void loadOffersForSameProductError() throws Exception {
        Product p = new Product();
        when(repository.getOffersWithSameProduct(p))
                .thenReturn(Observable.error(new Throwable("Error")));
        presenter.loadOffersForSameProduct(p);

        InOrder inOrder = Mockito.inOrder(view);
        inOrder.verify(view).setProgressIndicator(true);
        inOrder.verify(view).showError("Error");
        inOrder.verify(view).setProgressIndicator(false);
        verify(view, never()).loadOffers(anyList());
    }

    @Test
    public void loadOffersForSameProductTest() throws Exception {
        Product p = new Product();
        when(repository.getOffersWithSameProduct(p))
                .thenReturn(Observable.<List<ProductOffers>>just(new ArrayList<ProductOffers>()));
        presenter.loadOffersForSameProduct(p);

        InOrder inOrder = Mockito.inOrder(view);
        inOrder.verify(view).setProgressIndicator(true);
        inOrder.verify(view).loadOffers(anyList());
        inOrder.verify(view).setProgressIndicator(false);
        verify(view, never()).showError(anyString());
    }
}