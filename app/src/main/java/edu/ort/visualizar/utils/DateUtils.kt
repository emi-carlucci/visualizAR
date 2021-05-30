package edu.ort.visualizar.utils

import java.text.SimpleDateFormat
import java.util.*


class DateUtils {

    private val inputPattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX"
    private val outputPattern = "dd-MM-yyyy hh:mm a"

    fun parseDate(dateString: String): String{
        val inFormat = SimpleDateFormat(inputPattern, Locale.getDefault())
        val outFormat = SimpleDateFormat(outputPattern, Locale.getDefault())
        val inDate = inFormat.parse(dateString)
        return outFormat.format(inDate)
    }

}