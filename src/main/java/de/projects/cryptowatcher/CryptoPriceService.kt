package de.projects.cryptowatcher

import android.app.Service
import android.content.Intent
import android.os.HandlerThread
import android.os.IBinder
import com.beust.klaxon.Klaxon
import com.beust.klaxon.PathMatcher
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

	inner class BTCHandler : HandlerThread("btc") {
		private val btcUrl = "https://api.coindesk.com/site/chartandheaderdata?currency=${CryptoCurrencies.BTC}"

		override fun run() {
			val btcGetRequest = OkHttpClient.Builder().build().newCall(CryptoPriceService.createGetRequest(btcUrl))
			Klaxon().pathMatcher(BTCMatcher("USD")).parseJsonObject(InputStreamReader(btcGetRequest!!.execute().body()!!.byteStream()))
		}
	}

	inner class ETHHandler : HandlerThread("eth") {
		private val ethUrl = "https://api.coindesk.com/site/chartandheaderdata?currency=${CryptoCurrencies.ETH}"

		override fun run() {
			val ethGetRequest = OkHttpClient.Builder().build().newCall(createGetRequest(ethUrl))
			Klaxon().pathMatcher(ETHMatcher("USD")).parseJsonObject(InputStreamReader(ethGetRequest!!.execute().body()!!.byteStream()))
		}
	}

	inner class XRPHandler : HandlerThread("xrp") {
		private val xrpUrl = "https://api.coindesk.com/site/chartandheaderdata?currency=${CryptoCurrencies.XRP}"

		override fun run() {
			val xrpGetRequest = OkHttpClient.Builder().build().newCall(createGetRequest(xrpUrl))
			Klaxon().pathMatcher(XRPMatcher("USD")).parseJsonObject(InputStreamReader(xrpGetRequest!!.execute().body()!!.byteStream()))
		}
	}

	open inner class CurrencyMatcher(private val crypto: CryptoCurrencies, private val currencyFiat: String) : PathMatcher {
		override fun onMatch(path: String, value: Any) {
			val intent = Intent("MY_ACTION")
			intent.putExtra("$crypto PRICE", value.toString())
			sendBroadcast(intent)
		}

		override fun pathMatches(path: String): Boolean = Pattern.matches(".*$crypto.*header_data.*bpi.*$currencyFiat.*rate_float.*", path)
	}

	inner class BTCMatcher(currencyFiat: String) : CurrencyMatcher(CryptoCurrencies.BTC, currencyFiat)

	inner class ETHMatcher(currencyFiat: String) : CurrencyMatcher(CryptoCurrencies.ETH, currencyFiat)

	inner class XRPMatcher(currencyFiat: String) : CurrencyMatcher(CryptoCurrencies.XRP, currencyFiat)

	companion object Requests {
		fun createGetRequest(url: String): Request = Request.Builder().url(url).build()
	}

	override fun onBind(intent: Intent): IBinder? = null
}