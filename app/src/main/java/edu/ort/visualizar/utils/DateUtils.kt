package edu.ort.visualizar.utils

import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


class DateUtils {

    private val inputPattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX"
    private val outputPattern = "dd-MM-yyyy hh:mm a"
    private val validPattern = "yyyy-MM-dd"

    fun parseDate(dateString: String): String{
        val inFormat = SimpleDateFormat(inputPattern, Locale.getDefault())
        val outFormat = SimpleDateFormat(outputPattern, Locale.getDefault())
        val inDate = inFormat.parse(dateString)
        return outFormat.format(inDate)
    }

    fun isValidFormat(dateString: String): Boolean {
        var isValid = true
        try {
            val formatter = SimpleDateFormat(validPattern, Locale.getDefault())
            formatter.parse(dateString)
        } catch (ex: Exception) {
            isValid = false
        }
        return isValid
    }
}