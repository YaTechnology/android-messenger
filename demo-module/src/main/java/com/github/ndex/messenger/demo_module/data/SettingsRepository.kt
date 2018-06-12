package com.github.ndex.messenger.demo_module.data

import android.content.Context
import android.content.Context.MODE_PRIVATE


private const val PREFS_NAME = "settings"
private const val PREFS_LOGIN = "login"

class SettingsRepository(context: Context) {
    private val prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
    private var login: String = ""

    fun requestLogin(): String =
            if (login != "") login else prefs.getString(PREFS_LOGIN, "")

    fun storeLogin(login: String) {
        this.login = login
        val ed = prefs.edit()
        ed.putString(PREFS_LOGIN, login)
        ed.apply()
    }

}