package com.example.submission1

import android.view.KeyEvent
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {
    private val dummyString = "rizkyfirman00"
    private val dummyInt = 0

    @Before
    fun setup() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun test_search_view() {
        onView(withId(R.id.searchView))
            .perform(click())
        onView(withId(androidx.appcompat.R.id.search_src_text)).perform(typeText(dummyString))
        onView(withId(R.id.searchView)).perform(pressKey(KeyEvent.KEYCODE_ENTER))
        onView(withId(R.id.searchView)).check(matches(isDisplayed()))
    }

    fun test_recycler_view_to_detail_activity() {
        onView(withId(R.id.rvPeople)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ViewHolder>(
                dummyInt,
                click()
            )
        )
    }

    fun test_detail_activity() {
        onView(withId(R.id.rvPeople)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ViewHolder>(
                dummyInt,
                click()
            )
        )
        onView(withId(R.id.txtUsernameDetail)).check(matches(isDisplayed()))
        onView(withId(R.id.txtNameDetail)).check(matches(isDisplayed()))
        onView(withId(R.id.txtFollowersDetail)).check(matches(isDisplayed()))
        onView(withId(R.id.txtFollowingDetail)).check(matches(isDisplayed()))
        onView(withId(R.id.imgPhotoDetail)).check(matches(isDisplayed()))
    }

    fun test_following_tab() {
        onView(withId(R.id.rvPeople)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ViewHolder>(
                dummyInt,
                click()
            )
        )
        onView(withId(R.id.view_pager)).perform(swipeLeft())
        onView(withId(R.id.rvPeople)).check(matches(isDisplayed()))
    }

    fun test_favorite() {
        onView(withId(R.id.rvPeople)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ViewHolder>(
                dummyInt,
                click()
            )
        )
        onView(withId(R.id.btnFavorite)).perform(click())
        onView(isRoot()).perform(pressBack())
        onView(withId(R.id.favorite_menu)).perform(click())
        onView(withId(R.id.rvPeople)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ViewHolder>(
                dummyInt,
                click()
            )
        )
        onView(withId(R.id.btnFavorite)).perform(click())
        onView(isRoot()).perform(pressBack())
    }
}