package com.sway0422.androidmvvm.base

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.sway0422.androidmvvm.base.interfaces.ViewBehavior
import com.sway0422.androidmvvm.extension.observeNonNull

abstract class BaseBVMActivity<B : ViewDataBinding, VM : BaseViewModel> : BaseBindingActivity<B>(),
    ViewBehavior {

    protected lateinit var viewModel: VM
        private set

    override fun initContentView() {
        super.initContentView()
        injectViewModel()
        initInternalObserver()
    }

    protected fun injectViewModel() {
        val vm = createViewModel()
        viewModel = ViewModelProvider(this, BaseViewModel.createViewModelFactory(vm))
            .get(vm::class.java)
        viewModel.application = application
        lifecycle.addObserver(viewModel)
    }

    override fun onDestroy() {
        binding.unbind()
        lifecycle.removeObserver(viewModel)
        super.onDestroy()
    }

    protected fun initInternalObserver() {
        viewModel.loadingEvent.observeNonNull(this) {
            showLoadingUI(it)
        }

        viewModel.emptyPageEvent.observeNonNull(this) {
            showEmptyUI(it)
        }

        viewModel.toastEvent.observeNonNull(this) {
            showToast(it)
        }

        viewModel.pageNavigationEvent.observeNonNull(this) {
            navigate(it)
        }

        viewModel.backPressEvent.observeNonNull(this) {
            backPress(it)
        }

        viewModel.finishPageEvent.observeNonNull(this) {
            finishPage(it)
        }

    }


    protected abstract fun createViewModel(): VM

}