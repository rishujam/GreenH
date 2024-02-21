package com.ev.greenh.grow.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.ev.greenh.R
import com.ev.greenh.databinding.FragmentGrowDetailBinding
import com.ev.greenh.grow.data.GrowRepository
import com.ev.greenh.grow.ui.composable.GrowDetailScreen
import com.ev.greenh.grow.ui.model.BadgeIconData
import com.ev.greenh.grow.ui.model.GrowDetailData
import dagger.hilt.android.AndroidEntryPoint

/*
 * Created by Sudhanshu Kumar on 15/10/23.
 */

@AndroidEntryPoint
class GrowDetailFragment : Fragment() {

    private var _binding: FragmentGrowDetailBinding? = null
    private val binding get() = _binding
    private val viewModel: GrowViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGrowDetailBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val plantsId = arguments?.getString(GrowConstants.FragmentArgKeys.PLANT_ID)
        val data = plantsId?.let {
            val res = viewModel.getGrowDetail(plantsId)
            val badgeIcons = mutableListOf<BadgeIconData>()
            for(i in res.requirements) {
                val badgeData = when(i.key) {
                    GrowConstants.ResKey.MAINTENANCE -> {
                        BadgeIconData(
                            i.value,
                            R.drawable.ic_maintenance,
                            i.key
                        )
                    }
                    GrowConstants.ResKey.SEASON -> {
                        when(i.value) {
                            GrowConstants.ResValue.SEASON_RAINY -> {
                                BadgeIconData(
                                    i.value,
                                    R.drawable.ic_rainy,
                                    i.key
                                )
                            }
                            GrowConstants.ResValue.SEASON_SUMMER -> {
                                BadgeIconData(
                                    i.value,
                                    R.drawable.ic_summer,
                                    i.key
                                )
                            }
                            GrowConstants.ResValue.SEASON_WINTER -> {
                                BadgeIconData(
                                    i.value,
                                    R.drawable.ic_winter,
                                    i.key
                                )
                            }
                            else -> null
                        }
                    }
                    GrowConstants.ResKey.WATER -> {
                        BadgeIconData(
                            i.value,
                            R.drawable.ic_water_drop,
                            i.key
                        )
                    }
                    GrowConstants.ResKey.SUNLIGHT -> {
                        BadgeIconData(
                            i.value,
                            R.drawable.ic_summer,
                            i.key
                        )
                    }
                    else -> null
                }
                badgeData?.let { badgeIcons.add(it) }
            }
            GrowDetailData(
                res.headImageUrl,
                res.plantName,
                badgeIcons,
                res.steps,
                res.isAvailableInShop
            )
        }
        binding?.cvGrowDetailFrag?.setContent {
            GrowDetailScreen(data)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}