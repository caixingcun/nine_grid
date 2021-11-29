package com.cxc.nine_grid

import android.content.Context
import android.widget.ImageView

interface ImagePickerEngine {
    fun load(context: Context, image: ImageView, url: String)
}