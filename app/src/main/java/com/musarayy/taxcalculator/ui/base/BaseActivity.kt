package com.musarayy.taxcalculator.ui.base

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.musarayy.taxcalculator.R
import com.musarayy.taxcalculator.network.RetroClient
import com.musarayy.taxcalculator.ui.home.HomeFragment
import com.musarayy.taxcalculator.utils.DataCache
import com.musarayy.taxcalculator.utils.OSALogger
import com.musarayy.taxcalculator.utils.showToast
import org.koin.android.ext.android.inject

@SuppressLint("Registered")
abstract class BaseActivity : AppCompatActivity() {

    lateinit var rootView: View
    private var exitCount = 0
    private var progress: ProgressDialog? = null

    protected val logger: OSALogger by inject()
    protected val dataCache: DataCache by inject()
    protected val retroClient: RetroClient by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        rootView = findViewById(R.id.rootView)

        initViews()
        onClick()
    }

    fun showProgressDialog(message: String) {
        takeUnless { progress != null && progress!!.isShowing }?.let {
            progress = ProgressDialog(this, R.style.AppTheme_AlertDialogStyle)
            progress?.setMessage(getString(R.string.progress_bar_message))
            progress?.setCancelable(false)
            progress?.show()
        }
    }

    fun hideProgressDialog() {
        takeIf { progress != null && progress!!.isShowing }?.let {
            progress!!.dismiss()
        }
    }

    override fun onBackPressed() {
            val frag = supportFragmentManager.findFragmentById(R.id.navHostFragment)?.childFragmentManager?.fragments?.get(0)

        takeIf { frag is HomeFragment }
            ?.let {
                takeIf { exitCount == 1 }
                    ?.let { super.onBackPressed() }
                    ?: let {
                        exitCount++
                        Handler().postDelayed({ exitCount = 0 }, 1000)
                        this.showToast(getString(R.string.click_exit_msg))
                    }
            } ?: let { super.onBackPressed() }
    }


    abstract fun getLayout(): Int
    abstract fun initViews()
    abstract fun onClick()
}