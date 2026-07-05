package br.edu.utfpr.financeflow.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object DateFormatter {

    fun format(millis: Long): String {
        val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
        return formatter.format(Date(millis))
    }

    fun dateTime(millis: Long): String {
        val formatter = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
        return formatter.format(Date(millis))
    }
}
