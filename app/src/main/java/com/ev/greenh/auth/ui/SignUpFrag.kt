package com.ev.greenh.auth.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ev.greenh.GreenApp
//import androidx.lifecycle.viewmodel.compose.viewModel
import com.ev.greenh.R
import com.ev.greenh.auth.AuthActivity
import com.ev.greenh.auth.SignUpViewModel
import com.ev.greenh.auth.ui.composable.PhoneView
import com.ev.greenh.auth.ui.composable.SignUpBrandingView
import com.ev.greenh.auth.ui.composable.SignUpScreen
import com.ev.greenh.databinding.SignUpFragBinding
import com.ev.greenh.firebase.AuthSource
import com.ev.greenh.repository.AuthRepository
import com.ev.greenh.viewmodels.AuthViewModel
import com.ev.greenh.viewmodels.ViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

/*
 * Created by Sudhanshu Kumar on 06/07/23.
 */

class SignUpFrag : Fragment() {

    private var _binding: SignUpFragBinding? = null
    private val binding get() = _binding
    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SignUpFragBinding.inflate(inflater, container, false)
        val authSource = AuthSource(FirebaseAuth.getInstance())
        val repo = AuthRepository(authSource,(activity?.application as GreenApp).userPreferences)
        val factory = ViewModelFactory(repo)
        viewModel = ViewModelProvider(this,factory)[SignUpViewModel::class.java]
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.signUpFragComposeView?.setContent {
            Surface(modifier = Modifier.fillMaxSize()) {
                SignUpScreen(viewModel)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}