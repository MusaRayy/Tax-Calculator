package com.musarayy.taxcalculator

import android.app.Activity
import android.os.Bundle
import androidx.multidex.MultiDexApplication
import com.google.firebase.analytics.FirebaseAnalytics
import com.musarayy.taxcalculator.services.koinModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import java.lang.ref.WeakReference


class TaxApplication : MultiDexApplication() {

    private lateinit var mFireBaseAnalytics: FirebaseAnalytics

    companion object {
        lateinit var instance: TaxApplication
        private lateinit var mActivity: WeakReference<Activity>
        fun getCurrentActivity(): Activity? {
            return mActivity.get()
        }
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
        mFireBaseAnalytics = FirebaseAnalytics.getInstance(this)

        registerActivity()

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@TaxApplication)
            modules(koinModule)
        }
    }

    private fun registerActivity() {
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(p0: Activity, p1: Bundle?) {
                mActivity = WeakReference(p0)
            }

            override fun onActivityStarted(p0: Activity) {
            }

            override fun onActivityResumed(p0: Activity) {
                mActivity = WeakReference(p0)
            }

            override fun onActivityPaused(p0: Activity) {
            }

            override fun onActivityStopped(p0: Activity) {
            }

            override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
            }

            override fun onActivityDestroyed(p0: Activity) {
                if (mActivity == WeakReference(p0))
                    mActivity.clear()
            }
        })
    }
}