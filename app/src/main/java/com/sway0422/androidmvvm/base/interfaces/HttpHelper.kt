package com.sway0422.androidmvvm.base.interfaces

import com.blankj.utilcode.util.GsonUtils
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

interface HttpHelper {

    fun convertRequestBody(obj: Any): RequestBody {
        return GsonUtils.toJson(obj).toRequestBody("application/json".toMediaTypeOrNull())
    }
}