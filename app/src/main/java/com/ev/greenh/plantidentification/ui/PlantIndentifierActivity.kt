package com.ev.greenh.plantidentification.ui

import android.R.attr.bitmap
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.ev.greenh.databinding.ActivityPlantIndentifierBinding
import com.ev.greenh.plantidentification.data.PlantIdentificationRepo
import com.ev.greenh.plantidentification.data.model.req.AnnotateImageRequest
import com.ev.greenh.plantidentification.data.model.req.Feature
import com.ev.greenh.plantidentification.data.model.req.Image
import com.ev.greenh.plantidentification.data.model.req.ImageAnnotationRequest
import com.google.firebase.auth.GoogleAuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.google.protobuf.ByteString
import java.io.ByteArrayOutputStream
import java.io.IOException

private const val TAG = "PlantIndentifierActivity"

class PlantIndentifierActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlantIndentifierBinding
    private var selectedImageBitmap: Bitmap? = null
    private val apiKey = "564862815535-sck8tefigsebpqn0b23rjh24hmp8njjq.apps.googleusercontent.com"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlantIndentifierBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivSelectedImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            imagePicker.launch(intent)
        }

        binding.btnPush.setOnClickListener {
            if (selectedImageBitmap != null) {
                callApi()
            } else {
                Toast.makeText(this, "Please select an image first.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val imagePicker: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val imageUri = data?.data
            val imageStream = contentResolver.openInputStream(imageUri!!)
            selectedImageBitmap = BitmapFactory.decodeStream(imageStream)
            binding.ivSelectedImage.setImageBitmap(selectedImageBitmap)
        }
    }

    private fun callApi() {
        val repo = PlantIdentificationRepo()
        val image = encodeImageToBase64(selectedImageBitmap!!)
        repo.request(image)
    }

    private fun calll() {
        try {
            val repo = PlantIdentificationRepo()
            val featureList: MutableList<Feature> = ArrayList()
            val labelDetection = Feature(Feature.Type.LABEL_DETECTION, 10, null)
            val textDetection = Feature(Feature.Type.TEXT_DETECTION, 10, null)
            featureList.add(labelDetection)
            featureList.add(textDetection)
            val annotateImageRequest = AnnotateImageRequest(
                Image(encodeImageToBase64(selectedImageBitmap!!), null),
                featureList,
                null
            )

            val request = ImageAnnotationRequest(
                listOf(annotateImageRequest),
                null
            )
            repo.callApi(request)
            Log.d(TAG, "Sending request to Google Cloud")
        } catch (e: Exception) {
            Log.e(TAG, "Request error: " + e.message)
        }
    }

    private fun encodeImageToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

}