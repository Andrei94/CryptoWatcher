package de.projects.cryptowatcher.price

import android.app.Service
import android.content.Intent
import android.os.IBinder
import de.projects.cryptowatcher.currencies.CryptoCurrencies.*
import de.projects.cryptowatcher.currencies.FiatCurrencies.USD
import okhttp3.Request

class CryptoPriceService : Service() {
	override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
		CryptoHandlerThread("https://api.coindesk.com/site/chartandheaderdata?currency=$BTC", BTC, USD, this).start()
		CryptoHandlerThread("https://api.coindesk.com/site/chartandheaderdata?currency=$ETH", ETH, USD, this).start()
		CryptoHandlerThread("https://api.coindesk.com/site/chartandheaderdata?currency=$XRP", XRP, USD, this).start()
		CryptoHandlerThread("https://api.coindesk.com/site/chartandheaderdata?currency=$LTC", LTC, USD, this).start()
		CryptoHandlerThread("https://api.coindesk.com/site/chartandheaderdata?currency=$BCH", BCH, USD, this).start()

		return super.onStartCommand(intent, flags, startId)
	}

	companion object Requests {
		fun createGetRequest(url: String): Request = Request.Builder().url(url).build()
	}

	override fun onBind(intent: Intent): IBinder? = null
}