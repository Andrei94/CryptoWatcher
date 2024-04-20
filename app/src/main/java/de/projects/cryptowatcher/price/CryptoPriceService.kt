package de.projects.cryptowatcher.price

import android.app.Service
import android.content.Intent
import android.os.IBinder
import de.projects.cryptowatcher.currencies.CryptoCurrencies.*
import de.projects.cryptowatcher.intents.CryptoIntents
import okhttp3.Request

class CryptoPriceService : Service() {
	override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
		CryptoHandlerThread("https://production.api.coindesk.com/v2/tb/price/ticker?assets=$BTC", BTC, this).start()
		CryptoHandlerThread("https://production.api.coindesk.com/v2/tb/price/ticker?assets=$ETH", ETH, this).start()
		CryptoHandlerThread("https://production.api.coindesk.com/v2/tb/price/ticker?assets=$XRP", XRP, this).start()
		CryptoHandlerThread("https://production.api.coindesk.com/v2/tb/price/ticker?assets=$LTC", LTC, this).start()
		CryptoHandlerThread("https://production.api.coindesk.com/v2/tb/price/ticker?assets=$BCH", BCH, this).start()

		return START_NOT_STICKY
	}

	fun broadcastNetworkError() {
		val intent = Intent("${CryptoIntents.ACTION_CRYPTO_FAILED_TO_LOAD}")
		intent.putExtra("ERROR", "Failed to get data!")
		sendBroadcast(intent)
	}

	companion object Requests {
		fun createGetRequest(url: String): Request = Request.Builder().url(url).build()
	}

	override fun onBind(intent: Intent): IBinder? = null
}
