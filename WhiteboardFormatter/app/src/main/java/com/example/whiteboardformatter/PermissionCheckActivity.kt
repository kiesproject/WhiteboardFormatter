package com.example.whiteboardformatter

import android.Manifest
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AlertDialog
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnNeverAskAgain
import permissions.dispatcher.OnPermissionDenied

class PermissionCheckActivity : AppCompatActivity() {

    var isWrtieExternlStorageAllowed = false
    var isCameraAllowed = false
    val REQEST_CODE_MAGIC = 1212

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission_check)
        startPermissionCheck()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, permissions, grantResults) //29行目、参考サイトは"permissions,"なし。
    }

    //以下STRAGE
    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun showWriteExternalStorage() {
        isWrtieExternlStorageAllowed = true
        showWriteExternalStorage() //36,42,48行目共に参考記事は"showCameraWithPermissionCheck()"
    }

    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun onWeiteExternalStrageDenied(){
        isWrtieExternlStorageAllowed = false
        showWriteExternalStorage()
    }

    @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun onWriteExternalStorageNeverAskAgain(){
        isWrtieExternlStorageAllowed = false
        showWriteExternalStorage()
    }

    //以下CAMERA
    @NeedsPermission(Manifest.permission.CAMERA)
    fun showCamera(){
        isCameraAllowed = true
        startNextActivity()
    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    fun onCameraDenied(){
        isCameraAllowed = false
        startNextActivity()
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    fun onCaneraNeverAskAgain(){
        isCameraAllowed = false
        startNextActivity()
    }

    private fun startNextActivity() {

        if ((isCameraAllowed == true) && (isWrtieExternlStorageAllowed == true)) {

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {

            if (!isCameraAllowed)
                Log.d("CAM", "No")
            if (!isWrtieExternlStorageAllowed)
                Log.d("STO", "No")

            AlertDialog.Builder(this)
                .setPositiveButton(android.R.string.cancel) { _, _ ->
                    finish()
                }
                .setNegativeButton(R.string.button_name_app_info) { _, _ ->
                    val uriString = "package:$packageName"
                    val intent =
                        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse(uriString))
                    startActivityForResult(intent, REQEST_CODE_MAGIC)
                }
                .setCancelable(false)
                .setMessage(R.string.alert_dialog_message_for_permission)
                .show()

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)

        if (requestCode == REQEST_CODE_MAGIC){
            startPermissionCheck()
        }
    }

}
