package de.projects.cryptowatcher.price

import android.os.HandlerThread
import com.beust.klaxon.Klaxon
import de.projects.cryptowatcher.currencies.CryptoCurrencies
import okhttp3.OkHttpClient
import java.io.InputStreamReader
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class CryptoHandlerThread(private val url: String, private val crypto: CryptoCurrencies, private val cryptoPriceService: CryptoPriceService) : HandlerThread("crypto") {
	override fun run() {
		val cryptoGetRequest = OkHttpClient.Builder().build().newCall(CryptoPriceService.createGetRequest(url))
		try {
			val response = cryptoGetRequest!!.execute()
			Klaxon().pathMatcher(CurrencyMatcher(crypto, cryptoPriceService))
					.pathMatcher(CurrencyPercentMatcher(crypto, cryptoPriceService))
					.parseJsonObject(InputStreamReader(response.body()!!.byteStream()))
		} catch (e: UnknownHostException) {
			cryptoPriceService.broadcastNetworkError()
		} catch (e: SocketTimeoutException) {
			cryptoPriceService.broadcastNetworkError()
		}
		quit()
	}
}
