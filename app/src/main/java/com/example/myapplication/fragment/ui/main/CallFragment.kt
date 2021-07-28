package com.example.myapplication.fragment.ui.main

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.example.myapplication.R
import com.example.myapplication.ServiceReceiver

class CallFragment : Fragment() {

    var serviceReceiver : ServiceReceiver? = ServiceReceiver()

    lateinit var observer: MyLifecycleObserver

    companion object {
        fun newInstance() = CallFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
        requestPermission()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observer = MyLifecycleObserver(requireActivity().activityResultRegistry)
        lifecycle.addObserver(observer)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val selectButton = view.findViewById<Button>(R.id.makeACall)

        selectButton.setOnClickListener {
            requireActivity().registerReceiver(serviceReceiver, IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED))
            // Open the activity to select an image
            observer.makeCall()
        }
    }

    // All Functions call
    override fun onResume() {
        Log.d("Jaydev","Call fragment onResume")
        super.onResume()
    }

    override fun onDestroy() {
        Log.d("Jaydev","Call fragment onDestroy")
        serviceReceiver?.onDestroy()
        requireActivity().unregisterReceiver(serviceReceiver)
        serviceReceiver = null
        super.onDestroy()
    }

    private fun requestPermission(){
        if(ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_GRANTED){
            val intentFilter = IntentFilter()
            intentFilter.addAction("android.intent.action.ACTION_PHONE_STATE_CHANGED")
        }
    }

    // All Functions call
}

//  Lifecycle observer class
class MyLifecycleObserver(private val registry: ActivityResultRegistry) : DefaultLifecycleObserver {
    lateinit var mStartForResult : ActivityResultLauncher<Intent>

    override fun onCreate(owner: LifecycleOwner) {
        mStartForResult = registry.register("1001", owner, ActivityResultContracts.StartActivityForResult()){
            result ->
            //  Todo get something that confirms this call
                val data = result.data?.data
                // Handle the Intent
                Log.d("jaydev", "data $data")
        }
    }

    fun makeCall() {
        Log.d("jaydev", "making a call")
        // call this on button click
        val phone = "+61451300567"
        val intent = Intent(Intent.ACTION_CALL)
        intent.setData(Uri.parse("tel:" +phone))
        mStartForResult.launch(intent)
    }
}