package com.excellent.customcalendar.base

import android.app.Application
import com.blankj.utilcode.util.Utils

/**
 * 作者：WangBinBin on 1/11 14:17
 * 邮箱：1205998131@qq.com
 */
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
    }
}