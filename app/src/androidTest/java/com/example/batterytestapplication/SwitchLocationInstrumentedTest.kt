package com.example.batterytestapplication

import android.widget.Button
import android.widget.Switch
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class SwitchLocationInstrumentedTest {
    @get:Rule
    val rule = activityScenarioRule<MainActivity>()
    @Before
    fun setUp(){
        launchActivity<MainActivity>()
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.batterytestapplication", appContext.packageName)
    }

    @Test
    fun testEvent() {
        //Espresso.onView(ViewMatchers.withId(R.id.download_image)).perform()
        rule.scenario.onActivity {
            it.findViewById<Switch>(R.id.location_switch).performClick()
            assert(true)
        }
    }

    @After
    fun cleanup() {
        rule.scenario.close()
    }
}