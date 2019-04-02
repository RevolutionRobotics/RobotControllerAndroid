package com.revolution.robotics

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.revolution.robotics.core.interactor.RobotInteractor
import com.revolution.robotics.core.utils.dynamicPermissions.DynamicPermissionHandler
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.erased.instance
import org.revolution.blockly.view.BlocklyView

class MainActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val dynamicPermissionHandler: DynamicPermissionHandler by instance()
    private val robotInteractor: RobotInteractor by instance()

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) =
        dynamicPermissionHandler.onRequestPermissionsResult(requestCode, permissions, grantResults)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(BlocklyView(this))
        robotInteractor.execute({
            Log.e("TEST", it.toString())
        }, {
            Log.e("TEST", "ERRROr")
        })
    }
}
