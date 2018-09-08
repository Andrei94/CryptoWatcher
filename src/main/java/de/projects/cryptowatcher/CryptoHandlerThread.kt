package de.projects.cryptowatcher

import android.os.HandlerThread
import com.beust.klaxon.Klaxon
import okhttp3.OkHttpClient
import java.io.InputStreamReader

class CryptoHandlerThread(private val url: String, private val crypto: CryptoCurrencies, private val fiat: FiatCurrencies, private val cryptoPriceService: CryptoPriceService) : HandlerThread("crypto") {
	override fun run() {
		val btcGetRequest = OkHttpClient.Builder().build().newCall(CryptoPriceService.createGetRequest(url))
		Klaxon().pathMatcher(CurrencyMatcher(crypto, fiat, cryptoPriceService))
				.pathMatcher(CurrencyPercentMatcher(crypto, cryptoPriceService))
				.parseJsonObject(InputStreamReader(btcGetRequest!!.execute().body()!!.byteStream()))
	}
}