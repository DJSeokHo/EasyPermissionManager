import android.widget.Toast
import com.swein.easypermissionmanager.EasyPermissionManager

//package com.swein.easypermissionmanager.demo
//
//import android.Manifest
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.util.Log
//import android.widget.Button
//import com.swein.easypermissionmanager.EasyPermissionManager
//import com.swein.easypermissionmanager.R
//
//class EasyPermissionManagerDemoActivity : AppCompatActivity() {
//
//    private val permissionManager = EasyPermissionManager(this)
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_easy_permission_manager_demo)
//
//        findViewById<Button>(R.id.button).setOnClickListener {
//
//            permissionManager.requestPermission(
//                "Permission",
//                "permissions are necessary",
//                "setting",
//                arrayOf(Manifest.permission.CAMERA,
//                Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.ACCESS_COARSE_LOCATION,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                Manifest.permission.READ_EXTERNAL_STORAGE)
//            ) {
//
//                yourMethodShouldRunAfterAllPermissionGranted()
//            }
//
//        }
//    }
//
//    private fun yourMethodShouldRunAfterAllPermissionGranted() {
//        Toast.makeText(this, "All permission is ok, go go go", Toast.LENGTH_SHORT).show()
//    }
//}