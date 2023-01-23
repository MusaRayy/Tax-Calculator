package com.musarayy.taxcalculator.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.musarayy.taxcalculator.utils.DataCache
import com.musarayy.taxcalculator.utils.OSALogger
import org.koin.android.ext.android.inject

abstract class BaseFragment() : Fragment() {

    protected val dataCache: DataCache by inject()
    protected val logger: OSALogger by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, vgrp: ViewGroup?, bundle: Bundle?): View {
        val view = inflater.inflate(getLayout(), vgrp, false)

        initViews(view)
        onClick()
        return view
    }


    abstract fun getLayout(): Int
    abstract fun initViews(view: View)
    abstract fun onClick()
}