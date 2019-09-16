package com.revolution.robotics.features.onboarding.robotRegistration

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.CaptureManager
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.databinding.FragmentRobotRegistrationBinding
import org.kodein.di.erased.instance

class RobotRegistrationFragment :
    BaseFragment<FragmentRobotRegistrationBinding, RobotRegistrationViewModel>(R.layout.fragment_robot_registration),
    BarcodeCallback {

    override fun barcodeResult(result: BarcodeResult?) {
        Toast.makeText(context, result?.text, Toast.LENGTH_SHORT).show()
    }

    override fun possibleResultPoints(resultPoints: MutableList<ResultPoint>?) {
        //no op
    }

    private val navigator: Navigator by kodein.instance()

    override val viewModelClass: Class<RobotRegistrationViewModel> = RobotRegistrationViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.barcodeView?.decodeSingle(this)
    }

    override fun onResume() {
        super.onResume()
        binding?.barcodeView?.resume()
    }

    override fun onPause() {
        super.onPause()
        binding?.barcodeView?.pause()
    }

    override fun onBackPressed(): Boolean {
        navigator.back()
        return true
    }
}