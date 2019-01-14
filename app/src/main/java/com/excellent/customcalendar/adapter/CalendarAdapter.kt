package com.excellent.customcalendar.adapter

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.excellent.customcalendar.R
import com.excellent.customcalendar.data.DayBean

/**
 * 作者：WangBinBin on 1/11 14:26
 * 邮箱：1205998131@qq.com
 */
class CalendarAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(), View.OnClickListener {

    var dayList = ArrayList<DayBean>()
    lateinit var checkListener: OnDataCheckListener
    lateinit var context: Context

    public fun setData(dayList: MutableList<DayBean>) {
        this.dayList.clear()
        this.dayList.addAll(dayList)
        notifyDataSetChanged()
    }

    public fun setOnDataCheckListener(checkListener: OnDataCheckListener) {
        this.checkListener = checkListener
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {

        if (!::context.isInitialized) {
            context = p0.context
        }

        var view = LayoutInflater.from(context).inflate(R.layout.item_home_date_time_day, p0, false)

        return CurViewHolder(view)

    }

    override fun getItemCount(): Int = dayList.size

    override fun onBindViewHolder(curViewHolder: RecyclerView.ViewHolder, position: Int) {

        curViewHolder as CurViewHolder

        if (dayList.get(position).dayStr.equals("00")) {
            curViewHolder.monthDayStr.visibility = View.INVISIBLE
            curViewHolder.monthDayStr.setOnClickListener(null)
            curViewHolder.monthDayStr.tag = position
        } else {
            curViewHolder.monthDayStr.visibility = View.VISIBLE
            curViewHolder.monthDayStr.setOnClickListener(this)
            curViewHolder.monthDayStr.tag = position
        }

        curViewHolder.monthDayStr.setText(dayList.get(position).dayStr)


        if (dayList.get(position).isChecked) {
            curViewHolder.monthDayStr.setTextColor(Color.parseColor("#0EA6EB"))
        } else {
            curViewHolder.monthDayStr.setTextColor(Color.parseColor("#333333"))
        }


    }

    override fun onClick(v: View?) {
        var pos: Int = v?.tag as Int
        when (v?.id) {
            R.id.month_day_str -> {
                for (dayBean in dayList) {
                    dayBean.isChecked = false
                }
                dayList.get(pos).isChecked = true

                if (::checkListener.isInitialized) {
                    checkListener.onChecked(pos)
                }

            }

        }
        notifyDataSetChanged()
    }

    public interface OnDataCheckListener {
        fun onChecked(pos: Int)
    }

    inner class CurViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var monthDayStr: TextView = view.findViewById(R.id.month_day_str)
    }

}