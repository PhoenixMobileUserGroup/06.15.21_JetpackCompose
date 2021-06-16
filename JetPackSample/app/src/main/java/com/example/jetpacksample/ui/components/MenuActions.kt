package com.example.jetpacksample.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.jetpacksample.R

sealed class MenuAction(
    @StringRes val label: Int,
    @DrawableRes val icon: Int
) {
    object Settings: MenuAction(R.string.settings, R.drawable.ic_settings)
}