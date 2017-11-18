package com.koliadev.haveanotherniceday.module.common

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife
import com.koliadev.haveanotherniceday.R

abstract class BaseActivity<out T : BasePresenter<BaseView>> : AppCompatActivity() {

  private lateinit var presenter: T

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_home)
    ButterKnife.bind(this)
    presenter = initPresenter()
    onCreated()
  }

  protected abstract fun layout(): Int

  protected abstract fun initPresenter(): T

  protected abstract fun onCreated()
}