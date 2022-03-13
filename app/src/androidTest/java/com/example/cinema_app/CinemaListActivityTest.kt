package com.example.cinema_app

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.cinema_app.presentation.view.MainActivity
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class CinemaListActivityTest {
    @Rule
    @JvmField
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun addFavouriteCinemaTest() {
        val action = RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10)
        val context = ApplicationProvider.getApplicationContext<Context>()

        Espresso.onView(withId(R.id.recyclerView)).perform(action)
        Espresso.onView(listMatcher().atPosition(10))
            .check(matches(isDisplayed()))
        var title = getText(Espresso.onView(listMatcher().atPositionTarget(10, R.id.titleView)))

        Espresso.onView(listMatcher().atPosition(10)).perform(ViewActions.longClick())
        Espresso.onView(withText(context.getString(R.string.favouriteAddSnackbar)))
            .check(matches(isDisplayed()))

        Espresso.onView(withId(R.id.nav_favourite)).perform(ViewActions.click())
        Espresso.onView(withText(title))
            .check(matches(isDisplayed()))

    }

    private fun listMatcher(): RecyclerViewMatcher {
        return RecyclerViewMatcher(R.id.recyclerView)
    }

    private fun getText(matcher: ViewInteraction): String {
        var text = String()
        matcher.perform(object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isAssignableFrom(TextView::class.java)
            }

            override fun getDescription(): String {
                return "Text of the view"
            }

            override fun perform(uiController: UiController, view: View) {
                val tv = view as TextView
                text = tv.text.toString()
            }
        })

        return text
    }
}