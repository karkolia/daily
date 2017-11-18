package com.koliadev.haveanotherniceday.utils

import timber.log.Timber

/**
 * Created on 18/11/2017.
 */

fun log(message: String) = Timber.d(message)

fun log(exception: Exception) = Timber.e(exception)

fun <T> isEmptyOrNull(collection: Collection<T>?) = collection == null || collection.isEmpty()

fun isEmptyOrNull(value: String?) = value == null || value.isEmpty()