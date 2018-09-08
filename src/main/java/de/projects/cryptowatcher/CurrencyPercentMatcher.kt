package de.projects.cryptowatcher

import android.content.Intent
import com.beust.klaxon.PathMatcher
import java.util.regex.Pattern

class CurrencyPercentMatcher(private val crypto: CryptoCurrencies, private val cryptoPriceService: CryptoPriceService) : PathMatcher {
	override fun pathMatches(path: String): Boolean = Pattern.matches(".*$crypto.*header_data.*changepc.*", path)

	override fun onMatch(path: String, value: Any) {
		val intent = Intent("${CryptoIntents.ACTION_CRYPTO_PERCENT_LOADED}")
		intent.putExtra("$crypto PERCENT", value.toString())
		cryptoPriceService.sendBroadcast(intent)
	}
}