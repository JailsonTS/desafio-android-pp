package com.picpay.desafio.android.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.picpay.desafio.android.R
import com.picpay.desafio.android.util.ViewUtils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ViewUtils.setTema(this, resources.configuration)
        setContentView(R.layout.activity_main)
    }
}