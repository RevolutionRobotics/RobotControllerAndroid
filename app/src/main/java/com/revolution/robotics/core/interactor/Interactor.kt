package com.revolution.robotics.core.interactor

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
                    InteractorResponse(getData())
                } catch (exception: Exception) {
                    InteractorResponse<T>(null, exception)
                }
            }.let { result ->
                result.data?.let { onResponse.invoke(it) }
                result.throwable?.let { onError.invoke(it) }
            }
        }
    }

    abstract fun getData(): T

    data class InteractorResponse<T>(val data: T?, val throwable: Throwable? = null)

    interface InteractorCallback<T> {
        fun onResponse(result: T)
        fun onError(throwable: Throwable)
    }
}
