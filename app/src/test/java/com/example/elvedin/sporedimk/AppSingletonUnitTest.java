package com.example.elvedin.sporedimk;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by elvedin on 11/13/17.
 */

public class AppSingletonUnitTest {
    @Test
    public void checkDefaultCategoriesSize(){
        assertEquals(AppSingleton.getInstance().getDefaultSuggestedCategories().size(), 4);
    }

}
