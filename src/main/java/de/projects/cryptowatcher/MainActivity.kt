package de.projects.cryptowatcher

import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity(), ViewUpdater {
	private val myReceiver: CryptoBroadcastReceiver = CryptoBroadcastReceiver(this)

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
	}

	override fun onStart() {
		registerReceiver(myReceiver, intentFilter("MY_ACTION"))
		startService(Intent(applicationContext, CryptoPriceService::class.java))
		super.onStart()
	}

	override fun onStop() {
		unregisterReceiver(myReceiver)
		super.onStop()
	}

	override fun updateActivity(intent: Intent?) {
		if (intent?.getStringExtra("BTC PRICE") != null) {
			btc.text = intent.getStringExtra("BTC PRICE")
		}
		if (intent?.getStringExtra("ETH PRICE") != null) {
			eth.text = intent.getStringExtra("ETH PRICE")
		}
		if (intent?.getStringExtra("XRP PRICE") != null) {
			xrp.text = intent.getStringExtra("XRP PRICE")
		}
	}
}

fun intentFilter(action: String): IntentFilter {
	val filter = IntentFilter()
	filter.addAction(action)
	return filter
}