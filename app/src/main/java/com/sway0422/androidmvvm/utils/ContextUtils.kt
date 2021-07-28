package com.sway0422.androidmvvm.utils

import android.app.Application
import android.content.Context

object ContextUtils {

    private var context: Context? = null
    private var application: Application? = null

    private val initLock = Any()

    fun init(application: Application) {
        synchronized(initLock) {
            context = application.applicationContext
            this.application = application
        }
    }

    fun getContext(): Context {
        if (context == null) {
            context = application.applicationContext
        }
        return context!!
    }


}