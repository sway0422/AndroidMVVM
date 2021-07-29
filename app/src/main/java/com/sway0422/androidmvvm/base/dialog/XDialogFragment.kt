package com.sway0422.androidmvvm.base.dialog

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

public open class XDialogFragment: DialogFragment() {

    override fun show(manager: FragmentManager, tag: String?) {
        if (manager != null) {
            val ft: FragmentTransaction = manager.beginTransaction()
            ft.add(this, tag)
            ft.commitAllowingStateLoss()
        }
    }

}