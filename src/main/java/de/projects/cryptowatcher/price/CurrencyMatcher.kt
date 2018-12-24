package de.projects.cryptowatcher.price

import android.content.Intent
import com.beust.klaxon.PathMatcher
import de.projects.cryptowatcher.currencies.CryptoCurrencies
import de.projects.cryptowatcher.currencies.FiatCurrencies
import de.projects.cryptowatcher.intents.CryptoIntents
import java.util.regex.Pattern

class CurrencyMatcher(private val crypto: CryptoCurrencies, private val currencyFiat: FiatCurrencies, private val cryptoPriceService: CryptoPriceService) : PathMatcher {
	override fun pathMatches(path: String): Boolean = Pattern.matches(".*data.*currency.*$crypto.*$currencyFiat.*price.*", path)

	override fun onMatch(path: String, value: Any) {
		val intent = Intent("${CryptoIntents.ACTION_CRYPTO_DATA_LOADED}")
		intent.putExtra("$crypto PRICE", value.toString())
		cryptoPriceService.sendBroadcast(intent)
	}
}