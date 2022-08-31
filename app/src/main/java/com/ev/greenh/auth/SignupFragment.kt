package com.ev.greenh.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.ev.greenh.ui.MainActivity
import com.ev.greenh.R
import com.ev.greenh.databinding.FragmentSignupBinding
import com.ev.greenh.models.Profile
import com.ev.greenh.services.FirebaseNotify
import com.ev.greenh.util.Resource
import com.ev.greenh.util.visible
import com.ev.greenh.viewmodels.AuthViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit


class SignupFragment:Fragment() {

    private var _binding: FragmentSignupBinding?=null
    private val binding get() = _binding!!
    private lateinit var viewModel: AuthViewModel
    private lateinit var auth : FirebaseAuth
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var phoneNo:String
    private lateinit var token:String
    lateinit var countdown_timer: CountDownTimer
    var isRunning: Boolean = false
    var time_in_milli_seconds = 0L
    private lateinit var uid:String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding  = FragmentSignupBinding.inflate(inflater,container,false)
        viewModel = (activity as AuthActivity).viewModel
        auth = FirebaseAuth.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        FirebaseNotify.sharedPref = (activity as AuthActivity).getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        FirebaseMessaging.getInstance().token.addOnCompleteListener( OnCompleteListener { task ->
            if(!task.isSuccessful) {
                return@OnCompleteListener
            }
            token= task.result.toString()
            FirebaseNotify.token = token
            Log.e("token",token)
        })

        var verificationI = ""
        binding.progressBar.visible(false)

        viewModel.saveProfileRes.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            when(it){
                is Resource.Success -> {
                    viewModel.saveUIDLocally(uid)
                    viewModel.saveNotifyToken(uid, token,getString(R.string.token))
                    startActivity(Intent(context, MainActivity::class.java))
                    (activity as AuthActivity).finish()
                }
                is Resource.Error ->{
                    Toast.makeText(context, "Error in sign in", Toast.LENGTH_SHORT).show()
                    Log.e("SignupFragment", it.message.toString())
                }
                is Resource.Loading->{
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        })


        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {}

            override fun onVerificationFailed(e: FirebaseException) {
                binding.progressBar.visibility = View.INVISIBLE
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                Log.e("OtpVerifyFailed",e.message.toString())
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                binding.etCode1.requestFocus()
                binding.progressBar.visibility = View.INVISIBLE
                Log.d("TAG","onCodeSent:$verificationId")
                resendToken = token
                binding.etCode1.visibility = View.VISIBLE
                binding.etCode2.visibility = View.VISIBLE
                binding.etCode3.visibility = View.VISIBLE
                binding.etCode4.visibility = View.VISIBLE
                binding.etCode5.visibility = View.VISIBLE
                binding.etCode6.visibility = View.VISIBLE
                binding.resendTimer.visibility  = View.VISIBLE
                binding.btnSignup.visibility = View.GONE
                binding.btnVerifyCode.visibility = View.VISIBLE
                verificationI  = verificationId
                time_in_milli_seconds = 15*1000L
                startTimer(time_in_milli_seconds)
            }
        }

        binding.btnVerifyCode.setOnClickListener {
            val code1 = binding.etCode1.text.toString()
            val code2 = binding.etCode2.text.toString()
            val code3 = binding.etCode3.text.toString()
            val code4 = binding.etCode4.text.toString()
            val code5 = binding.etCode5.text.toString()
            val code6 = binding.etCode6.text.toString()
            if(code1.isNotEmpty() && code2.isNotEmpty() && code3.isNotEmpty() && code4.isNotEmpty() && code5.isNotEmpty() && code6.isNotEmpty()){
                binding.progressBar.visibility = View.VISIBLE
                countdown_timer.cancel()
                val otp = code1 +  code2 + code3 + code4 + code5 + code6
                val credential : PhoneAuthCredential = PhoneAuthProvider.getCredential(
                    verificationI, otp)
                signInWithPhoneAuthCredential(credential)
            }else{
                binding.progressBar.visibility = View.INVISIBLE
                Toast.makeText(context, "INVALID OTP", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnSignup.setOnClickListener {
            val phone = binding.etPhone.text.toString().trim()
            if(phone.length==10){
                phoneNo = phone
                binding.progressBar.visibility = View.VISIBLE
                sendVerificationCode("+91$phone")
            }else{
                Toast.makeText(context, "Please enter a valid number", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnResendCode.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            binding.btnResendCode.visibility = View.GONE
            resendCode()
        }

        binding.etCode1.addTextChangedListener{
            it?.let {
                if(it.toString().length==1){
                    binding.etCode2.requestFocus()
                }
            }
        }

        binding.etCode2.addTextChangedListener{
            it?.let {
                if(it.toString().length==1){
                    binding.etCode3.requestFocus()
                }
            }
        }

        binding.etCode3.addTextChangedListener{
            it?.let {
                if(it.toString().length==1){
                    binding.etCode4.requestFocus()
                }
            }
        }

        binding.etCode4.addTextChangedListener{
            it?.let {
                if(it.toString().length==1){
                    binding.etCode5.requestFocus()
                }
            }
        }

        binding.etCode5.addTextChangedListener{
            it?.let {
                if(it.toString().length==1){
                    binding.etCode6.requestFocus()
                }
            }
        }
    }


    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val uid = auth.currentUser?.uid.toString()
                    this.uid = uid
                    val profile = Profile(phone = phoneNo, uid = uid)
                    viewModel.saveUserProfile(getString(R.string.user_ref),profile)
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        binding.progressBar.visibility = View.INVISIBLE
                        Toast.makeText(context,"Invalid OTP", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    private fun sendVerificationCode(number: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity())// Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun startTimer(time_in_seconds:Long) {
        countdown_timer = object : CountDownTimer(time_in_seconds, 1000) {
            override fun onFinish() {
                binding.btnResendCode.visibility = View.VISIBLE
            }
            override fun onTick(p0: Long) {
                time_in_milli_seconds = p0
                val seconds = (time_in_milli_seconds / 1000) % 60
                binding.resendTimer.text = "Resend code in ${seconds}"
            }
        }
        countdown_timer.start()
        isRunning = true

    }

    private fun resendCode(){
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("+91$phoneNo") // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity())// Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .setForceResendingToken(resendToken)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}