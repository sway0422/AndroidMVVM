package com.sway0422.androidmvvm.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.sway0422.androidmvvm.base.interfaces.ILazyLoad
import com.sway0422.androidmvvm.utils.PermissionUtils

abstract class BaseFragment : Fragment(), ILazyLoad, PermissionUtils.PermissionCallbacks {

    private var rootView : View? = null

    private var lazyLoadEnable = true

    private var currentState = ILazyLoad.ANY

    private var hasLazyLoad = false

    private var isVisibleToUser = false

    private var isCallUserVisibleHint = false

    protected fun enableLazyLoad(enable: Boolean) {
        this.lazyLoadEnable = enable
    }

    protected fun getLazyLoadState() = ILazyLoad.ON_RESUME

    protected fun setCurrentState(state: Int) {
        this.currentState = state
    }

    protected fun doLazyLoad(callInUserVisibleHint: Boolean) {
        if (!callInUserVisibleHint) {
            if (!isCallUserVisibleHint) isVisibleToUser = !isHidden
        }
        if (lazyLoadEnable && !hasLazyLoad && isVisibleToUser && currentState >= getLazyLoadState()) {
            hasLazyLoad = true
            lazyLoad()
        }
    }

    protected fun getRootView(): View? {
        return rootView
    }

    protected fun setRootView(view: View) {
        this.rootView = view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setCurrentState(ILazyLoad.ON_ATTACH)
        doLazyLoad(false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCurrentState(ILazyLoad.ON_CREATE)
        doLazyLoad(false)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setCurrentState(ILazyLoad.ON_CREATE_VIEW)
        if (rootView != null)
            return rootView
        rootView = inflater.inflate(getLayoutId(), container, false)
        initialize(savedInstanceState)
        doLazyLoad(false)
        return rootView

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setCurrentState(ILazyLoad.ON_ACTIVITY_CREATED)
        doLazyLoad(false)
    }

    override fun onStart() {
        super.onStart()
        setCurrentState(ILazyLoad.ON_START)
        doLazyLoad(false)
    }

    override fun onResume() {
        super.onResume()
        setCurrentState(ILazyLoad.ON_RESUME)
        doLazyLoad(false)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        isVisibleToUser = !hidden
        doLazyLoad(false)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisibleToUser = isVisibleToUser
        isCallUserVisibleHint = true
        doLazyLoad(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        hasLazyLoad = false
        isVisibleToUser = false
        isCallUserVisibleHint = false
    }


    protected abstract @LayoutRes
    fun getLayoutId(): Int

    protected abstract fun initialize(savedInstanceState: Bundle?)

    override fun lazyLoad() {}

    /**============================================================
     *  权限相关
     **===========================================================*/

    /**
     * 检查是否有权限
     */
    fun hasPermissions(vararg perms: String): Boolean {
        return PermissionUtils.hasPermissions(requireContext(), *perms)
    }

    /**
     * 申请权限
     */
    fun applyPermissions(
        tip: String? = null, // 弹框提示
        positiveButtonText: String? = null, // 弹框确定按钮文字
        negativeButtonText: String? = null, // 弹框取消按钮文字
        theme: Int? = null,
        requestCode: Int,
        vararg perms: String
    ) {
        PermissionUtils.applyPermissions(
            this,
            tip,
            positiveButtonText,
            negativeButtonText,
            theme,
            requestCode,
            *perms
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (!perms.isNullOrEmpty()) {
            val refusedPerms = ArrayList<String>()
            val neverAskPerms = ArrayList<String>()
            for (item in perms) {
                if (PermissionUtils.permissionNeverAsk(this, item)) {
                    neverAskPerms.add(item)
                } else {
                    refusedPerms.add(item)
                }
            }
            onPermissonRefused(requestCode, refusedPerms)
            onPermissonNeverAsk(requestCode, neverAskPerms)
        }
    }

    open fun onPermissonRefused(requestCode: Int, perms: MutableList<String>) {

    }

    open fun onPermissonNeverAsk(requestCode: Int, perms: MutableList<String>) {

    }
}