package com.koliadev.haveanotherniceday.module.home

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.koliadev.haveanotherniceday.R
import com.koliadev.haveanotherniceday.module.tools.kotterknife.bindView
import com.koliadev.haveanotherniceday.module.tools.recyclr.Recyclr

/**
 * Created on 13/11/2017.
 */
class ItemViewHolder(itemView: View) : Recyclr.Holder(itemView) {
  val thumbnailIv: ImageView by bindView(R.id.thumbnail_iv)
  val titleTv: TextView by bindView(R.id.title_tv)
}