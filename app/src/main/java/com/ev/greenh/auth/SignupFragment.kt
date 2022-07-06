package com.ev.greenh.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.ev.greenh.ui.MainActivity
import com.ev.greenh.R
import com.ev.greenh.databinding.FragmentSignupBinding
import com.ev.greenh.models.Profile
import com.ev.greenh.services.FirebaseNotify
import com.ev.greenh.util.visible
import com.ev.greenh.viewmodels.AuthViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.messaging.FirebaseMessaging
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

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {}

            override fun onVerificationFailed(e: FirebaseException) {
                binding.progressBar.visibility = View.INVISIBLE
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                binding.progressBar.visibility = View.INVISIBLE
                Log.d("TAG","onCodeSent:$verificationId")
                resendToken = token
                binding.etCode1.visibility = View.VISIBLE
                binding.etCode2.visibility = View.VISIBLE
                binding.etCode3.visibility = View.VISIBLE
                binding.etCode4.visibility = View.VISIBLE
                binding.etCode5.visibility = View.VISIBLE
                binding.etCode6.visibility = View.VISIBLE
                binding.btnVerifyCode.visibility = View.VISIBLE
                binding.resendTimer.visibility  = View.VISIBLE
                binding.btnSignup.visibility = View.GONE
                verificationI  = verificationId
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


//        binding.etEmail.setOnClickListener {
//            openSomeActivityForResult()
//        }

//        binding.btnSignup.setOnClickListener {
//            email = binding.etEmail.text.trim().toString()
//            val pass = binding.etPass.text.toString().trim()
//            val confPass = binding.etPassConfirm.text.toString().trim()
//            if(email.isNotEmpty() && pass.isNotEmpty() && confPass.isNotEmpty()){
//                if(confPass == pass){
//                    viewModel.registerUser(email,pass)
//                }else{
//                    Toast.makeText(requireContext(), "Password should match.", Toast.LENGTH_SHORT).show()
//                }
//            }
//            else{
//                Toast.makeText(context,"Enter details properly",Toast.LENGTH_SHORT).show()
//            }
//        }
    }

//    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//        if (result.resultCode == Activity.RESULT_OK) {
//            val data: Intent? = result.data
//            val result = data?.getStringExtra(AccountManager.KEY_ACCOUNT_NAME).toString()
//            binding.etEmail.setText(result)
//        }
//    }

//    private fun openSomeActivityForResult() {
//        val intent = AccountPicker.newChooseAccountIntent(
//            AccountPicker.AccountChooserOptions.Builder()
//                .setAllowableAccountsTypes(Arrays.asList("com.google"))
//                .build()
//        )
//        resultLauncher.launch(intent)
//    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val uid = auth.currentUser?.uid.toString()
                    val profile = Profile(phone = phoneNo, uid = uid)
                    viewModel.saveUserProfile(getString(R.string.user_ref),profile)
                    viewModel.saveUIDLocally(uid)
                    viewModel.saveNotifyToken(uid, token,getString(R.string.token))
                    startActivity(Intent(context, MainActivity::class.java))
                    (activity as AuthActivity).finish()
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}