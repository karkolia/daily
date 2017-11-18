package com.koliadev.haveanotherniceday.module.common

/**
 * Created on 15/11/2017.
 */
public abstract class BasePresenter<out T : BaseView>(val view: T) {
  abstract fun onResume()
  abstract fun onPause()
}