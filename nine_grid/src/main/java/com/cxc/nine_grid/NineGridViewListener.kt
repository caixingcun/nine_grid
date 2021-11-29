package com.cxc.nine_grid

import android.view.View

interface NineGridViewListener {
    fun bigPicShowNotify(imagesWithoutAdd: List<ImagePickerBasicBean>, pos: Int,view:View?)

    fun addNewPicNotify(count: Int)

    fun delPicNotify(pos: Int, imagePickerBasicBean: ImagePickerBasicBean)
}