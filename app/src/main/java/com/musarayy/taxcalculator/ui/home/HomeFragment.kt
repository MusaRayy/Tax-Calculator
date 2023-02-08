package com.musarayy.taxcalculator.ui.home

import android.view.View
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.musarayy.taxcalculator.R
import com.musarayy.taxcalculator.ui.base.BaseFragment
import com.musarayy.taxcalculator.ui.taxCalculation.TaxCalculatorFragment
import com.musarayy.taxcalculator.utils.clearError
import com.musarayy.taxcalculator.utils.getTaxRegime
import com.musarayy.taxcalculator.utils.hideKeyboard
import com.musarayy.taxcalculator.utils.isValidAmount

class HomeFragment : BaseFragment(), AdapterView.OnItemSelectedListener {

    private lateinit var ageSpinner: Spinner
    private lateinit var calculateBtn: TextView
    private lateinit var taxFySpinner: Spinner
    private lateinit var taxModeTxt: SwitchCompat
    private lateinit var taxSlabLayout: LinearLayout
    private lateinit var grossSalaryTIL: TextInputLayout
    private lateinit var grossSalaryTIE: TextInputEditText

    override fun getLayout(): Int {
        return R.layout.fragment_home
    }

    override fun initViews(view: View) {
        ageSpinner = view.findViewById(R.id.ageSpinner)
        taxModeTxt = view.findViewById(R.id.taxModeTxt)
        taxFySpinner = view.findViewById(R.id.taxFySpinner)
        calculateBtn = view.findViewById(R.id.calculateBtn)
        grossSalaryTIL = view.findViewById(R.id.grossSalaryTIL)
        grossSalaryTIE = view.findViewById(R.id.grossSalaryTIE)
        taxSlabLayout = view.findViewById(R.id.taxSlabLayoutInflator)
    }

    override fun onClick() {

        grossSalaryTIL.clearError(grossSalaryTIE)

        calculateBtn.setOnClickListener {
            it?.hideKeyboard(requireContext())
            val grossSalary = grossSalaryTIE.text.toString()

            when {
                grossSalary.isValidAmount() <= 0 -> {
                    grossSalaryTIL.error = getString(R.string.invalid_amount)
                }
                grossSalary.isValidAmount() <= 200000 -> {
                    grossSalaryTIL.error = getString(R.string.min_gross_salary_error)
                }
                else -> {
                    calculateTax()
                }
            }
        }

        taxFySpinner.onItemSelectedListener = this

        ageSpinner.onItemSelectedListener = this

        taxModeTxt.setOnCheckedChangeListener { compoundButton, b ->
            compoundButton?.hideKeyboard(requireContext())
            if (b) {
                compoundButton.text = getString(R.string.traditional_tax_info)
            } else {
                compoundButton.text = getString(R.string.new_tax_info)
            }

            loadTaxRegimeDetails()
        }
    }

    private fun calculateTax() {
        val args = TaxCalculatorFragment.setArguments(
            grossSalaryTIE.text.toString().toLong(),
            taxFySpinner.selectedItem.toString(),
            ageSpinner.selectedItemPosition,
            taxModeTxt.isChecked
        )

        val navBuilder = NavOptions.Builder()
        navBuilder.setEnterAnim(R.anim.slide_right_to_left)
            .setExitAnim(R.anim.slide_left_to_right)
            .setPopEnterAnim(R.anim.slide_right_to_left)
            .setPopExitAnim(R.anim.slide_left_to_right)

        NavHostFragment.findNavController(this)
            .navigate(R.id.action_home_to_taxCalculator, args, navBuilder.build())
    }

    private fun loadTaxRegimeDetails() {
        val newTaxRegime = taxModeTxt.text.contains("New", true)
        val ageRang = ageSpinner.selectedItemPosition
        val taxFyYear = taxFySpinner.selectedItemPosition

        taxSlabLayout.removeAllViewsInLayout()
        loadTaxSlabView(newTaxRegime.getTaxRegime(ageRang, taxFyYear))
    }

    private fun loadTaxSlabView(map: HashMap<String, String>) {
        var bgColor = true
        for ((key, value) in map) {

            val child: View =
                layoutInflater.inflate(R.layout.layout_tax_slab_content, taxSlabLayout, false)
            child.findViewById<TextView>(R.id.incomeSlabTxt).text = key
            child.findViewById<TextView>(R.id.rateOfTaxTxt).text = value

            takeIf { bgColor }
                ?.let {
                    child.setBackgroundColor(requireActivity().getColor(R.color.colorHalfWhite))
                    bgColor = false
                } ?: let { bgColor = true }

            taxSlabLayout.addView(child)
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        loadTaxRegimeDetails()
        p1?.hideKeyboard(requireContext())
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }
}