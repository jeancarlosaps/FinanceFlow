package br.edu.utfpr.financeflow.utils

import java.text.NumberFormat
import java.util.Locale

object MoneyFormatter {

    private val locale = Locale.forLanguageTag("pt-BR")

    fun format(value: Double): String =
        NumberFormat.getCurrencyInstance(locale).format(value)

    fun parse(text: String): Double? {
        val trimmed = text.replace("R$", "").replace(" ", "").trim()
        if (trimmed.isEmpty()) return null
        val normalized = if (trimmed.contains(",")) {
            trimmed.replace(".", "").replace(",", ".")
        } else {
            trimmed
        }
        return normalized.toDoubleOrNull()
    }
}
