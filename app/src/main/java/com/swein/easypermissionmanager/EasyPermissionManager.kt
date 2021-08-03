package com.swein.easypermissionmanager

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import java.lang.ref.WeakReference
import java.util.*

object EasyPermissionManager {

    private var activityWeakReference: WeakReference<Activity>? = null
    private var runnable: Runnable? = null

    private val permissionNotGrantedList = mutableListOf<String>()

    private var permissionRequestCode = 6513

    private var title = ""
    private var message = ""
    private var positiveButtonTitle = ""

    fun requestPermission(
        activity: Activity,
        requestCode: Int,
        vararg permission: String,
        permissionDialogTitle: String = "Permission",
        permissionDialogMessage: String = "Request permissions",
        permissionDialogPositiveButtonTitle: String = "Confirm",
        runnableAfterPermissionGranted: Runnable? = null
    ) {

        permissionNotGrantedList.clear()

        for (i in permission.indices) {

            if (ActivityCompat.checkSelfPermission(activity, permission[i]) != PackageManager.PERMISSION_GRANTED) {
                // if permission is not granted
                permissionNotGrantedList.add(permission[i])
            }
        }

        if (permissionNotGrantedList.isNotEmpty()) {

            title = permissionDialogTitle
            message = permissionDialogMessage
            positiveButtonTitle = permissionDialogPositiveButtonTitle

            permissionRequestCode = requestCode

            // save the context and runnable
            runnable = runnableAfterPermissionGranted
            activityWeakReference = WeakReference(activity)

            // request permission
            ActivityCompat.requestPermissions(
                activity,
                permissionNotGrantedList.toTypedArray(),
                permissionRequestCode
            )
        }
        else {
            // if all of permissions is granted
            runnableAfterPermissionGranted?.run()
        }
    }

    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        if (requestCode == permissionRequestCode) {

            activityWeakReference?.get()?.let {

                val deniedPermissionList = mutableListOf<String>()

                for (i in grantResults.indices) {

                    val grantResult = grantResults[i]
                    val permission = permissions[i]

                    if(grantResult != PackageManager.PERMISSION_GRANTED) {

                        if (ActivityCompat.shouldShowRequestPermissionRationale(it, permission)) {
                            // if users denied permission, should request again
                            deniedPermissionList.add(permission)
                        }
                        else {
                            // if users denied permission twice, than go to app setting page
                            AlertDialog.Builder(it).apply {

                                this.setTitle(title)
                                this.setMessage(message)
                                this.setPositiveButton(positiveButtonTitle) { _: DialogInterface?, _: Int ->
                                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                    val uri = Uri.fromParts("package", it.packageName, null)
                                    intent.data = uri
                                    it.startActivityForResult(intent, permissionRequestCode)
                                }
                                this.create()
                                this.show()
                            }

                            return@let
                        }

                    }
                }

                if (deniedPermissionList.isEmpty()) {
                    runnable?.run()
                }

                activityWeakReference?.clear()

            }

        }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int) {

        if (requestCode == permissionRequestCode) {

            activityWeakReference?.get()?.let {

                val deniedPermissionList = mutableListOf<String>()

                if (resultCode == Activity.RESULT_CANCELED) {

                    for (i in permissionNotGrantedList.indices) {

                        val grantResult = ActivityCompat.checkSelfPermission(it, permissionNotGrantedList[i])
                        if (grantResult != PackageManager.PERMISSION_GRANTED) {
                            deniedPermissionList.add(permissionNotGrantedList[i])
                        }
                    }
                }

                if (deniedPermissionList.isEmpty()) {
                    runnable?.run()
                    permissionNotGrantedList.clear()
                }

                activityWeakReference?.clear()

            }
        }
    }
}