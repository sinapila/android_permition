package com.example.myapplication

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myapplication.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private var wifiManager: WifiManager? = null

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        wifiManager = applicationContext.
        getSystemService(Context.WIFI_SERVICE) as WifiManager?

        val bluetoothManager: BluetoothManager =
            ContextCompat.getSystemService(this, BluetoothManager::class.java)!!
        val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.getAdapter()

        if (bluetoothAdapter == null) {
            Toast.makeText(this@MainActivity, "Bluetooth is not find", Toast.LENGTH_SHORT).show()
        }

        binding.buttonBlutooth.setOnClickListener {
            if (bluetoothAdapter?.isEnabled == false) {

                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                val REQUEST_ENABLE_BT = 1

                ActivityCompat.startActivityForResult(
                    this@MainActivity,
                    enableBtIntent,
                    REQUEST_ENABLE_BT,
                    Bundle.EMPTY
                )


            }
        }

        binding.buttonWifi.setOnClickListener{
            wifiManager!!.setWifiEnabled(true)
        }

        binding.buttonMap.setOnClickListener {
            checkPermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION, 103)
        }


    }

    private fun checkPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(this@MainActivity, permission) == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(this@MainActivity, arrayOf(permission), requestCode)
        } else {
            Toast.makeText(this@MainActivity, "Permission already granted", Toast.LENGTH_SHORT).show()
        }
    }

}