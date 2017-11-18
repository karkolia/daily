package com.koliadev.haveanotherniceday.module.home

import com.koliadev.haveanotherniceday.data.DataManager
import com.koliadev.haveanotherniceday.module.common.BasePresenter

/**
 * Created on 15/11/2017.
 */
class HomePresenter(view: HomeView) : BasePresenter<HomeView>(view) {
  override fun onResume() {
    view.setLinksListData(DataManager.instance.getSavedLinks())
  }

  override fun onPause() {
  }
}