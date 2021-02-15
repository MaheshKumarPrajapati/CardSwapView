package com.mahesh_prajapati.itunes.utils

import android.app.Application

class MyApp : Application() {

    companion object {

        var isFirstTime: Boolean=true

        @JvmField
        var appInstance: MyApp? = null

        @JvmStatic
        fun getAppInstance(): MyApp {
            return appInstance as MyApp
        }
    }

    override fun onCreate() {
        super.onCreate()
        appInstance = this;
    }

}