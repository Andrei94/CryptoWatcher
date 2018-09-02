package de.projects.cryptowatcher

import android.app.Service
import android.content.Intent
import android.os.HandlerThread
import android.os.IBinder
import com.beust.klaxon.Json
import com.beust.klaxon.Klaxon
import okhttp3.OkHttpClient
import okhttp3.Request


class CryptoPriceService : Service() {
	inner class ServiceHandler : HandlerThread("crypto") {
		private val date = "2018-08-31"
		private val url = "https://api.coindesk.com/v1/bpi/historical/close.json?start=$date&end=$date"
		private val url2 = "https://api.coindesk.com/site/chartandheaderdata?currency=eth"

		override fun run() {
			val close = OkHttpClient.Builder().build().newCall(createGetRequest(url))
			val jsonResponse = Klaxon().parse<BitcoinClosePrice>(close.execute().body()!!.byteStream())

			val close2 = OkHttpClient.Builder().build().newCall(createGetRequest(url2))
			val jsonResponse2 = Klaxon().parse<EthereumClosePrice>(close2.execute().body()!!.byteStream())

			val intent = Intent()
			intent.action = "MY_ACTION"

			intent.putExtra("XBT PRICE", jsonResponse!!.priceOn(date)!!.toString())
			intent.putExtra("ETH PRICE", jsonResponse2!!.eth["header_data"]!!["bpi"]!!["USD"]!!["rate_float"]!!.toString())

			sendBroadcast(intent)
		}
	}

	class BitcoinClosePrice(val bpi : Map<String, Double>) {
		fun priceOn(date: String): Double? {
			return bpi[date]
		}
	}

	class EthereumClosePrice(@Json(name="ETH")val eth: Map<String, Map<String, Map<String, Map<String, Double>>>>)

	companion object Requests {
		fun createGetRequest(url: String) = Request.Builder().url(url).build()
	}

	override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
		val myThread = ServiceHandler()
		myThread.start()

		return super.onStartCommand(intent, flags, startId)
	}

	override fun onBind(intent: Intent): IBinder? {
		return null
	}
}