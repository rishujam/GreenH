package com.ev.greenh.profile.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.core.data.Constants
import com.core.ui.hide
import com.core.ui.show
import com.core.util.Resource
import com.ev.greenh.R
import com.ev.greenh.databinding.FragmentEditProfileBinding
import com.ev.greenh.ui.MainActivity
import com.ev.greenh.util.Constants.VERSION
import com.example.auth.data.model.UserProfile
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditProfileFragment : Fragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    private var profile: UserProfile? = null
    private val viewModel by viewModels<EditProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profile = arguments?.getSerializable(Constants.Args.PROFILE) as? UserProfile
        binding.apply {
            etNameEdit.setText(profile?.name.orEmpty())
            tvPhoneEP.text = "Phone: ${profile?.phone.orEmpty()}"
            etAddress.setText(profile?.address?.split("%")?.getOrNull(0).orEmpty())
            etPincode.setText(profile?.address?.split("%")?.getOrNull(1).orEmpty())
            etEmailEP.setText(profile?.emailId.orEmpty())
            etEmailEP.setText(profile?.emailId.orEmpty())
            if(profile?.gender == "Male") {
                binding.rbMale.isChecked = true
            } else if(profile?.gender == "Female") {
                binding.rbFemale.isChecked = true
            }
            when(profile?.ageGroup) {
                "Below 20" -> binding.rbBelow20.isChecked = true
                "20 - 25" -> binding.rb20to25.isChecked = true
                "Above 25" -> binding.rbAbove25.isChecked = true
            }
        }
        lifecycleScope.launch {
            viewModel.updateProfile.collectLatest {
                when(it) {
                    is Resource.Success -> {
                        Toast.makeText(context, "Profile updated", Toast.LENGTH_SHORT).show()
                        binding.pbEditProfile.hide()
                        (activity as? MainActivity)?.onBackPressedDispatcher?.onBackPressed()
                    }
                    is Resource.Error -> {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                        binding.pbEditProfile.hide()
                    }
                    is Resource.Loading -> {
                        binding.pbEditProfile.show()
                    }
                }
            }
        }

        binding.btnSave.setOnClickListener {
            val name = binding.etNameEdit.text.toString().trim()
            val address = binding.etAddress.text.toString().trim()
            val pin = binding.etPincode.text.toString().trim()
            val emailId = binding.etEmailEP.text.toString().trim()
            var gender = ""
            var ageGroup = ""
            when (binding.rgGender.checkedRadioButtonId) {
                R.id.rbMale -> {
                    gender = "Male"
                }

                R.id.rbFemale -> {
                    gender = "Female"
                }
            }
            when (binding.rgAgeGroup.checkedRadioButtonId) {
                R.id.rbBelow20 -> {
                    ageGroup = binding.rbBelow20.text.toString()
                }

                R.id.rb20to25 -> {
                    ageGroup = binding.rb20to25.text.toString()
                }

                R.id.rbAbove25 -> {
                    ageGroup = binding.rbAbove25.text.toString()
                }
            }
            if (name.isNotEmpty() && address.isNotEmpty() && pin.isNotEmpty() && gender != "" && ageGroup != "") {
                val updatedProfile = UserProfile(
                    emailId,
                    name,
                    "$address%$pin",
                    profile?.phone.orEmpty(),
                    VERSION,
                    profile?.uid.orEmpty(),
                    gender,
                    ageGroup,
                    true
                )
                viewModel.updateProfile(updatedProfile)
            } else {
                Toast.makeText(context, "Fill details properly", Toast.LENGTH_SHORT).show()
            }
        }

        binding.backBtn.setOnClickListener {
            (activity as? MainActivity)?.onBackPressedDispatcher?.onBackPressed()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}