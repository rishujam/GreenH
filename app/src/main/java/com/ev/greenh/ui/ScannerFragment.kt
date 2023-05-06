package com.ev.greenh.ui

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ev.greenh.databinding.FragmentScannerBinding
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

/*
 * Created by Sudhanshu Kumar on 07/05/23.
 */

class ScannerFragment : Fragment() {

    companion object {
        private const val PICK_IMAGE_CODE = 100
    }

    private var _binding: FragmentScannerBinding? = null
    private val binding get() = _binding
    private var pickedImage: Uri? = null

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

        binding?.ivSelectedImage?.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, PICK_IMAGE_CODE)
        }

        binding?.btnScan?.setOnClickListener {
            pickedImage?.let { notNullPickedImage ->
                val bitmap = MediaStore.Images.Media.getBitmap(
                    activity?.contentResolver,
                    Uri.parse(notNullPickedImage.toString())
                )
                val inputData = FloatArray(256 * 256 * 3)
                var pixelIndex = 0
                for (y in 0..225) {
                    for (x in 0..225) {
                        val pixel: Int = bitmap.getPixel(x, y)
                        val r: Float = Color.red(pixel) / 255.0f
                        val g: Float = Color.green(pixel) / 255.0f
                        val b: Float = Color.blue(pixel) / 255.0f
                        inputData[pixelIndex++] = r
                        inputData[pixelIndex++] = g
                        inputData[pixelIndex++] = b
                    }
                }
                loadModelFile()?.let {
                    val interpreter = Interpreter(it)
                    val outputData = FloatArray(38)
                    interpreter.run(inputData, outputData)
                    Log.d("rishu", outputData.toString())
                }
            }

        }
    }

    private fun loadModelFile(): MappedByteBuffer? {
        val fileDescriptor = context?.assets?.openFd("model.tflite")
        val inputStream = FileInputStream(fileDescriptor?.fileDescriptor)
        val fileChannel: FileChannel = inputStream.channel
        fileDescriptor?.let {
            val startOffset = it.startOffset
            val declaredLength = it.declaredLength
            return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
        }
        return null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE_CODE) {
            pickedImage = data?.data
            binding?.ivSelectedImage?.setImageURI(pickedImage)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}