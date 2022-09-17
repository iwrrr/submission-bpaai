package id.hwaryun.story.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat

const val REQUEST_CODE_PERMISSION = 100

fun checkPermissions(activity: Activity, context: Context, initBottomSheet: () -> Unit) {
    if (checkPermissionsIsGranted(
            activity,
            Manifest.permission.CAMERA,
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
            REQUEST_CODE_PERMISSION,
            context
        )
    ) {
        initBottomSheet.invoke()
    }
}

fun checkPermissionsIsGranted(
    activity: Activity,
    permission: String,
    permissions: Array<String>,
    requestCode: Int,
    context: Context
): Boolean {
    val permissionCheck = ActivityCompat.checkSelfPermission(activity, permission)
    return if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
            showPermissionDeniedDialog(activity, context)
        } else {
            ActivityCompat.requestPermissions(activity, permissions, requestCode)
        }
        false
    } else {
        true
    }
}

fun showPermissionDeniedDialog(activity: Activity, context: Context) {
    AlertDialog.Builder(context)
        .setTitle("Permission Denied")
        .setMessage("Permission is denied, please allow permissions from App Settings.")
        .setPositiveButton("Pengaturan") { _, _ -> openAppSettings(activity) }
        .setNegativeButton("Batal") { dialog, _ -> dialog.cancel() }
        .show()
}

fun openAppSettings(activity: Activity) {
    val intent = Intent()
    val uri = Uri.fromParts("package", activity.packageName, null)

    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
    intent.data = uri

    activity.startActivity(intent)
}