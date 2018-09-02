package de.projects.cryptowatcher

import android.content.Intent

interface ViewUpdater {
	fun updateActivity(intent : Intent?)
}