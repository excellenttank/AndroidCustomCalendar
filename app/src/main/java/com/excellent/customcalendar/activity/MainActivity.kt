package com.excellent.customcalendar.activity

import android.app.Dialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.TimeUtils
import com.excellent.customcalendar.R
import com.excellent.customcalendar.adapter.CalendarAdapter
import com.excellent.customcalendar.data.DayBean
import com.excellent.customcalendar.util.MyUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_date_time_chose.view.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var chooseDateRangeDialog: Dialog
    var dayList = ArrayList<DayBean>()
    var dateRangeMiddleIndex = 0
    lateinit var calendarAdapter: CalendarAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_show_calendar.setOnClickListener(this)

    }

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.btn_show_calendar -> {
                if (!::chooseDateRangeDialog.isInitialized) {
                    val view = LayoutInflater.from(this).inflate(R.layout.dialog_date_time_chose, null)

                    chooseDateRangeDialog = Dialog(this, R.style.dialog_normal)

                    chooseDateRangeDialog.setContentView(view)

                    view.cur_month.text = TimeUtils.getNowString(SimpleDateFormat("yyyy/MM", Locale.CHINA))

                    view.pre_month.setOnClickListener {
                        dateRangeMiddleIndex--
                        setMonthDay(view)
                    }

                    view.next_month.setOnClickListener {
                        dateRangeMiddleIndex++
                        setMonthDay(view)
                    }

                    calendarAdapter = CalendarAdapter()

                    view.month_day_grid.adapter = calendarAdapter

                    calendarAdapter.setOnDataCheckListener(object : CalendarAdapter.OnDataCheckListener {
                        override fun onChecked(pos: Int) {
                            chooseDateRangeDialog.dismiss()
                            date_tv.text = view.cur_month.text.toString().replace("/", "-") + "-" +
                                    dayList.get(pos).dayStr
                        }
                    })

                    setMonthDay(view)

                }
                chooseDateRangeDialog.setCancelable(true)
                chooseDateRangeDialog.show()
                var lp = chooseDateRangeDialog.window.attributes
                lp.width = (ScreenUtils.getScreenWidth() * 0.95).toInt()
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT
                chooseDateRangeDialog.window.attributes = lp
            }
        }

    }


    fun setMonthDay(view: View) {
        var calendar = Calendar.getInstance()
        calendar.firstDayOfWeek = Calendar.MONDAY
        calendar.set(Calendar.MONTH, dateRangeMiddleIndex)
        calendar.set(Calendar.DATE, 1);
        calendar.roll(Calendar.DATE, -1);
        view.cur_month.text = SimpleDateFormat("yyyy/MM", Locale.CHINA).format(calendar.time)

        var curMonthDaysCount = calendar.get(Calendar.DATE)
        dayList.clear()

        var dayOfWeek = MyUtil.getWeekDayOfMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH))

        var blankFillCount = 0

        if (dayOfWeek > 0) {
            blankFillCount = dayOfWeek - 1
        } else {
            blankFillCount = 6
        }

        for (i in 0 until blankFillCount) {
            var dayBean = DayBean("00", false)
            dayList.add(dayBean)
        }

        for (i in 1..curMonthDaysCount) {
            var dayBean = DayBean(MyUtil.addZero(i), false)
            dayList.add(dayBean)
        }
        calendarAdapter.setData(dayList)

    }


}
