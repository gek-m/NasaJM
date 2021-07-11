package com.example.nasajm.storages

import android.content.Context
import com.example.nasajm.R

class ThemeStorage(private val context: Context) {

    private val sharedPreferences = context.getSharedPreferences(
        context.getString(R.string.shared_preference_theme),
        Context.MODE_PRIVATE
    )

    var themeId: Int
        get() = sharedPreferences.getInt(context.getString(R.string.key_theme_id), 0)
        set(value) {
            sharedPreferences
                .edit()
                .putInt(context.getString(R.string.key_theme_id), value)
                .apply()
        }
}