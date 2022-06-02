package com.example.cryptowallet.util

import android.content.Context
import android.content.SharedPreferences
class SharedPreference  constructor(
    context: Context
) {

    companion object {
        const val SHARED_PREF_NAME = "Conhako_share_ref"
        const val ENCRYPT_VALUE = "ENCRYPT_VALUE"
        const val ENCRYPT_KEY = "ENCRYPT_KEY"
    }

    private val sharedPreferences by lazy(LazyThreadSafetyMode.NONE) {
        context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
    }
    var encryptValue: String
        get() = sharedPreferences.getString(ENCRYPT_VALUE, "")?: ""
        set(value) = sharedPreferences.putOrRemoveString(ENCRYPT_VALUE, value)

    var encryptKey: String
        get() = sharedPreferences.getString(ENCRYPT_KEY, "")?: ""
        set(value) = sharedPreferences.putOrRemoveString(ENCRYPT_KEY, value)

    fun clearData() {
        sharedPreferences.edit().remove(ENCRYPT_VALUE).apply()
        sharedPreferences.edit().remove(ENCRYPT_VALUE).apply()
    }

    private inline fun SharedPreferences.put(body: SharedPreferences.Editor.() -> Unit) {
        this.edit().apply {
            body()
            apply()
        }
    }

    private fun SharedPreferences.putOrRemoveString(key: String, value: String?) {
        if (value == null)
            this.edit().remove(key).apply()
        else
            this.edit().putString(key, value).apply()
    }

}