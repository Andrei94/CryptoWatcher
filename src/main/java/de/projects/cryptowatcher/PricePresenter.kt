package de.projects.cryptowatcher

import android.widget.TextView

class PricePresenter(private val price: TextView, private val percent: TextView) {
	fun setCryptoDataWithColor(cryptoData: CryptoData) {
		cryptoData.cryptoValue?.let {
			price.text = cryptoData.cryptoValue
		}
		cryptoData.cryptoPercent?.let {
			percent.text = cryptoData.cryptoPercent
			if (cryptoData.cryptoPercent.toDouble() >= 0) {
				price.setTextColor(cryptoData.colorAboveZero)
				percent.setTextColor(cryptoData.colorAboveZero)
			} else {
				price.setTextColor(cryptoData.colorBelowZero)
				percent.setTextColor(cryptoData.colorBelowZero)
			}
		}
	}
}

data class CryptoData(val cryptoValue: String?, val cryptoPercent: String?, val colorAboveZero: Int, val colorBelowZero: Int)