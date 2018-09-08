package de.projects.cryptowatcher

import android.app.Service
import android.content.Intent
import android.os.HandlerThread
import android.os.IBinder
import com.beust.klaxon.Klaxon
import com.beust.klaxon.PathMatcher
import de.projects.cryptowatcher.CryptoCurrencies.*
import de.projects.cryptowatcher.CryptoIntents.ACTION_CRYPTO_DATA_LOADED
import de.projects.cryptowatcher.CryptoIntents.ACTION_CRYPTO_PERCENT_LOADED
import de.projects.cryptowatcher.FiatCurrencies.USD
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.InputStreamReader
import java.util.regex.Pattern

class CryptoPriceService : Service() {
	override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
		BTCHandler().start()
		ETHHandler().start()
		XRPHandler().start()

		return super.onStartCommand(intent, flags, startId)
	}

	inner class BTCHandler : HandlerThread("$BTC") {
		private val btcUrl = "https://api.coindesk.com/site/chartandheaderdata?currency=$BTC"

		override fun run() {
			val btcGetRequest = OkHttpClient.Builder().build().newCall(CryptoPriceService.createGetRequest(btcUrl))
			Klaxon().pathMatcher(CurrencyMatcher(BTC, USD)).pathMatcher(CurrencyPercentMatcher(BTC)).parseJsonObject(InputStreamReader(btcGetRequest!!.execute().body()!!.byteStream()))
		}
	}

	inner class ETHHandler : HandlerThread("$ETH") {
		private val ethUrl = "https://api.coindesk.com/site/chartandheaderdata?currency=$ETH"

		override fun run() {
			val ethGetRequest = OkHttpClient.Builder().build().newCall(createGetRequest(ethUrl))
			Klaxon().pathMatcher(CurrencyMatcher(ETH, USD)).pathMatcher(CurrencyPercentMatcher(ETH)).parseJsonObject(InputStreamReader(ethGetRequest!!.execute().body()!!.byteStream()))
		}
	}

	inner class XRPHandler : HandlerThread("$XRP") {
		private val xrpUrl = "https://api.coindesk.com/site/chartandheaderdata?currency=$XRP"

		override fun run() {
			val xrpGetRequest = OkHttpClient.Builder().build().newCall(createGetRequest(xrpUrl))
			Klaxon().pathMatcher(CurrencyMatcher(XRP, USD)).pathMatcher(CurrencyPercentMatcher(XRP)).parseJsonObject(InputStreamReader(xrpGetRequest!!.execute().body()!!.byteStream()))
		}
	}

	inner class CurrencyMatcher(private val crypto: CryptoCurrencies, private val currencyFiat: FiatCurrencies) : PathMatcher {
		override fun pathMatches(path: String): Boolean = Pattern.matches(".*$crypto.*header_data.*bpi.*$currencyFiat.*rate_float.*", path)

		override fun onMatch(path: String, value: Any) {
			val intent = Intent("$ACTION_CRYPTO_DATA_LOADED")
			intent.putExtra("$crypto PRICE", value.toString())
			sendBroadcast(intent)
		}
	}

	inner class CurrencyPercentMatcher(private val crypto: CryptoCurrencies) : PathMatcher {
		override fun pathMatches(path: String): Boolean = Pattern.matches(".*$crypto.*header_data.*changepc.*", path)

		override fun onMatch(path: String, value: Any) {
			val intent = Intent("$ACTION_CRYPTO_PERCENT_LOADED")
			intent.putExtra("$crypto PERCENT", value.toString())
			sendBroadcast(intent)
		}
	}

	companion object Requests {
		fun createGetRequest(url: String): Request = Request.Builder().url(url).build()
	}

	override fun onBind(intent: Intent): IBinder? = null
}