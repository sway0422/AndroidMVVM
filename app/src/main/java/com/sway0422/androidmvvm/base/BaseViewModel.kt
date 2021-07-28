package com.sway0422.androidmvvm.base

import android.app.Application
import androidx.annotation.StringRes
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import com.sway0422.androidmvvm.base.interfaces.ViewBehavior
import com.sway0422.androidmvvm.base.interfaces.ViewModelLifecycle

abstract class BaseViewModel : ViewModel(), ViewModelLifecycle, ViewBehavior {

    // what's MutableLiveData?
    //MutableLiveData is subclass of LiveData with additional setValue/postValue apis
    var loadingEvent = MutableLiveData<Boolean>()

    var emptyPageEvent = MutableLiveData<Boolean>()

    var toastEvent = MutableLiveData<Map<String, *>>() //* is for? Why not Any

    var pageNavigationEvent = MutableLiveData<Any>()

    var backPressEvent = MutableLiveData<Any>()

    var finishPageEvent = MutableLiveData<Any?>()


    lateinit var application: Application

    //Q: what's lifecycle owner?
    private lateinit var lifecycleOwner: LifecycleOwner

    /**
     * execute a Coroutine on main thread
     */
    //Q: what's suspend? Composing suspend function. this function is a blocking function, and must be called within a Coroutine
    //Q: What's CoroutineScope? 划定协程的作用域
    //Q: What's Unit? Void return type
    //Q: What's job? 控制协程的生命周期，帮助协程间进行结构性并发
    //Q: What's viewModelScope? and launch? CoroutineScope
    //Q: Difference between Dispatchers options (Default, Main, IO, unconfined)
    //
    protected fun launchOnUI(block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch(Dispatchers.Main) {block}
    }

    /**
     * execute a Coroutine on I/O thread
     */
    protected fun launchOnIO(block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch(Dispatchers.IO) {block}
    }

    override fun onAny(owner: LifecycleOwner, event: Lifecycle.Event) {

    }

    override fun onCreate() {

    }

    override fun onResume() {

    }

    override fun onDestroy() {

    }

    override fun onStart() {

    }


    override fun onPause() {

    }

    override fun onStop() {

    }


    override fun showLoadingUI(isShow: Boolean) {
        loadingEvent.postValue(isShow)
    }

    override fun showEmptyUI(isShow: Boolean) {
        emptyPageEvent.postValue(isShow)
    }

    override fun showToast(map: Map<String, *>) {
        toastEvent.postValue(map)
    }

    override fun navigate(page: Any) {
        pageNavigationEvent.postValue(page)
    }

    override fun backPress(arg: Any?) {
        backPressEvent.postValue(arg)
    }

    override fun finishPage(arg: Any?) {
        finishPageEvent.postValue(arg)
    }

    protected fun showToast(msg: String, duration: Int? = null) {
//        val map = HashMap<String, Any>().apply {
//            put(
//                FlyBaseConstants.FLY_TOAST_KEY_CONTENT_TYPE,
//                FlyBaseConstants.FLY_TOAST_CONTENT_TYPE_STR
//            )
//            put(FlyBaseConstants.FLY_TOAST_KEY_CONTENT, msg)
//            if (duration != null) {
//                put(FlyBaseConstants.FLY_TOAST_KEY_DURATION, duration)
//            }
//        }
//        showToast(map)
    }

    protected fun showToast(@StringRes resId: Int, duration: Int? = null) {
//        val map = HashMap<String, Any>().apply {
//            put(
//                FlyBaseConstants.FLY_TOAST_KEY_CONTENT_TYPE,
//                FlyBaseConstants.FLY_TOAST_CONTENT_TYPE_RESID
//            )
//            put(FlyBaseConstants.FLY_TOAST_KEY_CONTENT, resId)
//            if (duration != null) {
//                put(FlyBaseConstants.FLY_TOAST_KEY_DURATION, duration)
//            }
//        }
//        showToast(map)
    }

    protected fun backPress() {
        backPress(null)
    }

    protected fun finishPage() {
        finishPage(null)
    }

    companion object {
        @JvmStatic
        fun <T : BaseViewModel> createViewModelFactory(viewModel: T): ViewModelProvider.Factory {
            return ViewModelFactory(viewModel)
        }
    }

}

/**
 * 创建ViewModel的工厂，以此方法创建的ViewModel，可在构造函数中传参
 */
class ViewModelFactory(val viewModel: BaseViewModel) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return viewModel as T
    }
}
