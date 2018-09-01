package de.projects.cryptowatcher

import com.beust.klaxon.Klaxon
import okhttp3.OkHttpClient
import okhttp3.Request
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Assert.assertThat
import org.junit.Test

class ExampleUnitTest {
	private val date = "2018-08-31"
	private val url = "https://api.coindesk.com/v1/bpi/historical/close.json?start=${date}&end=${date}"

	@Test
	fun `bitcoin price close on ${date}`() {
		val close = OkHttpClient.Builder().build().newCall(createGetRequest(url))
		val jsonResponse = Klaxon().parse<BitcoinClosePrice>(close.execute().body()!!.byteStream())
		assertThat(jsonResponse!!.priceOn(date), equalTo(7013.9738))
	}

	companion object Requests {
		fun createGetRequest(url: String) = Request.Builder().url(url).build()
	}
}

class BitcoinClosePrice(val bpi: Map<String, Double>) {
	fun priceOn(date: String): Double? {
		return bpi[date]
	}
}