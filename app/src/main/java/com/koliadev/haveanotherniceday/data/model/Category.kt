package com.koliadev.haveanotherniceday.data.model

/**
 * Created on 13/11/2017.
 */
enum class Category {
  WOMEN,
  MEN,
  ANIMALS,
  GEEKS,
  HUMOR,
  WEIRD,
  ART,
  OTHER;

  companion object {
    fun parse(stringValue: String): Category {
      return values().firstOrNull { it.name == stringValue }
          ?: OTHER
    }

  }
}