package com.example.auth.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.auth.data.remotesource.PhoneAuthApi
import com.example.auth.data.repositoryimpl.PhoneAuthRepoImpl
import com.example.auth.databinding.TestFragmentBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/*
 * Created by Sudhanshu Kumar on 18/01/24.
 */

class TestFragment : Fragment() {

    private var _binding: TestFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var verifyId: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = TestFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repo = PhoneAuthRepoImpl(PhoneAuthApi.api)
        binding.btnSend.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                repo.sendCredentials("+918076861086").collect {
                    Log.d("RishuTest", "data: ${it?.Status}")
                    Log.d("RishuTest", "data: ${it?.Details}")
                }
            }
        }

        binding.btnVerify.setOnClickListener {
            val otp = binding.etOtp.text.toString()
            lifecycleScope.launch(Dispatchers.IO) {
                val res = repo.verifyCredentials(verifyId, otp)
                Log.d("RishuTest", "verify result: $res")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}