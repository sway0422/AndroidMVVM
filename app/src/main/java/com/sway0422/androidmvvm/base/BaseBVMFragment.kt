package com.sway0422.androidmvvm.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.sway0422.androidmvvm.base.interfaces.ILazyLoad
import com.sway0422.androidmvvm.base.interfaces.ViewBehavior
import com.sway0422.androidmvvm.extension.observeNonNull

abstract class BaseBVMFragment<B : ViewDataBinding, VM : BaseViewModel> : BaseBindingFragment<B>(),
    ViewBehavior {

    protected lateinit var viewModel: VM
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
        injectViewModel()
        initialize(savedInstanceState)
        initInternalObserver()
        return getRootView()
    }

    protected fun injectViewModel() {
        val vm = createViewModel()
        viewModel = ViewModelProvider(this, BaseViewModel.createViewModelFactory(vm)).get(vm::class.java)
        viewModel.application = requireActivity().application
        lifecycle.addObserver(viewModel)
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(viewModel)
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

