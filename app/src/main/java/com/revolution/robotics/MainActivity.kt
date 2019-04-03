package com.revolution.robotics

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.revolution.robotics.core.utils.dynamicPermissions.DynamicPermissionHandler
import com.revolution.robotics.mainmenu.MainMenuFragment
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.erased.instance
import org.revolution.blockly.view.BlocklyView

class MainActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val dynamicPermissionHandler: DynamicPermissionHandler by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val frameLayout = FrameLayout(this)
        frameLayout.id = R.id.line1
        setContentView(frameLayout)

        supportFragmentManager.beginTransaction()
            .replace(R.id.line1, MainMenuFragment())
            .commit()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) =
        dynamicPermissionHandler.onRequestPermissionsResult(requestCode, permissions, grantResults)
}
