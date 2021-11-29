package com.cxc.nine_grid

import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import java.lang.RuntimeException


/**
 * @author caixingcun
 * @date 2021/11/29
 * Description :
 */
class ImagePickerAdapter(
    var mIsEdit: Boolean = false,
    data: MutableList<ImagePickerBasicBean>,
    var MAX_SIZE: Int = 9
) :
    BaseQuickAdapter<ImagePickerBasicBean, BaseViewHolder>(
        R.layout.list_item_image,
        data
    ) {

    override fun convert(helper: BaseViewHolder, item: ImagePickerBasicBean) {
        helper.apply {
            val iv = getView<ImageView>(R.id.iv_img)
            val civDel = getView<ImageView>(R.id.civ_del)
            addOnClickListener(R.id.civ_del)
            addOnClickListener(R.id.iv_img)

            if (item.isPhotoAdd()) {
                civDel.visibility = View.INVISIBLE
            } else {
                if (mIsEdit) {
                    civDel.visibility = View.VISIBLE
                } else {
                    civDel.visibility = View.INVISIBLE
                }
            }
            if (item.isPhotoAdd()) {
                iv.setImageResource(R.mipmap.ic_upload_pic)
            } else {
                if (engine == null) {
                    throw RuntimeException("please set image engine")
                } else {
                    engine?.load(mContext, iv, item.getImagePickerUrl())
                }
            }
        }
    }

    var engine: ImagePickerEngine? = null

    fun getImageWithAdd(): List<ImagePickerBasicBean> {
        return mData
    }

    fun getImagesWithoutAdd(): MutableList<ImagePickerBasicBean> {
        return mData.filter {
            !it.isPhotoAdd()
        }.toMutableList()
    }

    fun setImages(data: List<ImagePickerBasicBean>) {
        var temp = data.filter {
            !it.isPhotoAdd()
        }.toList()
        val data = ArrayList(temp)
        if (mIsEdit && itemCount < MAX_SIZE) {
            data.add(ImagePickerBasicBean.getPhotoAddBean())
        }
        setNewData(data)
    }

    fun setImageEngine(imagePickerEngine: ImagePickerEngine) {
        this.engine = imagePickerEngine
    }
}


interface ImagePickerBasicBean {
    companion object {
        /**
         * 图片添加标签
         */
        private const val PHOTO_ADD = "PHOTO_ADD"

        fun getPhotoAddBean(): ImagePickerBasicBean {
            return object : ImagePickerBasicBean {
                override fun getImagePickerUrl(): String {
                    return PHOTO_ADD
                }
            }
        }
    }

    fun getImagePickerUrl(): String

    /**
     * 是否是添加按钮
     */
    fun isPhotoAdd(): Boolean {
        return getImagePickerUrl() == PHOTO_ADD
    }
}