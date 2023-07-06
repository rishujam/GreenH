package com.ev.greenh.ui

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.renderscript.*
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ev.greenh.databinding.FragmentScannerBinding
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStreamReader
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.FileChannel


/*
 * Created by Sudhanshu Kumar on 07/05/23.
 */

class ScannerFragment : Fragment() {

    companion object {
        private const val PICK_IMAGE_CODE = 100
        private val hashDisease = hashMapOf<Int, String>(
            0 to "Apple__Apple_scab",
            1 to "Apple_Black_rot",
            2 to "Apple_Cedar_apple_rust",
            3 to "Apple_healthy",
            4 to "Blueberry_healthy",
            5 to "Cherry_Powdery_mildew",
            6 to "Cherry_healthy",
            7 to "Corn_Cercospora_leaf_spot Gray_leaf_spot",
            8 to "Corn_Common_rust",
            9 to "Corn_Northern_Leaf_Blight",
            10 to "Corn_healthy",
            11 to "Grape_Black_rot",
            12 to "Grape_Esca(Black_Measles)",
            13 to "Grape__Leaf_blight(Isariopsis_Leaf_Spot)",
            14 to "Grape__healthy",
            15 to "Orange_Haunglongbing(Citrus_greening)",
            16 to "Peach__Bacterial_spot",
            17 to "Peach___healthy",
            18 to "Pepper,bell__Bacterial_spot",
            19 to "Pepper,bell__healthy",
            20 to "Potato___Early_blight",
            21 to "Potato___Late_blight",
            22 to "Potato___healthy",
            23 to "Raspberry___healthy",
            24 to "Soybean___healthy",
            25 to "Squash___Powdery_mildew",
            26 to "Strawberry___Leaf_scorch",
            27 to "Strawberry___healthy",
            28 to "Tomato___Bacterial_spot",
            29 to "Tomato___Early_blight",
            30 to "Tomato___Late_blight",
            31 to "Tomato___Leaf_Mold",
            32 to "Tomato___Septoria_leaf_spot",
            33 to "Tomato___Spider_mites Two-spotted_spider_mite",
            34 to "Tomato___Target_Spot",
            35 to "Tomato___Tomato_Yellow_Leaf_Curl_Virus",
            36 to "Tomato___Tomato_mosaic_virus",
            37 to "Tomato___healthy"
        )
    }

    private var _binding: FragmentScannerBinding? = null
    private val binding get() = _binding
    private var pickedImage: Bitmap? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScannerBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.groupResult?.visibility = View.GONE

        binding?.ivSelectedImage?.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, PICK_IMAGE_CODE)
        }

        binding?.btnScan?.setOnClickListener {
            binding?.pbScanner?.visibility = View.VISIBLE
            context?.applicationContext?.let {
                getResult(it)
            }
        }
    }

    private fun getResult(context: Context) {
        pickedImage?.let {
            val bitmap = Bitmap.createScaledBitmap(it, 256, 256, false)
            val input = ByteBuffer.allocateDirect(1 * 256 * 256 * 3 * 4).order(ByteOrder.nativeOrder())
            for (y in 0 until 256) {
                for (x in 0 until 256) {
                    val px = bitmap.getPixel(x, y)
                    val r = Color.red(px)
                    val g = Color.green(px)
                    val b = Color.blue(px)
                    input.putFloat(r.toFloat())
                    input.putFloat(g.toFloat())
                    input.putFloat(b.toFloat())
                }
            }
            val bufferSize = 1 * 38 * 32 / java.lang.Byte.SIZE
            val modelOutput = ByteBuffer.allocateDirect(bufferSize).order(ByteOrder.nativeOrder())

            val assetManager = context.assets
            val modelFilename = "model.tflite"
            val tfliteModel = assetManager.openFd(modelFilename).use { fileDescriptor ->
                FileInputStream(fileDescriptor.fileDescriptor).channel.map(
                    FileChannel.MapMode.READ_ONLY,
                    fileDescriptor.startOffset,
                    fileDescriptor.declaredLength
                )
            }
            val tfLite = Interpreter(tfliteModel)
            tfLite.run(input, modelOutput)
            modelOutput.rewind()
            val outputProb = hashMapOf<Float, Int>()
            val probabilities = modelOutput.asFloatBuffer()
            var x = 0
            while (x < 38) {
                val value = probabilities.get(x)
                Log.d("RishuTest", "$x: $value")
                outputProb[value] = x
                x++
            }
            tfLite.close()
            val result = outputProb.toSortedMap()
            val confidence1 = result.keys.toList()[37]
            val confidence2 = result.keys.toList()[36]
            val confidence3 = result.keys.toList()[35]

            val conf1Index = result[confidence1]
            val conf2Index = result[confidence2]
            val conf3Index = result[confidence3]

            val disease1 = hashDisease[conf1Index]
            val disease2 = hashDisease[conf2Index]
            val disease3 = hashDisease[conf3Index]

            Log.d("RishuTest", "${disease1}: $confidence1")
            Log.d("RishuTest", "${disease2}: $confidence2")
            Log.d("RishuTest", "${disease3}: $confidence3")

            val outputString = "${disease1}: ${confidence1 * 100}" + "${disease2}: ${confidence2 * 100}" + "${disease3}: ${confidence3 * 100}"
            binding?.tvDisease?.visibility = View.VISIBLE
            binding?.tvDisease?.text = outputString
            binding?.pbScanner?.visibility = View.GONE
        }

    }

    private fun uriToBitmap(uri: Uri): Bitmap? {
        return try {
            val inputStream = context?.contentResolver?.openInputStream(uri)
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE_CODE) {
            pickedImage = data?.data?.let { uriToBitmap(it) }
            binding?.ivSelectedImage?.setImageBitmap(pickedImage)
            binding?.btnSelectImage?.visibility = View.GONE
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}