package com.example.elvedin.sporedimk;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.elvedin.sporedimk.ui.activity.MainActivity;
import com.example.elvedin.sporedimk.ui.fragment.HomeFragment;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by elvedin on 11/13/17.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class HomeFragmentTeset {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void SuggestedCategories_clickFirstElement() {
        onView(withId(R.id.rv_suggested_categories))
                .perform(
                        RecyclerViewActions.actionOnItemAtPosition(1, click()));
    }

    @Test
    public void MostFavoriteOffers_clickFirstElement() {
        onView(withId(R.id.rv_most_favorite_offers))
                .perform(
                        RecyclerViewActions.actionOnItemAtPosition(1, click()));
    }

    @Test
    public void MostFavoriteOffers_isDisplayed() {
        onView(withId(R.id.rv_most_favorite_offers))
                .check(matches(isDisplayed()));
    }

}
