package com.example.cryptowallet.util.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Message

const val NETWORK_CONNECTED_CODE = 1
const val NETWORK_ERROR_CODE = 2

class NetworkChangeReceiver(private val handler: Handler) : BroadcastReceiver() {
    private var isDisConnected = false
    override fun onReceive(context: Context, intent: Intent?) {
        try {
            if (isDisConnected && NetworkUtil.isConnected(context)) {
                sendMessage(NETWORK_CONNECTED_CODE)
            } else if (NetworkUtil.isNotConnected(context)) {
                isDisConnected = true
                sendMessage(NETWORK_ERROR_CODE)
            }
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
    }

    private fun sendMessage(status: Int) {
        val message = Message()
        message.arg1 = status
        handler.sendMessage(message)
    }
}