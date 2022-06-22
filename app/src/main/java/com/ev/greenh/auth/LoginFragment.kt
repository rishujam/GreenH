package com.ev.greenh.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.ev.greenh.MainActivity
import com.ev.greenh.databinding.FragmentLoginBinding
import com.ev.greenh.util.Resource
import com.ev.greenh.util.enable
import com.ev.greenh.util.startNewActivity
import com.ev.greenh.util.visible
import com.ev.greenh.viewmodels.AuthViewModel

class LoginFragment:Fragment() {

    private var _binding : FragmentLoginBinding?=null
    private val binding get() = _binding!!
    private lateinit var viewModel: AuthViewModel
    private lateinit var email:String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater,container,false)
        viewModel = (activity as AuthActivity).viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            pbLogin.visible(false)
            btnLogin.enable(false)
        }


        viewModel.authResponse.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Loading ->{
                    binding.pbLogin.visible(true)
                }
                is Resource.Success -> {
                    //viewModel.saveEmailLocally(email)
                    binding.pbLogin.visible(false)
                    requireActivity().startNewActivity(MainActivity::class.java)
                    Toast.makeText(requireContext(), "Login Success", Toast.LENGTH_SHORT).show()
                }
                is Resource.Error ->{
                    binding.pbLogin.visible(false)
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })

        binding.etPass.addTextChangedListener {
            email = binding.etEmail.text.toString().trim()
            binding.btnLogin.enable(email.isNotEmpty() && it.toString().trim().isNotEmpty())
        }
        binding.btnLogin.setOnClickListener {
            val pass = binding.etPass.text
            viewModel.loginUser(email,pass.toString())
        }

        binding.directToSignup.setOnClickListener {
            (activity as AuthActivity).supportFragmentManager.popBackStack()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding =null
    }
}