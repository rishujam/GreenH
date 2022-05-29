package com.ev.greenh

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.ev.greenh.databinding.FragmentSettingsBinding
import com.ev.greenh.models.Profile
import com.ev.greenh.util.Resource
import com.ev.greenh.util.visible
import com.ev.greenh.viewmodels.PlantViewModel
import kotlinx.coroutines.flow.first

class SettingFragment:Fragment() {

    private var _binding : FragmentSettingsBinding?=null
    private val binding get() = _binding!!
    private lateinit var viewModel:PlantViewModel
    private lateinit var email:String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater,container,false)
        viewModel = (activity as MainActivity).viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.readEmail()

        viewModel.profile.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success ->{
                    if(it.data!=null){
                        setupProfileData(it.data)
                        binding.pbSettings.visible(false)
                    }
                }
                is Resource.Error ->{
                    Toast.makeText(context, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading ->{}
            }
        })
        viewModel.email.observe(viewLifecycleOwner, Observer {
            when(it.getContentIfNotHandled()){
                is Resource.Success -> {
                    val user = it.peekContent().data
                    if(user!=null){
                        email = user
                        viewModel.getUserDetails(getString(R.string.user_ref),user)
                    }
                }
                is Resource.Error ->{
                    Toast.makeText(context,"Error Loading Profile",Toast.LENGTH_SHORT).show()
                    Log.e("Setting Frag",it.peekContent().message.toString())
                }
                is Resource.Loading->{}
                else ->{}
            }
        })

        binding.editProfile.setOnClickListener {
            val bundle = Bundle()
            val editProfileFragment = EditProfileFragment()
            bundle.putString("email",email)
            editProfileFragment.arguments = bundle
            (activity as MainActivity).setCurrentFragmentBack(editProfileFragment)
        }
    }

    private fun setupProfileData(profile:Profile){
        binding.apply {
            profileName.text = "Hi ${profile.name}!"
            profileAddress.text = profile.address
            profilePhone.text = profile.phone
            profileEmail.text = profile.emailId
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}