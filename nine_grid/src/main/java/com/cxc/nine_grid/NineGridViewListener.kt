package com.cxc.nine_grid

interface NineGridViewListener {
    fun bigPicShow(imagesWithoutAdd: List<ImagePickerBasicBean>, pos: Int)

    fun addNewPic(count: Int)

    fun delPic(pos: Int, imagePickerBasicBean: ImagePickerBasicBean)
}