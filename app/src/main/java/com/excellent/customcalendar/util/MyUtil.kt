package com.excellent.customcalendar.util

import java.util.*

/**
 * 作者：WangBinBin on 1/11 16:33
 * 邮箱：1205998131@qq.com
 */
class MyUtil {
    companion object {
        fun getWeekDayOfMonth(mYear: Int, mMonth: Int): Int {
            var calendar = Calendar.getInstance()
            calendar.set(mYear, mMonth, 1)
            return calendar.get(Calendar.DAY_OF_WEEK) - 1
        }

        fun addZero(month: Int): String =
                when (month) {
                    in 1..9 -> "0$month"
                    else -> month.toString()
                }
    }
}