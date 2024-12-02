package com.core.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import com.core.ui.R
import com.core.ui.databinding.ButtonLayoutBinding

/*
 * Created by Sudhanshu Kumar on 02/12/24.
 */

class ButtonG @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private var buttonType: Int? = null
    private var text: String? = ""
    private var isButtonEnabled: Boolean
    private var verticalPadding: Int = 2
    private var binding: ButtonLayoutBinding? = null

    init {
        val array = context.obtainStyledAttributes(attrs, R.styleable.ButtonG)
        buttonType = array.getInt(R.styleable.ButtonG_buttonType, 1)
        isButtonEnabled = array.getBoolean(R.styleable.ButtonG_isEnabled, true)
        text = array.getString(R.styleable.ButtonG_text)
        verticalPadding = array.getInt(R.styleable.ButtonG_verticalPadding, 2)
        array.recycle()
        binding = ButtonLayoutBinding.inflate(LayoutInflater.from(context))
        removeAllViews()
        init()
        addView(binding?.root)
    }

    private fun init() {
        binding?.apply {
            TextViewCompat.setTextAppearance(tvCustomButton, R.style.Typography_Docquity_Button)
            val bgResource = when (buttonType) {
                 1 -> {
                     if(isButtonEnabled) {
                         tvCustomButton.setTextColor(
                             ContextCompat.getColor(
                                 context,
                                 R.color.mat3_on_primary,
                             ),
                         )
                         R.drawable.bg_primary_btn
                     } else {
                         tvCustomButton.setTextColor(
                             ContextCompat.getColor(
                                 context,
                                 R.color.mat3_outline,
                             ),
                         )
                         R.drawable.bg_primary_disabled
                     }
                 }

                else -> {
                    tvCustomButton.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.mat3_on_surface_variant,
                        ),
                    )
                    if(isButtonEnabled) {
                        R.drawable.bg_secondary_btn
                    } else {

                        R.drawable.bg_secondary_btn
                    }
                }
            }
            if(verticalPadding == 1) {
                binding?.tvCustomButton?.setPadding(
                    16, 12, 16, 12
                )
            }
            root.setBackgroundResource(bgResource)
            binding?.tvCustomButton?.text = text
        }
    }

}