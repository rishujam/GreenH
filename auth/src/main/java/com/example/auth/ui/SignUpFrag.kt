package com.example.auth.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.core.data.Constants
import com.example.auth.data.model.UserProfile
import com.example.auth.databinding.SignupFragBinding
import com.example.auth.ui.composable.SignUpScreen
import dagger.hilt.android.AndroidEntryPoint

/*
 * Created by Sudhanshu Kumar on 06/07/23.
 */

@AndroidEntryPoint
class SignUpFrag : Fragment() {

    private var _binding: SignupFragBinding? = null
    private val binding get() = _binding
    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SignupFragBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val buildVersion = arguments?.getInt(Constants.Args.BUILD_VERSION)
        binding?.signUpFragComposeView?.setContent {
            Surface(modifier = Modifier.fillMaxSize()) {
                SignUpScreen(
                    viewModel.state,
                    onEvent = viewModel::onEvent,
                    buildVersion
                ) { profile ->
                    onLoggedIn(profile)
                }
            }
        }
    }

    private fun onLoggedIn(profile: UserProfile) {
        val intent = Intent()
            .putExtra(Constants.Args.RESULT_USER_LOGGED_IN, true)
            .putExtra(Constants.Args.PROFILE, profile)
        activity?.setResult(Activity.RESULT_OK, intent)
        activity?.finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}