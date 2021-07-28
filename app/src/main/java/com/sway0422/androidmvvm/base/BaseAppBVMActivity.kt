package com.sway0422.androidmvvm.base

import androidx.databinding.ViewDataBinding

abstract class BaseAppBVMActivity<B : ViewDataBinding, VM: BaseViewModel> : BaseBVMActivity<B, VM>() {
}