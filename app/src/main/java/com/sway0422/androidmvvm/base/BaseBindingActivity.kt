package com.sway0422.androidmvvm.base

import androidx.databinding.DataBindingUtil.setContentView
import androidx.databinding.ViewDataBinding

import com.sway0422.androidmvvm.base.interfaces.ViewBehavior

abstract class BaseBindingActivity<B : ViewDataBinding>: BaseActivity(),
    ViewBehavior {

    lateinit var binding: B
        private set

    override fun initContentView() {
        super.initContentView()
    }

    fun injectDataBinding() {
        binding = setContentView(this, getLayoutId())
        binding.lifecycleOwner = this
    }

    override fun onDestroy() {
        binding.unbind()
        super.onDestroy()
    }

}