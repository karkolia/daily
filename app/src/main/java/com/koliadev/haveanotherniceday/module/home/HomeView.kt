package com.koliadev.haveanotherniceday.module.home

import com.koliadev.haveanotherniceday.data.model.Link
import com.koliadev.haveanotherniceday.module.common.BaseView

/**
 * Created on 15/11/2017.
 */
interface HomeView : BaseView {
  fun setLoadingVisibility(visible: Boolean = true)
  fun setLinksListData(savedLinks: List<Link>)
}