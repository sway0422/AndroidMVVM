package com.sway0422.androidmvvm.base.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerAdapter<T> : RecyclerView.Adapter<BaseViewHolder>() {

    private var items: MutableList<T>? = null
    private var itemClickListener: OnItemClickListener<T>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(getLayoutid(viewType), parent, false)
        return BaseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    abstract @LayoutRes
    fun getLayoutid(viewType: Int): Int

    abstract fun onBindItem(holder: BaseViewHolder, item: T, position: Int)

    fun getItems(): MutableList<T>? {
        return items
    }

    fun setItems(items: MutableList<T>?) {
        this.items = items
    }

    fun addItem(item: T) {
        if (items.isNullOrEmpty()) {
            items = ArrayList<T>()
        }
        items!!.add(item)
        notifyDataSetChanged()
    }

    fun refreshItems(items: MutableList<T>?) {
        this.items = items
        notifyDataSetChanged()
    }

    fun refreshItem(position: Int, item: T) {
        if (!items.isNullOrEmpty() && itemCount > position) {
            items!!.set(position, item)
            notifyItemChanged(position)
        }
    }

    fun removeItem(position: Int) {
        if (itemCount > position) {
            items!!.removeAt(position)
            notifyItemRemoved(position)
            // 使用notifyItemRemoved()方法时此句一定要加，用于通知item的变化，否则item的position会错乱
            notifyItemRangeChanged(0, items!!.size)
        }
    }

    fun clear() {
        if (items != null) {
            items!!.clear()
            items = null
        }
    }

    fun setOnItemClickListener(listener: OnItemClickListener<T>?) {
        this.itemClickListener = listener
    }

    fun getItemClickListener(): OnItemClickListener<T>? {
        return this.itemClickListener
    }

    interface OnItemClickListener<T> {
        fun onItemClick(holder: Any, item: T, position: Int)
    }
}