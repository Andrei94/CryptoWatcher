package de.projects.cryptowatcher.price

import android.content.Intent
import com.beust.klaxon.PathMatcher
import de.projects.cryptowatcher.currencies.CryptoCurrencies
import de.projects.cryptowatcher.currencies.FiatCurrencies
import de.projects.cryptowatcher.intents.CryptoIntents
import java.util.regex.Pattern

class CurrencyPercentMatcher(private val crypto: CryptoCurrencies, private val currencyFiat: FiatCurrencies, private val cryptoPriceService: CryptoPriceService) : PathMatcher {
	override fun pathMatches(path: String): Boolean = Pattern.matches(".*data.*currency.*$crypto.*$currencyFiat.*percent.*", path)

	override fun onMatch(path: String, value: Any) {
		val intent = Intent("${CryptoIntents.ACTION_CRYPTO_PERCENT_LOADED}")
		intent.putExtra("$crypto PERCENT", value.toString())
		cryptoPriceService.sendBroadcast(intent)
	}
}