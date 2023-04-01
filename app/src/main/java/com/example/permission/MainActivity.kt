package com.example.permission

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton


class MainActivity : AppCompatActivity() {

    private lateinit var btnSinglePermissionHandling: MaterialButton
    private lateinit var btnMultiplePermissionHandling: MaterialButton
    private lateinit var tvResult: TextView

    private companion object {
        private const val TAG = "PERMISSION_TAG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSinglePermissionHandling = findViewById(R.id.btnSinglePermission)
        btnMultiplePermissionHandling = findViewById(R.id.btnMultiplePermission)
        tvResult = findViewById(R.id.tvResult)





        btnSinglePermissionHandling.setOnClickListener {
            val permission = Manifest.permission.CAMERA
            permissionLauncherSingle.launch(permission)

        }

        btnMultiplePermissionHandling.setOnClickListener {
            val permission =
                arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_CONTACTS)
            permissionLauncherMultiple.launch(permission)

        }

    }





    private val permissionLauncherSingle = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        Log.d(TAG, "permissionLauncherSingle: isGranted: $isGranted")

        if (isGranted) {
            singlePermissionGranted()

        } else {

            Log.d(TAG, "permissionLauncherSingle: Permission denied...")
            Toast.makeText(this, "Permission denied...", Toast.LENGTH_SHORT).show()

        }


    }

    private val permissionLauncherMultiple = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        var areAllGranted = true
        for (isGranted in result.values) {
            Log.d(TAG, "permissionLauncherMultiple: isGranted: $isGranted")
            areAllGranted = areAllGranted && isGranted

        }

        if (areAllGranted) {
            multiplePermissionGranted()

        } else {
            Log.d(TAG, "permissionLauncherMultiple: All or some Permissions denied...")
            Toast.makeText(this, "All or some Permissions denied...", Toast.LENGTH_SHORT).show()
        }
    }





    private fun singlePermissionGranted() {
        tvResult.text = "Single permission granted!"
    }

    private fun multiplePermissionGranted() {
        tvResult.text = "Multiple permissions granted!"
    }


}