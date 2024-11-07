package com.ev.greenh.profile

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.core.data.Constants
import com.core.ui.nav.Navigation
import com.ev.greenh.BuildConfig
import com.ev.greenh.databinding.FragmentProfileBinding
import com.ev.greenh.profile.composable.ProfileScreen
import com.ev.greenh.profile.edit.EditProfileFragment
import com.ev.greenh.ui.MainActivity
import com.example.auth.data.model.UserProfile
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
                    val userLoggedIn = getBooleanExtra(
                        Constants.Args.RESULT_USER_LOGGED_IN,
                        false
                    )
                    val profile = getSerializableExtra(Constants.Args.PROFILE) as? UserProfile
                    viewModel.state = viewModel.state.copy(
                        isLoggedIn = userLoggedIn,
                        profile = profile
                    )
                    viewModel.getProfileDetail()
                }
            }
        }
        setFragmentResultListener(
            requestKey = Constants.Args.RESULT_PROFILE_UPDATE
        ) { requestKey, bundle ->
            if(requestKey == Constants.Args.RESULT_PROFILE_UPDATE) {
                val profileUpdated = bundle.getBoolean(Constants.Args.RESULT_PROFILE_UPDATE)
                if(profileUpdated) {
                    viewModel.getProfileDetail()
                }
            }
        }
        binding?.cvProfileFrag?.setContent {
            ProfileScreen(viewModel.state) {
                when(it) {
                    is ProfileEvents.Edit -> {
                        val editFragment = EditProfileFragment()
                        val bundle = Bundle()
                        bundle.putSerializable(Constants.Args.PROFILE, viewModel.state.profile)
                        editFragment.arguments = bundle
                        (activity as? MainActivity)?.setCurrentFragmentBack(editFragment)
                    }

                    is ProfileEvents.Auth -> {
                        navigation.authActivity(
                            context = activity,
                            buildVersion = buildVersion,
                            activityLauncher = resultLauncher
                        )
                    }

                    else -> viewModel.onEvent(it)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}