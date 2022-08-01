package com.ev.greenh.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.ev.greenh.R
import com.ev.greenh.databinding.FragmentEditProfileBinding
import com.ev.greenh.models.Profile
import com.ev.greenh.ui.MainActivity
import com.ev.greenh.util.Constants.VERSION
import com.ev.greenh.util.Resource
import com.ev.greenh.viewmodels.PlantViewModel
import com.google.android.material.snackbar.Snackbar

class EditProfileFragment : Fragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: PlantViewModel
    private lateinit var userDetails:Profile

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

        viewModel.updateProfile.observe(viewLifecycleOwner,Observer{
            when(it.getContentIfNotHandled()){
                is Resource.Success -> {
                    Snackbar.make(binding.root, "Profile completed", Snackbar.LENGTH_SHORT).show()
                    (activity as MainActivity).supportFragmentManager.popBackStack()
                }
                is Resource.Error -> {
                    Toast.makeText(context, "Error Updating Address", Toast.LENGTH_SHORT).show()
                    Log.e("updateAddress",it.peekContent().message.toString())
                }
                is Resource.Loading -> {}
                else ->{}
            }
        })

        viewModel.profile.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success -> {
                    val profile = it.data
                    if(profile!=null){
                        userDetails = profile
                        binding.etNameEdit.setText(profile.name)
                        if(profile.address!=""){
                            binding.etAddress.setText(profile.address.split("%")[0])
                            binding.etPincode.setText(profile.address.split("%")[1])
                        }
                        binding.tvPhoneEP.text = "Phone: ${profile.phone}"
                        when(profile.gender){
                            "Male" ->{ binding.rbMale.isChecked = true }
                            "Female" -> { binding.rbFemale.isChecked = true }
                        }
                        when(profile.ageGroup){
                            "Below 20" ->{binding.rbBelow20.isChecked = true }
                            "20 - 25" ->{binding.rb20to25.isChecked = true }
                            "Above 25" ->{binding.rbAbove25.isChecked = true }
                        }
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
            val emailId = binding.etEmailEP.text.toString().trim()
            var gender = ""
            var ageGroup = ""
            when(binding.rgGender.checkedRadioButtonId){
                R.id.rbMale -> {gender = "Male"}
                R.id.rbFemale -> {gender = "Female"}
            }
            when(binding.rgAgeGroup.checkedRadioButtonId){
                R.id.rbBelow20 ->{ageGroup = binding.rbBelow20.text.toString()}
                R.id.rb20to25 ->{ageGroup = binding.rb20to25.text.toString()}
                R.id.rbAbove25 ->{ageGroup = binding.rbAbove25.text.toString()}
            }
            if (name.isNotEmpty() && address.isNotEmpty() && pin.isNotEmpty() && gender!="" && ageGroup!="") {
                val profile = Profile(emailId,name, "$address%$pin",userDetails.phone,VERSION,userDetails.uid,gender, ageGroup,true)
                viewModel.updateUserDetails(getString(R.string.user_ref),profile)
            }else{
                Toast.makeText(context, "Fill details properly", Toast.LENGTH_SHORT).show()
            }
        }

        binding.backBtn.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.popBackStack()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}