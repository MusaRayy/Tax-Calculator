package com.musarayy.taxcalculator.utils

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.musarayy.taxcalculator.R
import com.musarayy.taxcalculator.ui.base.BaseActivity
import java.lang.Exception
import java.util.HashMap


fun String.isValidString(): Boolean {
    return (this.trim().isNotEmpty() && !this.equals("null", true))
}

fun String.isValidAmount(): Long {
    try {
        if (this.trim().isNotEmpty() && !this.equals("null", true) && this.toLong() > 0) {
            return this.toLong()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return 0
}

fun TextInputLayout.clearError(textInputEditText: TextInputEditText) {
    textInputEditText.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            this@clearError.error = ""
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(p0: Editable?) {
        }
    })
}

fun String.validateString(): String {
    return this.takeIf { it.isValidString() }?.let { this.trim() } ?: ""
}

fun String.isValidEmail(): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun Throwable.mLogException(logger: OSALogger) {
    logger.e(this.message.toString())
    FirebaseCrashlytics.getInstance().recordException(this)
}


fun showConfirmationDialog(
    context: Context,
    title: String = "",
    message: String,
    isPositiveButtonOnly: Boolean = false,
    isYerOrNo: Boolean = false,
    isCancelable: Boolean = false,
    yesListener: DialogInterface.OnClickListener? = null,
    noListener: DialogInterface.OnClickListener? = null,
    positiveBtnText: String = "",
    negativeBtnText: String = ""
) {

    val builder = AlertDialog.Builder(context, R.style.AppTheme_AlertDialogStyle)
        .setTitle(title)
        .setMessage(message)
        .setCancelable(isCancelable)
        .setPositiveButton(
            when {
                positiveBtnText.isValidString() -> positiveBtnText
                isYerOrNo -> context.getString(R.string.yes)
                else -> context.getString(R.string.Okay)
            }
        ) { dialog, which ->
            yesListener?.onClick(dialog, which)
        }

    if (!isPositiveButtonOnly)

        builder.setNegativeButton(
            when {
                negativeBtnText.isValidString() -> negativeBtnText
                isYerOrNo -> context.getString(R.string.no)
                else -> context.getString(R.string.cancel)
            }
        ) { dialog, which ->
            noListener?.onClick(dialog, which)
        }

    builder.show()

}

fun View.hideKeyboard(context: Context) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm!!.hideSoftInputFromWindow(windowToken, 0)
}

fun FragmentActivity.showToast(message: String, isLong: Boolean = false) {
    val view = (this as BaseActivity).rootView
    view.hideKeyboard(this)
    Snackbar.make(view, message, if (isLong) Snackbar.LENGTH_LONG else Snackbar.LENGTH_SHORT).show()
}

fun Long.getTaxSlabPercentage(isOldTaxYear: Boolean): String {

    when {
        isOldTaxYear -> {
            when {
                this <= 250000 -> return "0 %"
                this in 250001..500000 -> return "5 %"
                this in 500001..1000000 -> return "20 %"
                this > 1000000 -> return "30 %"
            }
        }
        else -> {
            when {
                this <= 250000 -> return "0 %"
                this in 250001..500000 -> return "5 %"
                this in 500001..700000 -> return "10 %"
                this in 700001..1000000 -> return "15 %"
                this in 1000001..1250000 -> return "20 %"
                this in 1250001..1500000 -> return "25 %"
                this > 1500000 -> return "30 %"
            }
        }
    }
    return ""
}

fun Boolean.getTaxRegime(ageRang: Int): HashMap<String, String> {
    val map = HashMap<String, String>()
    val newTaxRegime = this
    if (newTaxRegime) {
        map["0 - 2.5 Lakh"] = "0 %"
        map["2.5 Lakh - 5 Lakh"] = "5 %"
        map["5 Lakh - 7.5 Lakh"] = "10 %"
        map["7.5 Lakh & 10 Lakh"] = "15 %"
        map["10 Lakh - 12.5 Lakh"] = "20 %"
        map["12.5 Lakh - 15 Lakh"] = "25 %"
        map["15 Lakh & above"] = "30 %"
    } else {
        when (ageRang) {
            0 -> {
                map["0 - 2.5 Lakh"] = "0 %"
                map["2.5 Lakh - 5 Lakh"] = "5 %"
                map["5 Lakh - 10 Lakh"] = "20 %"
                map["10 Lakh & above"] = "30 %"
            }
            1 -> {
                map["0 - 3 Lakh"] = "0 %"
                map["3 Lakh - 5 Lakh"] = "5 %"
                map["5 Lakh - 10 Lakh"] = "20 %"
                map["10 Lakh & above"] = "30 %"
            }
            2 -> {
                map["0 - 5 Lakh"] = "0 %"
                map["5 Lakh - 10 Lakh"] = "20 %"
                map["10 Lakh & above"] = "30 %"
            }
        }
    }

    return map
}