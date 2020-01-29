package com.revolution.robotics

import android.os.Bundle
import android.os.Handler
import android.os.Message
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.revolution.robotics.analytics.Reporter
import com.revolution.robotics.core.cache.RemoteDataCache
import com.revolution.robotics.core.extensions.hideSystemUI
import com.revolution.robotics.core.interactor.api.RefreshDataCacheInteractor
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.core.utils.dynamicPermissions.DynamicPermissionHandler
import com.revolution.robotics.features.bluetooth.BluetoothManager
import com.revolution.robotics.features.shared.ErrorHandler
import com.revolution.robotics.features.splash.forceUpdate.ForceUpdateDialog
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.erased.instance
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity(), KodeinAware, Navigator.NavigationEventListener {

    private companion object {
        const val WINDOW_HIDE_DELAY = 300L
    }

    override val kodein by kodein()
    private val dynamicPermissionHandler: DynamicPermissionHandler by instance()
    private val navigator: Navigator by instance()
    private val errorHandler: ErrorHandler by instance()
    private val bluetoothManager: BluetoothManager by instance()
    private val navController: NavController by lazy { findNavController(R.id.nav_host_fragment) }
    private val reporter: Reporter by instance()
    private val refreshDataCacheInteractor: RefreshDataCacheInteractor by instance()
    private val remoteDataCache: RemoteDataCache by instance()

    private var windowHideHandler: WindowHideHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigator.registerListener(this)
        windowHideHandler = WindowHideHandler(this)
    }

    override fun onStart() {
        super.onStart()
        errorHandler.registerContext(this)
        bluetoothManager.init(this)
        refreshDataCacheInteractor.execute (
            onResponse = {
                if (remoteDataCache.data.minVersion.android > BuildConfig.VERSION_CODE) {
                    ForceUpdateDialog.newInstance().show(supportFragmentManager)
                }
            }
        )
    }

    override fun onResume() {
        super.onResume()
        window.hideSystemUI()
    }

    // From the official Google sample app:
    // https://android.googlesource.com/platform/development/+/e7a6ab4/samples/devbytes/ui/ImmersiveMode/src/main/java/com/example/android/immersive/ImmersiveActivity.java
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            windowHideHandler?.sendEmptyMessageDelayed(0, WINDOW_HIDE_DELAY)
        } else {
            windowHideHandler?.removeMessages(0)
        }
    }

    override fun onStop() {
        reporter.reportEvent(
            Reporter.Event.LEAVE_APP,
            Bundle().apply {
                putString(
                    Reporter.Parameter.SCREEN.parameterName,
                    reporter.lastOpenedScreen?.screenName ?: ""
                )
            })
        bluetoothManager.shutDown()
        errorHandler.releaseContext(this)
        super.onStop()
    }

    override fun onDestroy() {
        navigator.unregisterListener(this)
        super.onDestroy()
    }

    override fun onBackPressed() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val currentFragment =
            (navHostFragment as NavHostFragment).childFragmentManager.primaryNavigationFragment
        if ((currentFragment as? BaseFragment<*, *>)?.onBackPressed() != true) {
            super.onBackPressed()
        }
    }

    override fun onNavigationEvent(targetId: Int) {
        navController.navigate(targetId)
    }

    override fun onNavigationEvent(navDirections: NavDirections) {
        navController.navigate(navDirections)
    }

    override fun back(count: Int) {
        repeat(count) {
            navController.navigateUp()
        }
    }

    override fun popUntil(vararg fragmentId: Int) {
        while (navController.currentDestination?.id ?: 0 !in fragmentId) {
            navController.popBackStack()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) =
        dynamicPermissionHandler.onRequestPermissionsResult(
            requestCode,
            permissions,
            grantResults,
            this
        )

    private class WindowHideHandler(activity: MainActivity) : Handler() {
        private val mActivity: WeakReference<MainActivity> = WeakReference(activity)

        override fun handleMessage(msg: Message) {
            mActivity.get()?.window?.hideSystemUI()
        }
    }
}
