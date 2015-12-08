package com.calendarapp.kaylagallatin.calendar;

/**
 * Created by Sriven on 12/3/2015.
 */
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.assertion.PositionAssertions.isLeftOf;
import static android.support.test.espresso.assertion.PositionAssertions.isRightOf;
import static android.support.test.espresso.assertion.PositionAssertions.isTopAlignedWith;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Sriven on 12/5/2015.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class deleteCatagoryTest {

    //Instantiate ActivityTestRule - > with delete_category
    @Rule
    public ActivityTestRule<delete_category> mActivityRule = new ActivityTestRule(delete_category.class);

    //TEST if "Delete Category" is displayed
    @Test
    public void testDeleteCategory() {
        onView(withText("Delete Category"))
                .check(matches(isDisplayed()));
    }

    //TEST if "Category Name to Delete: " is displayed
    @Test
    public void testCategoryNameToDelete() {
        onView(withText("Category Name to Delete: ")) // space needed to work
                .check(matches(isDisplayed()));
    }

    //TEST PrevMonth Button
    @Test
    public void testPrevMonthButton2() {
        onView(withId(R.id.saveDeleteCategoryButton))
                .perform(click());
    }

    //TEST To FAIL
    @Test
    public void testFailureByID() {
        onView(withId(23545))
                .check(doesNotExist());
    }
}

