package com.revolution.robotics.core.interactor

import com.crashlytics.android.Crashlytics
import com.revolution.bluetooth.threading.moveToUIThread
import com.revolution.robotics.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

abstract class Interactor<T> {

    private var runningJob: Job? = null

    fun execute(callback: InteractorCallback<T>? = null) {
        if (callback == null) {
            execute({}, { it.printStackTrace() })
        } else {
            execute(callback::onResponse, callback::onError)
        }
    }

    @Suppress("TooGenericExceptionCaught")
    fun execute(onResponse: (result: T) -> Unit, onError: (throwable: Throwable) -> Unit) {
        runningJob?.cancel()
        runningJob = GlobalScope.launch {
            withContext(Dispatchers.Default) {
                try {
                    getData()
                } catch (exception: Exception) {
                    exception
                }
            }.let { result ->
                moveToUIThread {
                    if (result is Throwable) {
                        if (!BuildConfig.DEBUG) {
                            Crashlytics.logException(result)
                        }
                        onError.invoke(result)
                    } else {
                        onResponse.invoke(result as T)
                    }
                }
            }
        }
    }

    abstract fun getData(): T

    interface InteractorCallback<T> {
        fun onResponse(result: T)
        fun onError(throwable: Throwable)
    }
}
