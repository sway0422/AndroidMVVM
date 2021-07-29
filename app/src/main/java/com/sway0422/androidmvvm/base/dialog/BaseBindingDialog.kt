package com.sway0422.androidmvvm.base.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseBindingDialog<B : ViewDataBinding> : BaseDialog {
    protected lateinit var binding: B

    constructor(context: Context) : super(context)

    constructor(context: Context, themeResId: Int) : super(context, themeResId)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize(savedInstanceState)
        refreshAttributes()
    }

    override fun initContentView() {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), getLayoutId(), null, false)
        setContentView(binding.root)
    }

}