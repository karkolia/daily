package com.koliadev.haveanotherniceday.module.home

import android.support.v7.widget.RecyclerView
import com.koliadev.haveanotherniceday.R
import com.koliadev.haveanotherniceday.data.model.Link
import com.koliadev.haveanotherniceday.module.common.BaseActivity
import com.koliadev.haveanotherniceday.module.tools.kotterknife.bindView
import com.koliadev.haveanotherniceday.module.tools.recyclr.Recyclr
import com.koliadev.haveanotherniceday.module.tools.recyclr.recyclr


class HomeActivity : BaseActivity<HomePresenter>(), HomeView {

  private val list: RecyclerView by bindView(R.id.list)

  override fun layout() = R.layout.activity_home

  override fun initPresenter() = HomePresenter(this)

  override fun onCreated() {
    list.recyclr<Recyclr.Holder, Link>()
        .layout(R.layout.item_home)
        .holder({ ItemViewHolder(it) }, { h, i ->
          (h as ItemViewHolder).titleTv.text = "youpi_$i"
        })
  }

  override fun setLoadingVisibility(visible: Boolean) {

  }

  override fun setLinksListData(savedLinks: List<Link>) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
}
