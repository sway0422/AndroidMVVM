package com.sway0422.androidmvvm.utils

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest

class PermissionUtils {
    interface PermissionCallbacks : EasyPermissions.PermissionCallbacks
    companion object {

        @JvmStatic
        fun hasPermissions(context: Context, vararg perms: String): Boolean {
            return EasyPermissions.hasPermissions(context, *perms)
        }

        @JvmStatic
        fun permissionNeverAsk(host: Activity, perm: String): Boolean {
            return EasyPermissions.permissionPermanentlyDenied(host, perm)
        }

        @JvmStatic
        fun permissionNeverAsk(host: Fragment, perm: String): Boolean {
            return EasyPermissions.permissionPermanentlyDenied(host, perm)
        }

        @JvmStatic
        fun applyPermissions(
            host: Activity,
            tip: String? = null,
            positiveButtonText: String? = null,
            negativeButtonText: String? = null,
            theme: Int? = null,
            requestCode: Int,
            vararg perms: String
        ) {
            var builder = PermissionRequest.Builder(host, requestCode, *perms)
                .also {
                    tip?.run { it.setRationale(this) }
                    positiveButtonText?.run { it.setPositiveButtonText(this) }
                    negativeButtonText?.run { it.setNegativeButtonText(this) }
                    theme?.run { it.setTheme(this) }
                }

            EasyPermissions.requestPermissions(builder.build())
        }

        @JvmStatic
        fun applyPermissions(
            host: Fragment,
            tip: String? = null, // 弹框提示
            positiveButtonText: String? = null, // 弹框确定按钮文字
            negativeButtonText: String? = null, // 弹框取消按钮文字
            theme: Int? = null,
            requestCode: Int,
            vararg perms: String
        ) {
            val builder = PermissionRequest.Builder(host, requestCode, *perms)
                .also {
                    tip?.run { it.setRationale(this) }
                    positiveButtonText?.run { it.setPositiveButtonText(this) }
                    negativeButtonText?.run { it.setNegativeButtonText(this) }
                    theme?.run { it.setTheme(this) }
                }
            EasyPermissions.requestPermissions(builder.build())
        }

        fun onRequestPermissionsResult(
            requestCode: Int,
            perms: Array<out String>,
            grantResults: IntArray,
            vararg receivers: Any
        ) {
            EasyPermissions.onRequestPermissionsResult(requestCode, perms, grantResults, receivers)
        }
    }
}