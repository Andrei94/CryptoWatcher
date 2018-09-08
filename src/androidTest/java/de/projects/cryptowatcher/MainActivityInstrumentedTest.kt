package de.projects.cryptowatcher

import android.support.test.InstrumentationRegistry
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import de.projects.cryptowatcher.price.MainActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertNotNull

@RunWith(AndroidJUnit4::class)
class MainActivityInstrumentedTest {
	@Rule
	@JvmField
	val rule = ActivityTestRule<MainActivity>(MainActivity::class.java)

	@Test
	fun useAppContext() {
		val appContext = InstrumentationRegistry.getTargetContext()
		assertEquals("de.projects.cryptowatcher", appContext.packageName)
	}

	@Test
	fun elementsAreFilled() {
		val activity = rule.activity
		assertNotNull(activity.btc.text)
		assertNotNull(activity.eth.text)
		assertNotNull(activity.xrp.text)
	}
}
