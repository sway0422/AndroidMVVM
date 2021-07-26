package com.sway0422.androidmvvm.base.interfaces

interface ViewBehavior {

    fun showLoadingUI(isShow: Boolean)

    fun showEmptyUI(isShow: Boolean)

    fun showToast(map: Map<String, *>)

    fun navigate(page: Any)

    fun backPress(arg: Any?)

    fun finishPage(arg: Any?)

}