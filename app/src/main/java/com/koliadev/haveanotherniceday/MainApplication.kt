package com.koliadev.haveanotherniceday

import android.app.Application
import com.koliadev.haveanotherniceday.data.DataManager
import timber.log.Timber

/**
 * Created on 17/11/2017.
 */
class MainApplication : Application() {
  init {
    Timber.plant(Timber.DebugTree())
    DataManager.instance.init(applicationContext)
  }
}