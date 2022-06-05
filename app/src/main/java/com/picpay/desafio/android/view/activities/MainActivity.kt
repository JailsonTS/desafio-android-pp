package com.picpay.desafio.android.view.activities

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.picpay.desafio.android.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTema()
        setContentView(R.layout.activity_main)

    }

    private fun setTema() {
        val config = resources.configuration
        when (config.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> {
                setTheme(R.style.AppTheme)
            }
            Configuration.UI_MODE_NIGHT_YES -> {
                setTheme(R.style.AppTheme_Dark)
            }
        }
    }
}