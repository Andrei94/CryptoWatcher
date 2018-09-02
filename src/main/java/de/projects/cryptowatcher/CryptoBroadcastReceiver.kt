package de.projects.cryptowatcher

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class CryptoBroadcastReceiver(private val viewUpdater: ViewUpdater) : BroadcastReceiver() {
	override fun onReceive(context: Context?, intent: Intent?) {
		viewUpdater.updateActivity(intent)
	}
}