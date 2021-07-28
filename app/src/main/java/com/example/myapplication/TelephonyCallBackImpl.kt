package com.example.myapplication

import android.os.Build
import android.telephony.TelephonyCallback
import android.telephony.TelephonyManager
import android.util.Log
import androidx.annotation.RequiresApi
import java.util.concurrent.Executor

@RequiresApi(Build.VERSION_CODES.S)
class TelephonyCallBackImpl : TelephonyCallback(), TelephonyCallback.CallStateListener {

    override fun onCallStateChanged(state: Int) {
        when (state) {
            TelephonyManager.CALL_STATE_IDLE -> {
                Log.d("Jaydev", "telcallback IDLE")
            }
            TelephonyManager.CALL_STATE_OFFHOOK -> {
                Log.d("Jaydev", "telcallback OFFHOOK")
            }
            TelephonyManager.CALL_STATE_RINGING -> {
                Log.d("Jaydev", "telcallback RINGING")
            }
        }
    }
}

class DirectExecutor : Executor {
    override fun execute(r: Runnable) {
        r.run()
    }
}