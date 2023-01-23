package com.musarayy.taxcalculator.network

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat

object PermissionManager {

    private val PERMISSION_REQUEST_CODE = 101

    private fun shouldAskPermission(context: Context, permission: String): Boolean {
        val permissionResult = ActivityCompat.checkSelfPermission(context, permission)
        if (permissionResult != PackageManager.PERMISSION_GRANTED) {
            return true
        }
        return false
    }

    fun requestPermission(
        activity: Activity,
        permissions: Array<String>,
        listener: PermissionAskListener
    ) {
        permissions.forEach {
            if (shouldAskPermission(activity, it)) {
                ActivityCompat.requestPermissions(activity, permissions, PERMISSION_REQUEST_CODE)
            } else {
                listener.onPermissionGranted(it)
            }
        }

    }

    fun processRequestPermissionResult(
        activity: Activity,
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
        listener: PermissionAskListener
    ) {

        when {

            (requestCode == PERMISSION_REQUEST_CODE && grantResults.isNotEmpty() && permissions.isNotEmpty()) -> {

                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    listener.onPermissionGranted(permissions[0])
                } else {

                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            activity,
                            permissions[0]
                        )
                    ) {
                        listener.onPermissionDisabled(permissions[0])
                    } else {
                        listener.onPermissionPermanentlyDenied(permissions[0])
                    }
                }
            }
        }
    }

    interface PermissionAskListener {

        fun onPermissionPermanentlyDenied(permission: String)

        fun onPermissionDisabled(permission: String)

        fun onPermissionGranted(permission: String)
    }
}