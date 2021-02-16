package com.mahesh_prajapati.matchingapp.utils

import java.text.SimpleDateFormat
import java.util.*

class HelperMethods {
    fun milliSecondToDuration(milliseconds: Long): String {
        val minutes = milliseconds / 1000 / 60
        val seconds = milliseconds / 1000 % 60
        return "$minutes:$seconds Minutes"
    }

    fun parseDate(date:String): String {
        val inputFormat = SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.US);
        inputFormat.timeZone = TimeZone.getTimeZone("UTC");
        val outputFormat = SimpleDateFormat("MMMM dd,yyyy hh:mm:ss a")
        val parsedDate: Date = inputFormat.parse(date)
        return outputFormat.format(parsedDate)

    }
}