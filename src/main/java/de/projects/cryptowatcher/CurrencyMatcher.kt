package de.projects.cryptowatcher

import android.content.Intent
import com.beust.klaxon.PathMatcher
import java.util.regex.Pattern

class CurrencyMatcher(private val crypto: CryptoCurrencies, private val currencyFiat: FiatCurrencies, private val cryptoPriceService: CryptoPriceService) : PathMatcher {
	override fun pathMatches(path: String): Boolean = Pattern.matches(".*$crypto.*header_data.*bpi.*$currencyFiat.*rate_float.*", path)

	override fun onMatch(path: String, value: Any) {
		val intent = Intent("${CryptoIntents.ACTION_CRYPTO_DATA_LOADED}")
		intent.putExtra("$crypto PRICE", value.toString())
		cryptoPriceService.sendBroadcast(intent)
	}
}