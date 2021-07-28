package com.example.myapplication

import android.content.Context
import android.telephony.PhoneStateListener
import android.util.Log

import android.telephony.TelephonyManager
import android.widget.Toast


class PhoneListener(val context: Context) : PhoneStateListener() {

    var phoneRinging = false

    override fun onCallStateChanged(state: Int, phoneNumber: String?) {
        when (state) {
            TelephonyManager.CALL_STATE_IDLE -> {
                Log.d("Jaydev", "Plistener IDLE")
                phoneRinging = false
            }
            TelephonyManager.CALL_STATE_OFFHOOK -> {
                Log.d("Jaydev", "Plistener OFFHOOK")
                phoneRinging = false
            }
            TelephonyManager.CALL_STATE_RINGING -> {
                Log.d("Jaydev", "Plistener RINGING")
                phoneRinging = true
            }
        }
    }
}

