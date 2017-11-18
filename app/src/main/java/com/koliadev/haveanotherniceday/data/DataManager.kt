package com.koliadev.haveanotherniceday.data

import android.content.Context
import com.koliadev.haveanotherniceday.data.model.Category
import com.koliadev.haveanotherniceday.data.model.Link
import com.koliadev.haveanotherniceday.data.provider.database.DatabaseProvider

/**
 * Created on 15/11/2017.
 */
class DataManager {

  private lateinit var dbProvider: DatabaseProvider

  fun init(applicationContext: Context) {
    dbProvider = DatabaseProvider(applicationContext)
  }

  fun getSavedLinks(): List<Link> {
    val list = ArrayList<Link>()
    for (i in 1..10) {
      var elt = Link(name = "Name_${i}", url = "Url${i}", category = Category.values()[i % Category.values().size])
      list.add(elt)
    }
    return list
  }

  companion object {
    val instance = DataManager()
  }
}