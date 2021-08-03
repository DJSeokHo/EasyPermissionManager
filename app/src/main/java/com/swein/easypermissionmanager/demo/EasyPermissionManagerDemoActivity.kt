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
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_easy_permission_manager_demo)
//
//        findViewById<Button>(R.id.button).setOnClickListener {
//
//            EasyPermissionManager.requestPermission(this, 9999,
//                Manifest.permission.CAMERA,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                Manifest.permission.READ_EXTERNAL_STORAGE
//            ) {
//                Log.d("EasyPermissionManager", "go go go")
//            }
//
//        }
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        EasyPermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        EasyPermissionManager.onActivityResult(requestCode, resultCode)
//        super.onActivityResult(requestCode, resultCode, data)
//    }
//}