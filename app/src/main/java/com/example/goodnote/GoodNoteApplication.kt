package com.example.goodnote

import android.app.Application
import com.facebook.stetho.Stetho

class GoodNoteApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(this)
    }
}