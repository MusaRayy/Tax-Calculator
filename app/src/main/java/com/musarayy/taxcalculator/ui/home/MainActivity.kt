package com.musarayy.taxcalculator.ui.home

import android.content.Intent
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.musarayy.taxcalculator.R
import com.musarayy.taxcalculator.ui.base.BaseActivity
import com.musarayy.taxcalculator.ui.favorite.FavoriteActivity

class MainActivity : BaseActivity() {

    lateinit var mToolbar: Toolbar

    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun initViews() {
        mToolbar = findViewById(R.id.mToolbar)
    }

    override fun onClick() {

        mToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.favorite -> {
                    startActivity(Intent(this, FavoriteActivity::class.java))
                }
            }
            true
        }
    }
}