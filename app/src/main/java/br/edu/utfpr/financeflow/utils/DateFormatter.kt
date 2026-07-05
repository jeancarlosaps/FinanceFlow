package br.edu.utfpr.financeflow.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object DateFormatter {

    private val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.forLanguageTag("pt-BR")).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }

    fun format(millis: Long): String = formatter.format(Date(millis))
}
