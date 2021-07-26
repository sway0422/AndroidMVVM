package com.sway0422.androidmvvm.base

import com.sway0422.androidmvvm.base.interfaces.ViewBehavior


abstract class BaseBindingActivity: BaseActivity(), ViewBehavior {


//    protected lateinit var binding: B
//        private  set

    override fun initContentView() {
        super.initContentView()
    }



}