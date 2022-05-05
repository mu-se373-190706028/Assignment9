package com.example.assigment9

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import com.example.assigment9.databinding.ActivityMainBinding

import java.io.File
import java.io.IOException
import java.util.*

open class PhotoActivity : AppCompatActivity() {

    var choosen = 0

    var REQUEST_CODE = 200


    override fun onCreate(savedInstanceState: Bundle?) {
        val binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.takePictureButton.setOnClickListener {
            choosen = 0
            REQUEST_CODE = 200
            capturePhoto()

        }
        binding.openGalleryButton.setOnClickListener {
            choosen = 1
            REQUEST_CODE = 100
            openGalleryForImage()

        }

        binding.takeVideoButton.setOnClickListener {
            startActivity(Intent(this,VideoActivity::class.java))
            this.finish()
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val imageView = findViewById<ImageView>(R.id.imageView)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE && data != null) {

            if (choosen == 0) {
                MediaStore.Images.Media.insertImage(
                    contentResolver,
                    data.extras?.get("data") as Bitmap,
                    "epic",
                    "epic"
                )
            } else {

                imageView.setImageURI(data?.data)
            }
        }
    }

    fun capturePhoto() {

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, REQUEST_CODE)
    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }

/*

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            val imageView = findViewById<ImageView>(R.id.imageView)
            imageView.setImageBitmap(imageBitmap)
        }
    }
    val REQUEST_IMAGE_CAPTURE = 1
    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {


                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "com.example.android.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    lateinit var currentPhotoPath: String

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {

            currentPhotoPath = absolutePath
        }
    }
*/
}