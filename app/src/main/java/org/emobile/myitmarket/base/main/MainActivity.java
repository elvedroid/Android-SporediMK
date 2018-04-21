package org.emobile.myitmarket.base.main;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.graphics.Movie;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView;
import com.jakewharton.rxbinding2.support.v7.widget.SearchViewQueryTextEvent;

import org.emobile.myitmarket.R;

import org.emobile.myitmarket.MainApplication;

import org.emobile.myitmarket.base.di.component.DaggerProductComponent;
import org.emobile.myitmarket.base.di.component.ProductComponent;
import org.emobile.myitmarket.base.di.module.ProductModule;
import org.emobile.myitmarket.base.di.HasComponent;
import org.emobile.myitmarket.base.BaseActivity;
import org.emobile.myitmarket.base.products.productdetails.ProductDetailsFragment;
import org.emobile.myitmarket.manager.AppHolder;
import org.emobile.myitmarket.manager.log.LogLevel;
import org.emobile.myitmarket.model.Offer;
import org.emobile.myitmarket.ui.fragment.SearchResultFragment;
import org.emobile.myitmarket.base.BaseFragment;
import org.emobile.myitmarket.ui.fragment.CategoryFragment;
import org.emobile.myitmarket.ui.fragment.FavoritesFragment;
import org.emobile.myitmarket.ui.fragment.HomeFragment;
import org.emobile.myitmarket.ui.fragment.MoreFragment;
import org.emobile.myitmarket.utils.BottomNavigationViewHelper;
import org.emobile.myitmarket.utils.Constants;
import org.emobile.myitmarket.utils.Filter;
import org.emobile.myitmarket.utils.UiHelper;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.observers.DisposableObserver;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.emobile.myitmarket.base.main.SuggestionsAdapter.PRODUCT_CURSOR;

public class MainActivity extends BaseActivity implements HasComponent<ProductComponent> {

    private static final String TAG = "MainActivity";
    private static final int SEARCH_QUERY_THRESHOLD = 3;
    private static final String[] sAutocompleteColNames = new String[]{
            BaseColumns._ID,                         // necessary for adapter
            PRODUCT_CURSOR      // the full search term
    };

    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    SuggestionsAdapter suggestionsAdapter;
    SearchView searchView;
    private ProductComponent productComponent;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                UiHelper.clearBackstack(getSupportFragmentManager());
                UiHelper.replaceFragment(getSupportFragmentManager(),
                        R.id.containerLayoutMain,
                        HomeFragment.newInstance(),
                        false, 0, 0);
                return true;
            case R.id.navigation_categories:
                UiHelper.clearBackstack(getSupportFragmentManager());
                UiHelper.replaceFragment(getSupportFragmentManager(),
                        R.id.containerLayoutMain,
                        CategoryFragment.newInstance(Constants.ROOT_CATEGORIES),
                        CategoryFragment.TAG,
                        true, 0, 0);
                return true;
            case R.id.navigation_favorite:
                UiHelper.clearBackstack(getSupportFragmentManager());
                UiHelper.replaceFragment(getSupportFragmentManager(),
                        R.id.containerLayoutMain,
                        FavoritesFragment.newInstance(),
                        true, 0, 0);
                return true;
            case R.id.navigation_more:
                UiHelper.clearBackstack(getSupportFragmentManager());
                UiHelper.replaceFragment(getSupportFragmentManager(),
                        R.id.containerLayoutMain,
                        MoreFragment.newInstance(),
                        true, 0, 0);
                return true;
        }
        return false;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        this.initializeInjector();
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.disableShiftMode(navigation);

