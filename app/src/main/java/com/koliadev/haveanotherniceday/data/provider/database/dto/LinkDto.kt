package com.koliadev.haveanotherniceday.data.provider.database.dto

import io.realm.RealmObject

/**
 * Created on 18/11/2017.
 */
class LinkDto(val name: String, val url: String, val category: String) : RealmObject()