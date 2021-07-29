package com.sway0422.androidmvvm.base

import android.content.Intent
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.databinding.ViewDataBinding
import com.blankj.utilcode.util.BarUtils

abstract class BaseAppBVMActivity<B : ViewDataBinding, VM: BaseViewModel> :
    BaseBVMActivity<B, VM>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BarUtils.setStatusBarLightMode(this, true)
        BarUtils.transparentStatusBar(this)
    }

    override fun showLoadingUI(isShow: Boolean) {

    }

    override fun showEmptyUI(isShow: Boolean) {

    }

    override fun showToast(map: Map<String, *>) {

    }

    protected fun showToast(str: String) {
        showToast(str, null)
    }

    protected fun showToast(str: String, duration: Int?) {

    }

    protected fun showToast(@StringRes resId: Int) {
        showToast(resId, null)
    }

    protected fun showToast(@StringRes resId: Int, duration: Int?) {

    }

    override fun navigate(page: Any) {
        startActivity(Intent(this, page as Class<*>))
    }

    override fun backPress(arg: Any?) {
        onBackPressed()
    }

    override fun finishPage(arg: Any?) {
        finish()
    }




    }