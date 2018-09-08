package de.projects.cryptowatcher

import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.Toast
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

	fun updatePrices(view: View) {
		startService(Intent(applicationContext, CryptoPriceService::class.java))
		Toast.makeText(applicationContext, "Prices updated", Toast.LENGTH_SHORT).show()
	}

	override fun updateActivity(intent: Intent) {
		PricePresenter(btc, btcPercentChange)
				.setCryptoDataWithColor(CryptoData(intent.getStringExtra("$BTC PRICE"), intent.getStringExtra("$BTC PERCENT"), getColor(R.color.green), getColor(R.color.red)))
		PricePresenter(eth, ethPercentChange)
				.setCryptoDataWithColor(CryptoData(intent.getStringExtra("$ETH PRICE"), intent.getStringExtra("$ETH PERCENT"), getColor(R.color.green), getColor(R.color.red)))
		PricePresenter(xrp, xrpPercentChange)
				.setCryptoDataWithColor(CryptoData(intent.getStringExtra("$XRP PRICE"), intent.getStringExtra("$XRP PERCENT"), getColor(R.color.green), getColor(R.color.red)))
	}
}

fun intentFilter(vararg actions: String): IntentFilter {
	val intents = IntentFilter()
	actions.forEach { intents.addAction(it) }
	return intents
}