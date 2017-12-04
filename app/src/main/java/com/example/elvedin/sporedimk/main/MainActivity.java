package com.example.elvedin.sporedimk.main;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.elvedin.sporedimk.R;
import com.example.elvedin.sporedimk.model.Category;
import com.example.elvedin.sporedimk.ui.activity.BaseActivity;
import com.example.elvedin.sporedimk.ui.activity.SearchResultFragment;
import com.example.elvedin.sporedimk.ui.fragment.BaseFragment;
import com.example.elvedin.sporedimk.ui.fragment.CategoryFragment;
import com.example.elvedin.sporedimk.ui.fragment.FavoritesFragment;
import com.example.elvedin.sporedimk.ui.fragment.HomeFragment;
import com.example.elvedin.sporedimk.ui.fragment.MoreFragment;
import com.example.elvedin.sporedimk.ui.manager.network_manager.RemoteRepository;
import com.example.elvedin.sporedimk.utils.BottomNavigationViewHelper;
import com.example.elvedin.sporedimk.utils.Constants;
import com.example.elvedin.sporedimk.utils.UiHelper;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.disableShiftMode(navigation);

//        handleIntent(getIntent());

        if (savedInstanceState == null && !Intent.ACTION_SEARCH.equals(getIntent().getAction())) {
            initHomeFragment();
        }
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
            SearchView searchView =
                    (SearchView) menu.findItem(R.id.menu_search).getActionView();
            searchView.setSearchableInfo(
                    searchManager.getSearchableInfo(getComponentName()));

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
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        BaseFragment fragment = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.containerLayoutMain);
        if (fragment instanceof SearchResultFragment){
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
}
