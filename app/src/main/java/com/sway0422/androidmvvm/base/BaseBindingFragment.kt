package com.sway0422.androidmvvm.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.sway0422.androidmvvm.base.interfaces.ILazyLoad

abstract class BaseBindingFragment<B: ViewDataBinding> : BaseFragment() {

    protected lateinit var binding: B
        private set

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setCurrentState(ILazyLoad.ON_CREATE_VIEW)
        if (getRootView() != null) {
            return getRootView()
        }
        injectDataBinding(inflater, container)
        initialize(savedInstanceState)
        doLazyLoad(false)
        return getRootView()
    }

    protected open fun injectDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        binding.lifecycleOwner = this
        setRootView(binding.root)
    }

    override fun onDestroyView() {
        binding.unbind()
        super.onDestroyView()
    }
}