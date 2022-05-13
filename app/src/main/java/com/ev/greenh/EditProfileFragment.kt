package com.ev.greenh

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.ev.greenh.databinding.FragmentEditProfileBinding
import com.ev.greenh.models.Response
import com.ev.greenh.util.Resource
import com.ev.greenh.viewmodels.PlantViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class EditProfileFragment : Fragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: PlantViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        viewModel = (activity as MainActivity).viewModel
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val email = arguments?.getString("email")

        viewModel.updateAddress.observe(viewLifecycleOwner,Observer{
            when(it){
                is Resource.Success -> {
                    Toast.makeText(context, "Address Updated", Toast.LENGTH_SHORT).show()
                    (activity as MainActivity).supportFragmentManager.popBackStack()
                }
                is Resource.Error -> {
                    Toast.makeText(context, "Error Updating Address", Toast.LENGTH_SHORT).show()
                    Log.e("updateAddress",it.message.toString())
                }
                is Resource.Loading -> {}
            }
        })

        viewModel.profile.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success -> {
                    val profile = it.data
                    if(profile!=null){
                        binding.etNameEdit.setText(profile.name)
                        binding.etAddress.setText(profile.address.split("%")[0])
                        binding.etPincode.setText(profile.address.split("%")[1])
                        binding.etPhone.setText(profile.phone)
                    }
                }
                is Resource.Error -> {
                    Toast.makeText(context, "Unable To Load Profile", Toast.LENGTH_SHORT).show()
                    Log.e("EditProfileFragment",it.message.toString())
                }
                is Resource.Loading -> {}
            }
        })

        viewModel.getUserDetails(getString(R.string.user_ref),email.toString())

        binding.btnSaveAddress.setOnClickListener {
            val name = binding.etNameEdit.text.toString().trim()
            val address = binding.etAddress.text.toString().trim()
            val pin = binding.etPincode.text.toString().trim()
            if (name.isNotEmpty() && address.isNotEmpty() && pin.isNotEmpty()) {
                viewModel.updateAddress(getString(R.string.user_ref),email.toString(),"$address%$pin",name)
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}