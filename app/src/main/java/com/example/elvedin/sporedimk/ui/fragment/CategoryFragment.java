package com.example.elvedin.sporedimk.ui.fragment;


import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.elvedin.sporedimk.R;
import com.example.elvedin.sporedimk.adapter.CategoryAdapter;
import com.example.elvedin.sporedimk.managers.persistence.Persistence;
import com.example.elvedin.sporedimk.model.CatLang;
import com.example.elvedin.sporedimk.model.Category;
import com.example.elvedin.sporedimk.ui.manager.AppHolder;
import com.example.elvedin.sporedimk.utils.UiHelper;
import com.example.elvedin.sporedimk.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.elvedin.sporedimk.utils.Constants.ROOT_CATEGORIES;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends BaseFragment implements CategoryAdapter.CategoryItemInterface {

    public final static String TAG = "CategoryFragment";
    public final static String FROM_CATEGORY = "FROM_CATEGORY";
    private static final String IS_LINEAR_LAYOUT_CATEGORIES = "IS_LINEAR_LAYOUT_CATEGORIES";

    View rootView;
    RecyclerView recyclerView;
    List<Category> categories;
    List<Category> subCategoriesList;
    CategoryAdapter adapter;
    RecyclerView.LayoutManager mGridLayoutManager, mLinearLayoutManager;
    String fromCategory = ROOT_CATEGORIES;
    Boolean isLinearLayout = false;

    public static CategoryFragment newInstance(String fromCategory) {
        Bundle args = new Bundle();
        args.putString(FROM_CATEGORY, fromCategory);
        CategoryFragment fragment = new CategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public String getTitle() {
        return "Category";
    }

    @Override
    protected void initViews(View contentView) {
        rootView = contentView;
        if (getArguments() != null) {
            fromCategory = getArguments().getString(FROM_CATEGORY);
        }
        if (ROOT_CATEGORIES.equals(fromCategory)) {
            getRootCategories(fromCategory);
        } else {
            getSubcategories(fromCategory);
        }
    }

    private void getRootCategories(String fromCategory) {
        Call<List<Category>> call = AppHolder.getInstance().getClientInterface().getRootCategories("en");
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful()) {
                    subCategoriesList = response.body();
                }
                initUI();
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_category;
    }

    private void initUI() {
        isLinearLayout = Persistence.getBoolean(IS_LINEAR_LAYOUT_CATEGORIES, false);
        categories = new ArrayList<>();
        if (subCategoriesList != null && subCategoriesList.size() > 0) {
            categories.addAll(subCategoriesList);
        }

        recyclerView = rootView.findViewById(R.id.rv_categories);

        mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(isLinearLayout ? mLinearLayoutManager : mGridLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new CategoryAdapter(getActivity(), categories, isLinearLayout ? CategoryAdapter.ITEM_TYPE_LIST : CategoryAdapter.ITEM_TYPE_GRID);
        adapter.setmClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    private void getSubcategories(String cat) {
        Call<List<Category>> call = AppHolder.getInstance().getClientInterface().getCategoriesFromCategory(new CatLang(cat, "en"));
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful()) {
                    subCategoriesList = response.body();
                }
                initUI();
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCategoryItemClicked(View view, int adapterPosition) {
        Category category = adapter.getCategories().get(adapterPosition);
        if (Utils.categoriesContainsCategory(category)) {
            UiHelper.replaceFragment(getActivity().getSupportFragmentManager(),
                    R.id.containerLayoutMain,
                    ProductListFragment.newInstance(category.getName()),
                    true, 0, 0);
        } else {
            UiHelper.replaceFragment(getActivity().getSupportFragmentManager(),
                    R.id.containerLayoutMain,
                    CategoryFragment.newInstance(category.getName()),
                    true, 0, 0);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_cards_view:
                adapter.setItemType(CategoryAdapter.ITEM_TYPE_GRID);
                recyclerView.setLayoutManager(mGridLayoutManager);
                adapter.notifyDataSetChanged();
                isLinearLayout = false;
                Persistence.setBoolean(IS_LINEAR_LAYOUT_CATEGORIES, isLinearLayout);
                break;
            case R.id.menu_list_view:
                adapter.setItemType(CategoryAdapter.ITEM_TYPE_LIST);
                recyclerView.setLayoutManager(mLinearLayoutManager);
                adapter.notifyDataSetChanged();
                isLinearLayout = true;
                Persistence.setBoolean(IS_LINEAR_LAYOUT_CATEGORIES, isLinearLayout);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
