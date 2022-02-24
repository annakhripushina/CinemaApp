package com.example.cinema_app

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.cinema_app.presentation.view.MainActivity
import com.example.cinema_app.presentation.view.cinemaList.CinemaListActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(AndroidJUnit4::class)
@LargeTest
class CinemaListActivityTest {
    @Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)
    @Test
    fun addFavouriteCinemaTest(){
        //Espresso.onView(ViewMatchers.withId(R.id.recyclerView))
        Espresso.onView(ViewMatchers.withId(R.id.nav_favourite))
            .perform(ViewActions.click())

//        Espresso.onView(ViewMatchers.withId(R.id.recyclerViewFavourite))
//            .check(ViewAssertion(matches(ViewMatchers.assertThat())))

        Espresso.onView(ViewMatchers.withId(R.id.recyclerView))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    1,
                    ViewActions.longClick()
                )
            )
            //.check(ViewAssertion())

    }
}