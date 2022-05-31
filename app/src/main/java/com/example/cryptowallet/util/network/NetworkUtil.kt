package com.example.cryptowallet.util.network

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager


object NetworkUtil {

    @SuppressLint("MissingPermission")
    @JvmStatic
    fun isConnected(context: Context): Boolean {
        val cm: ConnectivityManager? =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm?.activeNetworkInfo
        return null != activeNetwork && activeNetwork.isConnected
    }

    @JvmStatic
    fun isNotConnected(context: Context) = !isConnected(context)
}