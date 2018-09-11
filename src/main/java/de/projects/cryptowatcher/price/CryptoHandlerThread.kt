package de.projects.cryptowatcher.price

import android.os.HandlerThread
import com.beust.klaxon.Klaxon
import de.projects.cryptowatcher.currencies.CryptoCurrencies
import de.projects.cryptowatcher.currencies.FiatCurrencies
import okhttp3.OkHttpClient
import java.io.InputStreamReader
import java.net.UnknownHostException

class CryptoHandlerThread(private val url: String, private val crypto: CryptoCurrencies, private val fiat: FiatCurrencies, private val cryptoPriceService: CryptoPriceService) : HandlerThread("crypto") {
	override fun run() {
		val btcGetRequest = OkHttpClient.Builder().build().newCall(CryptoPriceService.createGetRequest(url))
		try {
			val response = btcGetRequest!!.execute()
			Klaxon().pathMatcher(CurrencyMatcher(crypto, fiat, cryptoPriceService))
					.pathMatcher(CurrencyPercentMatcher(crypto, cryptoPriceService))
					.parseJsonObject(InputStreamReader(response.body()!!.byteStream()))
		} catch (e : UnknownHostException) {
			quit()
		}
	}
}