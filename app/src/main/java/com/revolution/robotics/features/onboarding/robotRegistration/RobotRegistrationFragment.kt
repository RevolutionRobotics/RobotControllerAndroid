package com.revolution.robotics.features.onboarding.robotRegistration

import android.content.res.Resources
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.Size
import com.revolution.robotics.BaseFragment
import com.revolution.robotics.R
import com.revolution.robotics.analytics.Reporter
import com.revolution.robotics.core.extensions.dp
import com.revolution.robotics.core.extensions.px
import com.revolution.robotics.core.utils.AppPrefs
import com.revolution.robotics.core.utils.Navigator
import com.revolution.robotics.databinding.FragmentRobotRegistrationBinding
import org.kodein.di.erased.instance

class RobotRegistrationFragment :
    BaseFragment<FragmentRobotRegistrationBinding, RobotRegistrationViewModel>(R.layout.fragment_robot_registration),
    BarcodeCallback {

    private val navigator: Navigator by kodein.instance()
    private val reporter: Reporter by kodein.instance()
    private val appPrefs: AppPrefs by kodein.instance()

    override val viewModelClass: Class<RobotRegistrationViewModel> = RobotRegistrationViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.barcodeView?.decodeSingle(this)
        val height = Resources.getSystem().displayMetrics.heightPixels
        binding?.barcodeView?.barcodeView?.framingRectSize = Size(height / 2, height / 2)
        binding?.barcodeView?.statusView?.visibility = View.GONE
        binding?.btnBack?.setOnClickListener { onBackPressed() }
        binding?.btnSkip?.setOnClickListener { onBackPressed() }
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
        reporter.reportEvent(Reporter.Event.SKIPPED_ROBOT_REGISTRATION)
        appPrefs.robotRegistered = true
        navigator.back()
        return true
    }

    override fun barcodeResult(result: BarcodeResult?) {
        if (result != null) {
            reporter.setUserProperty(Reporter.UserProperty.ROBOT_ID, result.text)
            reporter.reportEvent(Reporter.Event.REGISTERED_ROBOT)
            appPrefs.robotRegistered = true
            navigator.back()
        }
    }

    override fun possibleResultPoints(resultPoints: MutableList<ResultPoint>?) {
        //no op
    }
}