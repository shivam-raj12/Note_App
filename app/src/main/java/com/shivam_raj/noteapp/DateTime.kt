package com.shivam_raj.noteapp

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val DATE_FORMAT = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
private val TIME_FORMAT = SimpleDateFormat("hh:mm a", Locale.ENGLISH)

/**
 * Returns a string time in the format of dd/MM/yyyy hh:mm a
 * @param millisecond time in millisecond
 *
 * @return A formatted String time of the given millisecond.
 */
fun getSimpleFormattedDate(millisecond: Long): String {
    return SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault()).format(Date(millisecond))
}

/**
 *  Returns a string time of the given millisecond. Below is the condition how the returned string will be formatted.
 *  - If the millisecond represents today's date than it will return - Today, $time.
 *  - If the millisecond represents yesterday's date than it will return - Yesterday, $time.
 *  - Else it will only return the date represented by the given millisecond - dd/MM/yyyy.
 *
 * @param millisecond time in millisecond
 *
 * @return A formatted String time of the given millisecond.
 */
fun getStandardFormattedDate(millisecond: Long): String {

    val currentDate = DATE_FORMAT.format(Date(System.currentTimeMillis()))
    val lastUpdateDate = DATE_FORMAT.format(Date(millisecond))

    return if (currentDate == lastUpdateDate) {
        "Today, ${TIME_FORMAT.format(Date(millisecond))}"
    } else if (
        (currentDate.substring(0..1).toInt() - lastUpdateDate.substring(0..1).toInt() == 1) &&
        (currentDate.substring(3) == lastUpdateDate.substring(3))
    ) {
        "Yesterday, ${TIME_FORMAT.format(Date(millisecond))}"
    } else {
        lastUpdateDate
    }
}
