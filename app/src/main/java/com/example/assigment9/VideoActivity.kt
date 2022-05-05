package com.example.assigment9

import android.Manifest
import android.app.Activity
import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View

import android.widget.Toast
import android.widget.VideoView

import com.example.assigment9.databinding.ActivityVideoBinding
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

class VideoActivity : AppCompatActivity() {

/*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.takeVideoButton.setOnClickListener {
            dispatchTakeVideoIntent()
        }


    }

    val REQUEST_VIDEO_CAPTURE = 1

    private fun dispatchTakeVideoIntent() {
        Intent(MediaStore.ACTION_VIDEO_CAPTURE).also { takeVideoIntent ->
            takeVideoIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE)
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val videoView = findViewById<VideoView>(R.id.videoView)
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            val videoUri: Uri = data?.data!!
            videoView.setVideoURI(videoUri)
        }
    }*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestMultiplePermissions()


        binding.takeVideoButton.setOnClickListener {
            takeVideoFromCamera()
        }
        binding.openGalleryButton.setOnClickListener {
            chooseVideoFromGallery()
        }
        binding.takePhotoButton.setOnClickListener {
            startActivity(Intent(this,PhotoActivity::class.java))
            this.finish()
        }


    }

    private val GALLERY = 1
    private val CAMERA = 2

    fun chooseVideoFromGallery() {
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        )

        startActivityForResult(galleryIntent, GALLERY)
    }

    private fun takeVideoFromCamera() {
        val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        startActivityForResult(intent, CAMERA)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        var videoView = findViewById(R.id.videoView) as VideoView
        videoView.visibility= View.VISIBLE
        if (resultCode == Activity.RESULT_CANCELED) {
            return
        }
        if (requestCode == GALLERY) {

            if (data != null) {
                val contentURI = data!!.data
                videoView.setVideoURI(contentURI)
                videoView.requestFocus()
                videoView.start()

            }

        } else if (requestCode == CAMERA) {

            val contentURI = data!!.data

            videoView.setVideoURI(contentURI)
            videoView.requestFocus()
            videoView.start()
        }
    }


    private fun requestMultiplePermissions() {
        Dexter.withActivity(this)
            .withPermissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {

                    if (report.areAllPermissionsGranted()) {
                        Toast.makeText(
                            applicationContext,
                            "All permissions are granted by user!",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest>,
                    token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }
            }).withErrorListener {
                Toast.makeText(
                    applicationContext,
                    "Some Error! ",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .onSameThread()
            .check()
    }


}