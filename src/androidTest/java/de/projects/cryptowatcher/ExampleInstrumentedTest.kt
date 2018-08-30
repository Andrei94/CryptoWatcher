package de.projects.cryptowatcher

import android.support.test.InstrumentationRegistry
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.widget.Button
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertTrue

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
	@Rule @JvmField
	val rule = ActivityTestRule<MainActivity>(MainActivity::class.java)
	@Test
	fun useAppContext() {
		// Context of the app under test.
		val appContext = InstrumentationRegistry.getTargetContext()
		assertEquals("de.projects.cryptowatcher", appContext.packageName)
	}

	@Test
	fun hasButton() {
		val activity = rule.activity
		assertTrue(activity.findViewById<Button>(R.id.button).isEnabled)
		assertEquals("Press me", activity.findViewById<Button>(R.id.button).text)
	}
}
