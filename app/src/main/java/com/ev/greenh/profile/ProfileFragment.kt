package com.ev.greenh.profile

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.core.ui.nav.NavConstants
import com.core.ui.nav.Navigation
import com.ev.greenh.BuildConfig
import com.ev.greenh.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/*
 * Created by Sudhanshu Kumar on 27/10/24.
 */

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding
    private val viewModel by viewModels<ProfileViewModel>()

    @Inject
    lateinit var navigation: Navigation

    private val buildVersion by lazy {
        BuildConfig.VERSION_CODE
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.apply {
                    val userLoggedIn = getBooleanExtra(NavConstants.RESULT_USER_LOGGED_IN, false)
                    viewModel.state = viewModel.state.copy(
                        isLoggedIn = userLoggedIn
                    )
                }
            }
        }
        binding?.cvProfileFrag?.setContent {
            ProfileScreen(viewModel.state) {
                when(it) {
                    is ProfileEvents.EditClick -> {

                    }

                    is ProfileEvents.AuthClick -> {
                        navigation.authActivity(
                            context = activity,
                            buildVersion = buildVersion,
                            activityLauncher = resultLauncher
                        )
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}