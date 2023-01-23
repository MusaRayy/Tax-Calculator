package com.musarayy.taxcalculator.ui.home

import android.app.ActivityOptions
import android.content.Intent
import android.os.Handler
import android.view.View
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.database.*
import com.musarayy.taxcalculator.BuildConfig
import com.musarayy.taxcalculator.R
import com.musarayy.taxcalculator.network.checkInternetConnection
import com.musarayy.taxcalculator.ui.base.BaseActivity
import com.musarayy.taxcalculator.utils.AppConstants.APP_CONFIGS
import com.musarayy.taxcalculator.utils.AppConstants.APP_VERSION
import com.musarayy.taxcalculator.utils.showConfirmationDialog
import kotlinx.coroutines.Runnable

class LandingActivity : BaseActivity() {

    var mRef: DatabaseReference? = null

    override fun getLayout(): Int {
        return R.layout.activity_landing_page
    }

    override fun initViews() {
        (findViewById<LottieAnimationView>(R.id.landingAnimationView)).playAnimation()

        Handler().postDelayed(Runnable {

            if (!checkInternetConnection()) {
                showConfirmationDialog(
                    context = this,
                    isYerOrNo = true,
                    positiveBtnText = getString(R.string.settings),
                    negativeBtnText = getString(R.string.retry),
                    message = getString(R.string.no_internet),
                    yesListener = { _, _ ->
                        startActivity(Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS))
                    },
                    noListener = { _, _ -> recreate() }
                )
            }

            mRef = FirebaseDatabase
                .getInstance()
                .getReference(APP_CONFIGS)

            mRef!!.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    if (dataSnapshot.child(APP_VERSION).value == (BuildConfig.VERSION_NAME)) {
                        mRef!!.removeEventListener(this)
                        moveNextScreen()
                    } else showForceUpgradeAlert()
                }

                override fun onCancelled(error: DatabaseError) {
                    mRef!!.removeEventListener(this)
                    moveNextScreen()
                }
            })
        }, 3000)
    }

    override fun onClick() {

    }

    private fun showForceUpgradeAlert() {

        showConfirmationDialog(
            context = this,
            title = "Update ${getString(R.string.app_name)}",
            message = String.format(getString(R.string.update_msg), getString(R.string.app_name)),
            positiveBtnText = getString(R.string.update),
            negativeBtnText = getString(R.string.not_now),
            yesListener = { _, _ -> moveNextScreen() },
            noListener = { _, _ -> moveNextScreen() }
        )
    }

    private fun moveNextScreen() {
        if (!isFinishing && !isDestroyed)
            finish()
        startActivity(
            Intent(this, MainActivity::class.java),
            ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
        )
    }
}