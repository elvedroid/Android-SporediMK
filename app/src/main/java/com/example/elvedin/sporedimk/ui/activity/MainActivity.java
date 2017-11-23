package com.example.elvedin.sporedimk.ui.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.elvedin.sporedimk.R;
import com.example.elvedin.sporedimk.ui.activity.BaseActivity;
import com.example.elvedin.sporedimk.ui.fragment.BaseFragment;
import com.example.elvedin.sporedimk.ui.fragment.CategoryFragment;
import com.example.elvedin.sporedimk.ui.fragment.FavoritesFragment;
import com.example.elvedin.sporedimk.ui.fragment.HomeFragment;
import com.example.elvedin.sporedimk.ui.fragment.MoreFragment;
import com.example.elvedin.sporedimk.ui.fragment.ProductListFragment;
import com.example.elvedin.sporedimk.utils.BottomNavigationViewHelper;
import com.example.elvedin.sporedimk.utils.Constants;
import com.example.elvedin.sporedimk.utils.UiHelper;

public class MainActivity extends BaseActivity implements FragmentManager.OnBackStackChangedListener {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setShowMenuItemsChooseView(false);
                    UiHelper.clearBackstack(getSupportFragmentManager());
                    UiHelper.replaceFragment(getSupportFragmentManager(),
                            R.id.containerLayoutMain,
                            HomeFragment.newInstance(),
                            true, 0, 0);
                    return true;
                case R.id.navigation_categories:
                    setShowMenuItemsChooseView(true);
                    UiHelper.clearBackstack(getSupportFragmentManager());
                    UiHelper.replaceFragment(getSupportFragmentManager(),
                            R.id.containerLayoutMain,
                            CategoryFragment.newInstance(Constants.ROOT_CATEGORIES),
                            true, 0, 0);
                    return true;
                case R.id.navigation_favorite:
                    setShowMenuItemsChooseView(true);
                    UiHelper.clearBackstack(getSupportFragmentManager());
                    UiHelper.replaceFragment(getSupportFragmentManager(),
                            R.id.containerLayoutMain,
                            FavoritesFragment.newInstance(),
                            true, 0, 0);
                    return true;
                case R.id.navigation_more:
                    setShowMenuItemsChooseView(false);
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
        setHomeAsUp(false);
        bindView();
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
        }

        getSupportFragmentManager().addOnBackStackChangedListener(this);
        initHomeFragment();
    }

    private void initHomeFragment() {
        UiHelper.clearBackstack(getSupportFragmentManager());
        UiHelper.replaceFragment(getSupportFragmentManager(),
                R.id.containerLayoutMain,
                HomeFragment.newInstance(),
                true, 0, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();

//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//        searchView.setQueryRefinementEnabled(true);
//        searchView.setIconifiedByDefault(false);

        MenuItem cardsView = menu.findItem(R.id.menu_cards_view);
        MenuItem listView = menu.findItem(R.id.menu_list_view);
        MenuItem search = menu.findItem(R.id.menu_search);
        if (cardsView != null) {
            cardsView.setVisible(getShowMenuItemsChooseView());
        }
        if (listView != null) {
            listView.setVisible(getShowMenuItemsChooseView());
        }
        if(search != null){
            search.setVisible(!getHideSearchMenuItem());
        }

        return true;
    }

    private void bindView() {
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.disableShiftMode(navigation);
    }


    @Override
    public void onBackStackChanged() {
        BaseFragment fragment = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.containerLayoutMain);
        if (fragment != null) {
//            setTitle(fragment.getTitle());
            if (fragment instanceof CategoryFragment
                    || fragment instanceof ProductListFragment) {
                setShowMenuItemsChooseView(true);
                invalidateOptionsMenu();
            } else {
                setShowMenuItemsChooseView(false);
                invalidateOptionsMenu();

            }
        }
    }

}
