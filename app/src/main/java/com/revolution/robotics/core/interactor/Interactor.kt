package com.revolution.robotics.core.interactor

import com.crashlytics.android.Crashlytics
import com.revolution.robotics.BuildConfig
import com.revolution.robotics.RoboticsApplication
import com.revolution.robotics.features.shared.ErrorHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.kodein.di.erased.instance
import org.revolutionrobotics.robotcontroller.bluetooth.threading.moveToUIThread

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
                        if (!BuildConfig.DEBUG) {
                            Crashlytics.logException(result)
                        }
                        result.printStackTrace()
                        if (onError != null) {
                            onError.invoke(result)
                        } else {
                            errorHandler.onError()
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
