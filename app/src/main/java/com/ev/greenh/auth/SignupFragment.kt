package com.ev.greenh.auth

import android.accounts.AccountManager
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.ev.greenh.MainActivity
import com.ev.greenh.databinding.FragmentSignupBinding
import com.ev.greenh.util.Resource
import com.ev.greenh.util.startNewActivity
import com.ev.greenh.util.visible
import com.ev.greenh.viewmodels.AuthViewModel
import com.google.android.gms.common.AccountPicker
import java.util.*

class SignupFragment:Fragment() {

    private var _binding: FragmentSignupBinding?=null
    private val binding get() = _binding!!
    private lateinit var viewModel: AuthViewModel
    private lateinit var email:String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding  = FragmentSignupBinding.inflate(inflater,container,false)
        viewModel = (activity as AuthActivity).viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.progressBar.visible(false)
        viewModel.authResponse.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            when(it){
                is Resource.Loading -> {
                    binding.progressBar.visible(true)
                }
                is Resource.Success -> {
                    viewModel.saveEmailLocally(email)
                    binding.progressBar.visible(false)
                    requireActivity().startNewActivity(MainActivity::class.java)
                    Toast.makeText(requireContext(), "Account Created", Toast.LENGTH_SHORT).show()
                }
                is Resource.Error ->{
                    binding.progressBar.visible(false)
                    Toast.makeText(requireContext(), "Authentication Failure", Toast.LENGTH_SHORT).show()
                }
            }
        })

        binding.directToLogin.setOnClickListener {
            val loginFragment = LoginFragment()
            (activity as AuthActivity).setCurrentFragmentBack(loginFragment)
        }

        binding.etEmail.setOnClickListener {
            openSomeActivityForResult()
        }

        binding.btnSignup.setOnClickListener {
            email = binding.etEmail.text.trim().toString()
            val pass = binding.etPass.text.toString().trim()
            val confPass = binding.etPassConfirm.text.toString().trim()
            if(email.isNotEmpty() && pass.isNotEmpty() && confPass.isNotEmpty()){
                if(confPass == pass){
                    viewModel.registerUser(email,pass)
                }else{
                    Toast.makeText(requireContext(), "Password should match.", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(context,"Enter details properly",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val result = data?.getStringExtra(AccountManager.KEY_ACCOUNT_NAME).toString()
            binding.etEmail.setText(result)
        }
    }

    private fun openSomeActivityForResult() {
        val intent = AccountPicker.newChooseAccountIntent(
            AccountPicker.AccountChooserOptions.Builder()
                .setAllowableAccountsTypes(Arrays.asList("com.google"))
                .build()
        )
        resultLauncher.launch(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}