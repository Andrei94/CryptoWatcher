package de.projects.cryptowatcher

import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import de.projects.cryptowatcher.CryptoCurrencies.*
import de.projects.cryptowatcher.CryptoIntents.ACTION_CRYPTO_DATA_LOADED
import de.projects.cryptowatcher.CryptoIntents.ACTION_CRYPTO_PERCENT_LOADED
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity(), ViewUpdater {
	private val myReceiver: CryptoBroadcastReceiver = CryptoBroadcastReceiver(this)

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
	}

	override fun onStart() {
		registerReceiver(myReceiver, intentFilter("$ACTION_CRYPTO_DATA_LOADED", "$ACTION_CRYPTO_PERCENT_LOADED"))
		startService(Intent(applicationContext, CryptoPriceService::class.java))
		super.onStart()
	}

	override fun onStop() {
		unregisterReceiver(myReceiver)
		super.onStop()
	}

	override fun updateActivity(intent: Intent?) {
		if (intent?.getStringExtra("$BTC PRICE") != null) {
			btc.text = intent.getStringExtra("$BTC PRICE")
		}
		if (intent?.getStringExtra("$BTC PERCENT") != null) {
			btcPercentChange.text = intent.getStringExtra("$BTC PERCENT")
			if (intent.getStringExtra("$BTC PERCENT") != null) {
				ethPercentChange.text = intent.getStringExtra("$ETH PERCENT")
				if(intent.getStringExtra("$BTC PERCENT").toDouble() < 0) {
					btc.setTextColor(getColor(R.color.red))
					btcPercentChange.setTextColor(getColor(R.color.red))
				} else {
					btc.setTextColor(getColor(R.color.green))
					btcPercentChange.setTextColor(getColor(R.color.green))
				}
			}
		}

		if (intent?.getStringExtra("$ETH PRICE") != null) {
			eth.text = intent.getStringExtra("$ETH PRICE")
		}
		if (intent?.getStringExtra("$ETH PERCENT") != null) {
			ethPercentChange.text = intent.getStringExtra("$ETH PERCENT")
			if(intent.getStringExtra("$ETH PERCENT").toDouble() < 0) {
				eth.setTextColor(getColor(R.color.red))
				ethPercentChange.setTextColor(getColor(R.color.red))
			} else {
				eth.setTextColor(getColor(R.color.green))
				ethPercentChange.setTextColor(getColor(R.color.green))
			}
		}

		if (intent?.getStringExtra("$XRP PRICE") != null) {
			xrp.text = intent.getStringExtra("$XRP PRICE")
		}
		if (intent?.getStringExtra("$XRP PERCENT") != null) {
			xrpPercentChange.text = intent.getStringExtra("$XRP PERCENT")
			if(intent.getStringExtra("$XRP PERCENT").toDouble() < 0) {
				xrp.setTextColor(getColor(R.color.red))
				xrpPercentChange.setTextColor(getColor(R.color.red))
			} else {
				xrp.setTextColor(getColor(R.color.green))
				xrpPercentChange.setTextColor(getColor(R.color.green))
			}
		}
	}
}

fun intentFilter(vararg actions: String): IntentFilter {
	val intents = IntentFilter()
	actions.forEach { intents.addAction(it) }
	return intents
}