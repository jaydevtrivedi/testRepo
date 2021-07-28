package com.example.myapplication

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    var serviceReceiver : ServiceReceiver? = ServiceReceiver()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.makeACall)
        startACall(button)

        requestPermission()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 1001){
            Log.d("Jaydev","requestCode + $requestCode")
            Toast.makeText(this,"made call + $resultCode", Toast.LENGTH_LONG).show()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onResume() {
        this.registerReceiver(serviceReceiver, IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED))
        super.onResume()
    }

    override fun onDestroy() {
        Log.d("Jaydev","onDestroy")
        serviceReceiver?.onDestroy()
        this.unregisterReceiver(serviceReceiver)
        serviceReceiver = null
        super.onDestroy()
    }

    private fun requestPermission(){
        if(ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CALL_PHONE
        ) == PackageManager.PERMISSION_GRANTED){
            val intentFilter = IntentFilter()
            intentFilter.addAction("android.intent.action.ACTION_PHONE_STATE_CHANGED")
        }
    }

    private fun startACall(button: Button){
        val phone = "+61451300567"
        val intent = Intent(Intent.ACTION_CALL)
        intent.setData(Uri.parse("tel:" +phone))
        button.setOnClickListener( View.OnClickListener {
            startActivityForResult(intent,1001)
        })
    }
}
