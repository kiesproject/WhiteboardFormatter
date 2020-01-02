package com.example.whiteboardformatter

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        const val CAMERA_REQUEST_CODE = 1
        const val CAMERA_PERMISSION_REQUEST_CODE = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()

        btnLaunchCamera.setOnClickListener {
            // カメラ機能を実装したアプリが存在するかチェック
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).resolveActivity(packageManager)?.let {
                if (checkCameraPermission()) {
                    takePicture()
                } else {
                    grantCameraPermission()
                }
            } ?: Toast.makeText(this, "カメラを扱うアプリがありません", Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val image = data?.extras?.get("data")?.let {
                cameraImage.setImageBitmap(it as Bitmap)
            }
        }
    }

    private fun takePicture() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            addCategory(Intent.CATEGORY_DEFAULT)
        }

        startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }

    private fun checkCameraPermission() = PackageManager.PERMISSION_GRANTED ==
            ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA)


    private fun grantCameraPermission() =
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.CAMERA),
            CAMERA_PERMISSION_REQUEST_CODE)


    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<out String>,
                                            grantResults: IntArray) {
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePicture()
            }
        }
    }
}
