package de.projects.cryptowatcher.price

import android.content.Intent

interface ViewUpdater {
	fun updateActivity(intent : Intent)
}