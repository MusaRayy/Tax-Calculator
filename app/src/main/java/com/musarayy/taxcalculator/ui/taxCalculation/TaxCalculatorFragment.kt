package com.musarayy.taxcalculator.ui.taxCalculation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.musarayy.taxcalculator.R
import com.musarayy.taxcalculator.ui.base.BaseFragment
import com.musarayy.taxcalculator.utils.getTaxSlabPercentage
import java.text.NumberFormat
import java.util.*


private const val ARG_TAX_FY = "taxFinancialYear"
private const val ARG_AGE_GROSS_CTC = "grossSalary"
private const val ARG_AGE_TYPE = "ageType"
private const val ARG_IS_OLD_TAX_STRUCTURE = "isOldTaxStructure"

class TaxCalculatorFragment : BaseFragment() {

    private var ageType: Int = 0
    private var grossCTC: Long = 0
    private var taxFinancialYear: String? = null
    private var isOldTaxStructure: Boolean = true

    private lateinit var pfTxt: TextView
    private lateinit var ctcTxt: TextView
    private lateinit var hraTxt: TextView
    private lateinit var profTaxTxt: TextView
    private lateinit var basicPayTxt: TextView
    private lateinit var takeHomeTxt: TextView
    private lateinit var splAllowTxt: TextView
    private lateinit var totalMonthlyPayTxt: TextView
    private lateinit var taxableYeaarIncomeTxt: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            ageType = it.getInt(ARG_AGE_TYPE)
            grossCTC = it.getLong(ARG_AGE_GROSS_CTC, 0)
            taxFinancialYear = it.getString(ARG_TAX_FY, "")
            isOldTaxStructure = it.getBoolean(ARG_IS_OLD_TAX_STRUCTURE)
        }
    }

    override fun getLayout(): Int {
        return R.layout.fragment_tax_calculator
    }

    @SuppressLint("SetTextI18n")
    override fun initViews(view: View) {

        pfTxt = view.findViewById(R.id.pfTxt)
        ctcTxt = view.findViewById(R.id.ctcTxt)
        hraTxt = view.findViewById(R.id.hraTxt)
        profTaxTxt = view.findViewById(R.id.profTaxTxt)
        basicPayTxt = view.findViewById(R.id.basicPayTxt)
        takeHomeTxt = view.findViewById(R.id.takeHomeTxt)
        splAllowTxt = view.findViewById(R.id.splAllowTxt)
        totalMonthlyPayTxt = view.findViewById(R.id.totalMonthlyPayTxt)
        taxableYeaarIncomeTxt = view.findViewById(R.id.taxableYeaarIncomeTxt)


        // loading total CTC in rupees format
        val format: NumberFormat = NumberFormat.getCurrencyInstance()
        format.maximumFractionDigits = 0
        format.currency = Currency.getInstance("INR")

        val monthPay = grossCTC / 12
        ctcTxt.text = "${format.format(grossCTC)}.00"

        val takeHome = monthPay - ((monthPay * 0.4) * 0.12) - 208

        pfTxt.text = "- ${format.format(((monthPay * 0.4) * 0.12))}"
        hraTxt.text = format.format((monthPay * 0.2))
        profTaxTxt.text = "- ${format.format(208)}"
        basicPayTxt.text = format.format((monthPay * 0.4))
        splAllowTxt.text = format.format((monthPay * 0.4))
        totalMonthlyPayTxt.text = format.format((monthPay))
        takeHomeTxt.text = format.format(takeHome)

        taxableYeaarIncomeTxt.text =
            String.format(
                taxableYeaarIncomeTxt.text.toString(), format.format(takeHome * 12),
                (takeHome * 12).toLong().getTaxSlabPercentage(isOldTaxStructure)
            )
    }

    override fun onClick() {

    }

    companion object {
        @JvmStatic
        fun setArguments(
            grossCTC: Long,
            taxFinancialYear: String,
            ageType: Int,
            isOldTaxStructure: Boolean
        ) =
            Bundle().apply {
                putInt(ARG_AGE_TYPE, ageType)
                putLong(ARG_AGE_GROSS_CTC, grossCTC)
                putString(ARG_TAX_FY, taxFinancialYear)
                putBoolean(ARG_IS_OLD_TAX_STRUCTURE, isOldTaxStructure)
            }
    }
}