package com.example.nasajm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nasajm.storages.ThemeStorage
import com.example.nasajm.ui.main.MainFragment

class MainActivity() : AppCompatActivity(R.layout.main_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }

    private fun init() {
        val themeStorage = ThemeStorage(this.application)
        themeStorage.themeId?.let {
            when (it) {
                0 -> setTheme(R.style.Theme_NasaJM)
                1 -> setTheme(R.style.AppTheme)
            }
        }
    }
}