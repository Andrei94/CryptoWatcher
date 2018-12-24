package de.projects.cryptowatcher.price

import android.widget.TextView

class PricePresenter(private val price: TextView, private val percent: TextView) {
	fun setCryptoDataWithColor(cryptoData: CryptoData) {
		cryptoData.cryptoValue?.let {
			if(cryptoData.cryptoValue.toDouble() <= 1)
				price.text = "%.4f".format(cryptoData.cryptoValue.toFloat())
			else
				price.text = "%.2f".format(cryptoData.cryptoValue.toFloat())
		}
		cryptoData.cryptoPercent?.let {
			percent.text = "%.2f".format(cryptoData.cryptoPercent.toFloat()).plus("%")
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