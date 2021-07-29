package com.sway0422.androidmvvm.base.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseBindingDialogFragment<B : ViewDataBinding> : BaseDialogFragment() {
    protected lateinit var binding: B

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        rootView = binding.root

        return rootView
    }

    override fun getDialogStyle(): Int? {
        return null
    }

    override fun getDialogTheme(): Int? {
        return null
    }
}