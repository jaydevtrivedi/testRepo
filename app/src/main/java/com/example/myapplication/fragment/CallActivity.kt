package com.example.myapplication.fragment

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.myapplication.R
import com.example.myapplication.fragment.ui.main.CallFragment

class CallActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.call_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, CallFragment.newInstance())
                .commitNow()
        }
    }


//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if(requestCode == 1001){
//            Log.d("Jaydev","requestCode + $requestCode")
//            Toast.makeText(this,"made call + $resultCode", Toast.LENGTH_LONG).show()
//        }
//        super.onActivityResult(requestCode, resultCode, data)
//    }
}