package com.sway0422.androidmvvm.base.recyclerview

import android.util.SparseArray
import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView

class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val views: SparseArray<View>

    init {
        views = SparseArray()
    }

    fun <T: View> getView(@IdRes resId: Int): T {
        var v: View? = views.get(resId)
        if (v == null) {
            v = itemView.findViewById(resId)
            views.put(resId, v)
        }
        return v as T
    }
}