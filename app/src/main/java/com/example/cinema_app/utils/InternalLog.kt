package com.example.cinema_app.utils

import android.util.Log
import com.example.cinema_app.BuildConfig

object InternalLog {
    fun d(tag: String, msg: String) {
        if (BuildConfig.USE_LOG)
            Log.d(tag, msg)
    }
}