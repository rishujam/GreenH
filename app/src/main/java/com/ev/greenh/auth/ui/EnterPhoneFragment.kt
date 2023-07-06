package com.ev.greenh.auth.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ev.greenh.auth.ui.composable.PhoneView
import com.ev.greenh.databinding.FragmentEnterPhoneBinding

/*
 * Created by Sudhanshu Kumar on 06/07/23.
 */

class EnterPhoneFragment : Fragment() {

    private var _binding: FragmentEnterPhoneBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEnterPhoneBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.composeEnterPhone?.setContent {
            PhoneView()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}