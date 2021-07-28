package com.sway0422.androidmvvm.base.http

import androidx.collection.LruCache
import com.blankj.utilcode.util.LogUtils
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.io.File
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.util.concurrent.TimeUnit

abstract class BaseHttpClient {

    //Q: what's difference between open vs public
    open var connectOutTime = 10L
    open var writeOutTime = 30L
    open var readOutTime - 30L
    private val SERVICE_CACHE_COUNT = 20

    private lateinit var serviceCache: LruCache<String, Any>
    private lateinit var okClient: OkHttpClient
    private lateinit var retrofitClient: Retrofit

    fun initialize() {
        serviceCache = LruCache(SERVICE_CACHE_COUNT)
        okClient = buildOKHttpClient()
        retrofitClient = buildRetrofitClient()
    }

    open fun buildOKHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                try {
                    LogUtils.e("FlyOKHttp======>${URLDecoder.decode(message, "utf-8")}")
                } catch (e: UnsupportedEncodingException) {
                    e.printStackTrace()
                    LogUtils.e("FlyOKHttp======>${message}")
                }
            }

        })
        if (isDebug()) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
        }
        var builder = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(connectOutTime, TimeUnit.SECONDS)
            .writeTimeout(writeOutTime, TimeUnit.SECONDS)
            .readTimeout(readOutTime, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .cache(
                Cache(
                    File(ContextUtils.getApplication().cacheDir.toString() + "swayHttpCache"),1024L * 1024 * 100
                    )
                )
            )

        return builder.build()
    }



}