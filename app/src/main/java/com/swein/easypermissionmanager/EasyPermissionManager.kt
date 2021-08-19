package com.swein.easypermissionmanager

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import java.util.*

/**
 * must declare this before onCreate()
 * private val permissionManager = EasyPermissionManager(this)
 */
class EasyPermissionManager(private val componentActivity: ComponentActivity) {

    private var runnable: Runnable? = null

    private val permissionNotGrantedList = mutableListOf<String>()

    private var title = ""
    private var message = ""
    private var positiveButtonTitle = ""

    private var activityPermissionResult: ActivityResultLauncher<Intent>
    private var activityPermission: ActivityResultLauncher<Array<String>>

    init {

        activityPermissionResult = registerActivityResult()
        activityPermission = registerPermission()

    }

    private fun registerPermission(): ActivityResultLauncher<Array<String>> {

        return componentActivity.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {

            val deniedPermissionList = mutableListOf<String>()

            it.entries.forEach { permissionResultMap ->

                val permission = permissionResultMap.key
                val grantResult = permissionResultMap.value

                if(!grantResult) {

                    if (ActivityCompat.shouldShowRequestPermissionRationale(componentActivity, permission)) {
                        // if users denied permission, should request again
                        deniedPermissionList.add(permission)
                    }
                    else {
                        // if users denied permission twice, than go to app setting page
                        AlertDialog.Builder(componentActivity).apply {

                            this.setTitle(title)
                            this.setMessage(message)
                            this.setPositiveButton(positiveButtonTitle) { _: DialogInterface?, _: Int ->

                                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                val uri = Uri.fromParts("package", componentActivity.packageName, null)
                                intent.data = uri
                                activityPermissionResult.launch(intent)
                            }
                            this.create()
                            this.show()
                        }

                        return@registerForActivityResult
                    }

                }
            }

            if (deniedPermissionList.isEmpty()) {
                runnable?.run()
            }
        }
    }

    private fun registerActivityResult(): ActivityResultLauncher<Intent> {

        return componentActivity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->

            val deniedPermissionList = mutableListOf<String>()

            if (result.resultCode == Activity.RESULT_CANCELED) {

                for (i in permissionNotGrantedList.indices) {

                    val grantResult = ActivityCompat.checkSelfPermission(componentActivity, permissionNotGrantedList[i])
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        deniedPermissionList.add(permissionNotGrantedList[i])
                    }
                }
            }

            if (deniedPermissionList.isEmpty()) {
                runnable?.run()
                permissionNotGrantedList.clear()
            }

        }
    }

    fun requestPermission(
        permissionDialogTitle: String,
        permissionDialogMessage: String,
        permissionDialogPositiveButtonTitle: String,
        permissions: Array<String>,
        runnableAfterPermissionGranted: Runnable? = null
    ) {

        permissionNotGrantedList.clear()

        for (i in permissions.indices) {

            if (ActivityCompat.checkSelfPermission(componentActivity, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                // if permission is not granted
                permissionNotGrantedList.add(permissions[i])
            }
        }

        if (permissionNotGrantedList.isNotEmpty()) {

            title = permissionDialogTitle
            message = permissionDialogMessage
            positiveButtonTitle = permissionDialogPositiveButtonTitle

            // save the context and runnable
            runnable = runnableAfterPermissionGranted

            activityPermission.launch(permissions)
        }
        else {
            // if all of permissions is granted
            runnableAfterPermissionGranted?.run()
        }
    }

}