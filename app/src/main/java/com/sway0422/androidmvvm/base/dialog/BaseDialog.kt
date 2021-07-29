package com.sway0422.androidmvvm.base.dialog

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.FloatRange
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

abstract class BaseDialog : AppCompatDialog {

    private var width: Int = ViewGroup.LayoutParams.MATCH_PARENT
    private var height: Int = ViewGroup.LayoutParams.WRAP_CONTENT
    private var gravity: Int = Gravity.CENTER
    private var animRes: Int = -1
    private var dimAmount: Float = 0.5f
    private var alpha: Float = 1f

    protected var uiScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    constructor(context: Context) : super(context)

    constructor(context: Context, themeResId: Int) : super(context, themeResId)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initContentView()
        initialize(savedInstanceState)
        refreshAttributes()
    }

    protected open fun initContentView() {
        setContentView(getLayoutId())
    }

    fun setWidth(width: Int) {
        this.width = width
    }

    fun setHeight(height: Int) {
        this.height = height
    }

    fun setGravity(gravity: Int) {
        this.gravity = gravity
    }

    fun setDimAmount(@FloatRange(from = 0.0, to = 1.0) dimAmount: Float) {
        this.dimAmount = dimAmount
    }


    fun setAlpha(@FloatRange(from = 0.0, to = 1.0) alpha: Float) {
        this.alpha = alpha
    }

    fun setAnimationRes(animation: Int) {
        this.animRes = animation
    }

    fun refreshAttributes() {
        window!!.let {
            val params: WindowManager.LayoutParams = it.getAttributes()
            params.width = width
            params.height = height
            params.gravity = gravity
            params.windowAnimations = animRes
            params.dimAmount = dimAmount
            params.alpha = alpha
            it.attributes = params
        }
    }

    override fun dismiss() {
        uiScope.cancel()
        super.dismiss()
    }

    protected abstract @LayoutRes
    fun getLayoutId(): Int

    protected open fun initialize(savedInstanceState: Bundle?) {

    }

}