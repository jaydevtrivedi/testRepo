package com.example.myapplication

import android.telephony.PhoneStateListener

import android.content.Context

import android.telephony.TelephonyManager

import android.content.Intent

import android.content.BroadcastReceiver
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi


class ServiceReceiver : BroadcastReceiver() {
    var telephony: TelephonyManager? = null

    lateinit var callStateListener : Any

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("Jaydev", "receiver onReceive")
//        val extras = intent.extras
//        if(extras!=null){
//            val state = extras.getString(TelephonyManager.EXTRA_STATE)
//            when (state) {
//                "OFFHOOK" -> {
//                    Log.d("Jaydev", "OFFHOOK")
//                }
//                "IDLE" -> {
//                    Log.d("Jaydev", "IDLE")
//                }
//            }
//        }
        telephony = context
            .getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S) {
            callStateListener = TelephonyCallBackImpl()
            telephony?.registerTelephonyCallback(DirectExecutor(),callStateListener as TelephonyCallBackImpl)
        } else {
            val phoneListener = PhoneListener(context)
            telephony!!.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE)
        }
    }

    fun onDestroy() {
        Log.d("Jaydev", "receiver onDestroy")

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S) {
            telephony?.unregisterTelephonyCallback(callStateListener as TelephonyCallBackImpl)
        } else {
            telephony!!.listen(null, PhoneStateListener.LISTEN_NONE)
        }
    }
}