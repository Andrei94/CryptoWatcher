package de.projects.cryptowatcher.price

import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import de.projects.cryptowatcher.R
import de.projects.cryptowatcher.currencies.CryptoCurrencies.*
import de.projects.cryptowatcher.intents.CryptoIntents.ACTION_CRYPTO_DATA_LOADED
import de.projects.cryptowatcher.intents.CryptoIntents.ACTION_CRYPTO_PERCENT_LOADED
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity(), ViewUpdater {
	private val myReceiver: CryptoBroadcastReceiver = CryptoBroadcastReceiver(this)
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		MobileAds.initialize(this, "ca-app-pub-2557366403157516~6699126486")
		adView.loadAd(AdRequest.Builder().build())
	}

	override fun onStart() {
		registerReceiver(myReceiver, intentFilter("$ACTION_CRYPTO_DATA_LOADED", "$ACTION_CRYPTO_PERCENT_LOADED"))
		startService(Intent(this, CryptoPriceService::class.java))
		super.onStart()
	}

	override fun onStop() {
		unregisterReceiver(myReceiver)
		super.onStop()
	}

	fun updatePrices(view: View) {
		startService(Intent(this, CryptoPriceService::class.java))
		Toast.makeText(this, "Prices updated", Toast.LENGTH_SHORT).show()
	}

	override fun updateActivity(intent: Intent) {
		PricePresenter(btc, btcPercentChange)
				.setCryptoDataWithColor(CryptoData(intent.getStringExtra("$BTC PRICE"), intent.getStringExtra("$BTC PERCENT"), getColor(R.color.green), getColor(R.color.red)))
		PricePresenter(eth, ethPercentChange)
				.setCryptoDataWithColor(CryptoData(intent.getStringExtra("$ETH PRICE"), intent.getStringExtra("$ETH PERCENT"), getColor(R.color.green), getColor(R.color.red)))
		PricePresenter(xrp, xrpPercentChange)
				.setCryptoDataWithColor(CryptoData(intent.getStringExtra("$XRP PRICE"), intent.getStringExtra("$XRP PERCENT"), getColor(R.color.green), getColor(R.color.red)))
		PricePresenter(ltc, ltcPercentChange)
				.setCryptoDataWithColor(CryptoData(intent.getStringExtra("$LTC PRICE"), intent.getStringExtra("$LTC PERCENT"), getColor(R.color.green), getColor(R.color.red)))
		PricePresenter(bch, bchPercentChange)
				.setCryptoDataWithColor(CryptoData(intent.getStringExtra("$BCH PRICE"), intent.getStringExtra("$BCH PERCENT"), getColor(R.color.green), getColor(R.color.red)))
	}

	private fun intentFilter(vararg actions: String): IntentFilter {
		val intents = IntentFilter()
		actions.forEach { intents.addAction(it) }
		return intents
	}
}