package de.projects.cryptowatcher.price

import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import de.projects.cryptowatcher.R
import de.projects.cryptowatcher.R.string.loading
import de.projects.cryptowatcher.currencies.CryptoCurrencies.*
import de.projects.cryptowatcher.intents.CryptoIntents.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity(), ViewUpdater {
	private val myReceiver: CryptoBroadcastReceiver = CryptoBroadcastReceiver(this)
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		MobileAds.initialize(this, "ca-app-pub-2557366403157516~6699126486")
		adView.loadAd(AdRequest.Builder().build())
		setLoadingText()
	}

	override fun onStart() {
		registerReceiver(myReceiver, intentFilter("$ACTION_CRYPTO_DATA_LOADED", "$ACTION_CRYPTO_PERCENT_LOADED", "$ACTION_CRYPTO_FAILED_TO_LOAD"))
		startService(Intent(this, CryptoPriceService::class.java))
		super.onStart()
	}

	override fun onStop() {
		unregisterReceiver(myReceiver)
		super.onStop()
	}

	fun updatePrices(view: View) {
		startService(Intent(this, CryptoPriceService::class.java))
		setLoadingText()
	}

	private fun setLoadingText() {
		btc.text = getString(loading)
		eth.text = getString(loading)
		xrp.text = getString(loading)
		ltc.text = getString(loading)
		bch.text = getString(loading)
	}

	override fun updateActivity(intent: Intent) {
		intent.getStringExtra("ERROR")?.let {
			btc.text = intent.getStringExtra("ERROR")
			eth.text = intent.getStringExtra("ERROR")
			xrp.text = intent.getStringExtra("ERROR")
			ltc.text = intent.getStringExtra("ERROR")
			bch.text = intent.getStringExtra("ERROR")
			return
		}
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