//        handleIntent(getIntent());

        if (savedInstanceState == null && !Intent.ACTION_SEARCH.equals(getIntent().getAction())) {
            initHomeFragment();
        }
    }

    private void initializeInjector() {
        productComponent = DaggerProductComponent.builder()
                .appComponent(((MainApplication) getApplication()).getAppComponent())
                .productModule(new ProductModule())
                .build();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
//        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            final String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
            UiHelper.clearBackstack(getSupportFragmentManager());
            UiHelper.replaceFragment(getSupportFragmentManager(),
                    R.id.containerLayoutMain,
                    SearchResultFragment.newInstance(query),
                    false, 0, 0);
        }
    }

    private void initHomeFragment() {
        UiHelper.clearBackstack(getSupportFragmentManager());
        UiHelper.replaceFragment(getSupportFragmentManager(),
                R.id.containerLayoutMain,
                HomeFragment.newInstance(),
                false, 0, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        MenuItem cardsView = menu.findItem(R.id.menu_cards_view);
        MenuItem listView = menu.findItem(R.id.menu_list_view);
        MenuItem search = menu.findItem(R.id.menu_search);
        if (cardsView != null) {
            cardsView.setVisible(getShowMenuItemsChooseView());
        }
        if (listView != null) {
            listView.setVisible(getShowMenuItemsChooseView());
        }
        if (search != null) {
            search.setVisible(getShowSearchMenuItem());
            // Associate searchable configuration with the SearchView
            SearchManager searchManager =
                    (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            searchView =
                    (SearchView) menu.findItem(R.id.menu_search).getActionView();
            searchView.setSearchableInfo(
                    searchManager.getSearchableInfo(getComponentName()));

            suggestionsAdapter = new SuggestionsAdapter(this, null, false);

            searchView.setSuggestionsAdapter(suggestionsAdapter);
            searchView.setOnSuggestionListener(onSuggestionListener);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    UiHelper.replaceFragment(getSupportFragmentManager(),
                            R.id.containerLayoutMain,
                            SearchResultFragment.newInstance(query),
                            true, 0, 0);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });

            ConnectableObservable<SearchViewQueryTextEvent> searchObs = RxSearchView.queryTextChangeEvents(searchView).publish();
            searchObs.skip(1)
                    .throttleLast(100, TimeUnit.MILLISECONDS)
                    .debounce(300, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableObserver<SearchViewQueryTextEvent>() {
                        @Override
                        public void onNext(SearchViewQueryTextEvent searchViewQueryTextEvent) {
                            String query = searchViewQueryTextEvent.queryText().toString();
                            if (query.length() < SEARCH_QUERY_THRESHOLD) {
                                searchView.getSuggestionsAdapter().changeCursor(null);
                                return;
                            }

                            if (searchViewQueryTextEvent.isSubmitted()) {
                                UiHelper.replaceFragment(getSupportFragmentManager(),
                                        R.id.containerLayoutMain,
                                        SearchResultFragment.newInstance(query),
                                        true, 0, 0);
                            } else {
                                getSuggestions(query);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
            Disposable searchSub = searchObs.connect();
        }
        return true;
    }

    public void getSuggestions(String query) {
        Call<List<Offer>> call = AppHolder.getInstance().getClientInterface().getFilteredProducts(query != null ? query : "", Filter.language, 0, 10);
        call.enqueue(new Callback<List<Offer>>() {
            @Override
            public void onResponse(@NonNull Call<List<Offer>> call, @NonNull Response<List<Offer>> response) {
                if (response != null) {
                    AppHolder.log(LogLevel.INFO, TAG, "OnResponseNOTNullBody");
                    MatrixCursor cursor = new MatrixCursor(sAutocompleteColNames);
                    for (Offer movie : response.body()) {
                        cursor.addRow(new Object[]{movie.getId(), new Gson().toJson(movie)});
                    }
                    if (cursor.getCount() == 0) {
                        cursor.addRow(new Object[]{-1, null});
                    }
                    searchView.getSuggestionsAdapter().changeCursor(cursor);
                } else {
                    AppHolder.logWithToast(MainActivity.this, LogLevel.ERROR, TAG, "OnResponseNullBody");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Offer>> call, @NonNull Throwable t) {
                AppHolder.logWithToast(MainActivity.this, LogLevel.ERROR, TAG, t.getMessage());

            }
        });
    }

    @Override
    public void onBackPressed() {
        BaseFragment fragment = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.containerLayoutMain);
        if (fragment instanceof SearchResultFragment) {
            navigation.setSelectedItemId(R.id.navigation_home);
            return;
        }

        super.onBackPressed();

        if (fragment != null) {
            if (fragment instanceof HomeFragment) {
                navigation.setSelectedItemId(R.id.navigation_home);
            }
        }
    }

    private SearchView.OnSuggestionListener onSuggestionListener = new SearchView.OnSuggestionListener() {
        @Override
        public boolean onSuggestionSelect(int position) {
            Cursor cursor = (Cursor) searchView.getSuggestionsAdapter().getItem(position);
            String movieStr = cursor.getString(cursor.getColumnIndex(PRODUCT_CURSOR));
            cursor.close();
            Log.d(TAG, movieStr);
            Offer offer = new Gson().fromJson(movieStr, Offer.class);
            ProductDetailsFragment fragment = ProductDetailsFragment.newInstance(offer);
            UiHelper.addFragment(getSupportFragmentManager(),
                    R.id.containerLayoutMain,
                    fragment,
                    ProductDetailsFragment.TAG,
                    true, 0, 0);
            return true;
        }

        @Override
        public boolean onSuggestionClick(int position) {
            return onSuggestionSelect(position);
        }
    };

    @Override
    public ProductComponent getComponent() {
        return productComponent;
    }
}
