package com.ev.greenh.ui.profile

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.ev.greenh.R
import com.ev.greenh.auth.AuthActivity
import com.ev.greenh.databinding.FragmentSettingsBinding
import com.ev.greenh.models.Profile
import com.ev.greenh.ui.MainActivity
import com.ev.greenh.util.Resource
import com.ev.greenh.util.visible
import com.ev.greenh.viewmodels.PlantViewModel
import com.google.firebase.auth.FirebaseAuth
import java.io.UnsupportedEncodingException
import java.net.URLEncoder


class SettingFragment:Fragment() {

    private var _binding : FragmentSettingsBinding?=null
    private val binding get() = _binding!!
    private lateinit var viewModel:PlantViewModel
    private lateinit var email:String
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater,container,false)
        viewModel = (activity as MainActivity).viewModel
        auth = FirebaseAuth.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.readUid()

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
        viewModel.uid.observe(viewLifecycleOwner, Observer {
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

        binding.logout.setOnClickListener {
            binding.pbSettings.visibility = View.VISIBLE
            auth.signOut()
            checkCurrentState()
        }

        binding.query.setOnClickListener {
            try {
                val packageManager = (activity as MainActivity).packageManager
                val i = Intent(Intent.ACTION_VIEW)
                val url =
                    "https://api.whatsapp.com/send?phone=8076861086&text=" + URLEncoder.encode(
                        "",
                        "UTF-8"
                    )
                i.setPackage("com.whatsapp")
                i.data = Uri.parse(url)
                if (i.resolveActivity(packageManager) != null) {
                    startActivity(i)
                }
            } catch (ex: ActivityNotFoundException) {
                Toast.makeText(context, "Not able to Whatsapp.", Toast.LENGTH_LONG).show()
            } catch (e: UnsupportedEncodingException) {
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupProfileData(profile:Profile){
        binding.apply {
            profileName.text = "Hi ${profile.name}!"
            profilePhone.text = profile.phone
            if(profile.profileComplete){
                profileAddress.text = profile.address
                profileEmail.text = profile.emailId
            }else{
                binding.tvEditProfile.text = "Complete Profile"
                profileAddress.visibility = View.GONE
                profileEmail.visibility = View.GONE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun checkCurrentState(){
        if(auth.currentUser==null){
            startActivity(Intent(context,AuthActivity::class.java))
            activity?.finish()
        }
    }
}