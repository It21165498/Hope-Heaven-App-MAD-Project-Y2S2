package com.example.hopeheaven

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule


@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.hopeheaven", appContext.packageName)
    }

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)
    private lateinit var activityScenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        activityScenario = activityScenarioRule.scenario
        activityScenario.onActivity { activity ->
            // Do any setup you need for your activity here
        }
    }

    @Test
    fun testBackButtonNavigationToHomeFragment() {
        // Click the back button
        onView(withContentDescription(R.string.btn_student)).perform(click())

        // Check if the home fragment is displayed
        onView(withId(R.id.home)).check(matches(isDisplayed()))
    }

    @After
    fun tearDown() {
        activityScenario.close()
    }





//    val scenario = launchFragmentInContainer<Home>()

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test //when clicked home icon in the bottom nav bar,checking is Home fragment is visible or not
    fun clickHomeIcon_showsHomeFragment() {

        onView(withId(R.id.icon_home)).perform(click())
        onView(withId(R.id.home)).check(matches(isDisplayed()))
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