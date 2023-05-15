package com.example.hopeheaven

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.lifecycle.Lifecycle.State.*



import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {


    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.hopeheaven", appContext.packageName)
    }


    val scenario = launchFragmentInContainer<Home>()

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test //when clicked home icon in the bottom nav bar,checking is Home fragment is visible or not
    fun clickHomeIcon_showsHomeFragment() {

        onView(withId(R.id.icon_home)).perform(click())
        onView(withId(R.id.fragment_home)).check(matches(isDisplayed()))
        val expectedFragment = Home::class.java.name

        val currentFragment = activityRule.scenario.onActivity { activity ->
            activity.supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        }
        val actualFragment = currentFragment?.javaClass?.name
        assertEquals(expectedFragment, actualFragment)
    }


    @Test
    fun clickEditProfileButton_opensEditProfileActivity() {
        activityRule.scenario.onActivity { activity ->
            val fragment = StudentProfile()
            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit()
        }

        onView(withId(R.id.tvEditProfile)).perform(click())
        onView(withId(R.id.editProfile)).check(matches(isDisplayed()))
    }
}