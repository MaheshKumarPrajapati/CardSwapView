package com.mahesh_prajapati.matchingapp.utils

import android.app.Application

class MyApp : Application() {

    companion object {

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