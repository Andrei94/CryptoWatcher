package de.projects.cryptowatcher

import android.app.Activity
import android.os.Bundle
import android.view.View
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.button
import org.jetbrains.anko.setContentView

class MainActivity : Activity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		MainView().setContentView(this)
	}
}

class MainView : AnkoComponent<MainActivity> {
	override fun createView(ui: AnkoContext<MainActivity>): View = with(ui) {
		button {
			id = R.id.button
			text = context.getString(R.string.buttonMessage)
		}
	}
}
