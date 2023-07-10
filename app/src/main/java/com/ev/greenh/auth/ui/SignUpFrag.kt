package com.ev.greenh.auth.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ev.greenh.R
import com.ev.greenh.auth.AuthActivity
import com.ev.greenh.databinding.SignUpFragBinding

/*
 * Created by Sudhanshu Kumar on 06/07/23.
 */

class SignUpFrag : Fragment() {

    private var _binding: SignUpFragBinding?=null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SignUpFragBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val verifyPhoneFragment = VerifyPhoneFragment()
        val enterPhoneFragment = EnterPhoneFragment()

        (activity as AuthActivity).setCurrentFragment(
            enterPhoneFragment,
            R.id.flAuthFrag
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}