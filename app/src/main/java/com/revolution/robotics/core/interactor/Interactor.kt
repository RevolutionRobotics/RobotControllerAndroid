package com.revolution.robotics.core.interactor

import com.revolution.robotics.RoboticsApplication
import com.revolution.robotics.features.shared.ErrorHandler
import kotlinx.coroutines.*
import org.kodein.di.erased.instance
import org.revolutionrobotics.bluetooth.android.threading.moveToUIThread

abstract class Interactor<T> {

    private val errorHandler: ErrorHandler by RoboticsApplication.kodein.instance()
    private var runningJob: Job? = null

    fun execute() =
        execute({}, null)

    fun execute(onResponse: (result: T) -> Unit) =
        execute(onResponse, null)

    @Suppress("TooGenericExceptionCaught")
    fun execute(onResponse: (result: T) -> Unit, onError: ((throwable: Throwable) -> Unit)?) {
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
                        result.printStackTrace()
                        if (onError != null) {
                            onError.invoke(result)
                        } else {
                            errorHandler.onError(result)
                        }
                    } else {
                        onResponse.invoke(result as T)
                    }
                }
            }
        }
    }

    abstract fun getData(): T
}
