package com.koliadev.haveanotherniceday.data.provider.database

import android.content.Context

/**
 * Created on 17/11/2017.
 */
class DatabaseProvider {

  private val helper: BaseDatabaseHelper

  constructor(context: Context) {
    helper = BaseDatabaseHelper(context)
  }
}