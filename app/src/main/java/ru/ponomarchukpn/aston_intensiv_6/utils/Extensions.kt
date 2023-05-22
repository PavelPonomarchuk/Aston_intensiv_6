package ru.ponomarchukpn.aston_intensiv_6.utils

import android.content.Context

fun Context.dpToIntegerPixels(valueDp: Int) = (resources.displayMetrics.density * valueDp).toInt()