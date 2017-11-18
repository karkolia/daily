package com.koliadev.haveanotherniceday.module.tools.recyclr

import android.content.Context
import android.support.annotation.LayoutRes
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import java.util.*

/**
 * Created by Nicolas Marone on 02/09/2017.
 * Init of recyclr library
 */

class Recyclr<T : Recyclr.Holder, U> {

  private var context: Context? = null
  private var layoutId: Int = 0
  private var adapter: Adapter? = null
  private var recyclerView: RecyclerView
  private var maker: ((v: View) -> T)? = null
  private var binder: ((holder: T, item: U) -> Unit)? = null
  private var items: ArrayList<U>? = null

  constructor(recyclerView: RecyclerView) {
    this.recyclerView = recyclerView
    this.context = recyclerView.context
    initRecyclerView()
  }

  private fun initRecyclerView() {
    val mLayoutManager = LinearLayoutManager(context)
    recyclerView!!.layoutManager = mLayoutManager
    recyclerView!!.itemAnimator = DefaultItemAnimator()
  }

  fun holder(maker: (v: View) -> T, binder: (holder: T, item: U) -> Unit) {
    this.maker = maker
    this.binder = binder
  }

  fun layout(@LayoutRes layoutId: Int): Recyclr<T, U> {
    this.layoutId = layoutId
    this.adapter = Adapter(context!!)
    recyclerView!!.adapter = adapter
    return this
  }

  fun items(items: ArrayList<U>) {
    this.items = items
    this.adapter!!.notifyDataSetChanged()
  }

  abstract class Holder(itemView: View) : ViewHolder(itemView) {

    init {
      ButterKnife.bind(this, itemView)
    }
  }

  private inner class Adapter internal constructor(context: Context) : RecyclerView.Adapter<T>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): T {
      return maker!!(inflater.inflate(layoutId, null))
    }

    override fun onBindViewHolder(holder: T, position: Int) {
      binder!!(holder, items!![position])
    }

    override fun getItemCount(): Int {
      return if (items == null) 0 else items!!.size
    }
  }

}

public fun <T : Recyclr.Holder, U> RecyclerView.recyclr(): Recyclr<T, U> = Recyclr(this)
