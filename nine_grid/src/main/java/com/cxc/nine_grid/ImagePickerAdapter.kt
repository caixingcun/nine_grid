package com.cxc.nine_grid

import android.util.Log
import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder


/**
 * @author caixingcun
 * @date 2021/11/29
 * Description :
 */
class ImagePickerAdapter(
    /**
     * whether can edit （delete or add）
     */
    var mIsEdit: Boolean = false,
    /**
     * pic data list
     */
    data: MutableList<ImagePickerBasicBean>,
    /**
     * pic max size
     */
    var MAX_SIZE: Int = 9,
    /**
     * item layout
     */
    var layout_item: Int,
    /**
     * imageview id
     */
    var id_iv_img: Int,
    /**
     * imageView del
     */
    var id_iv_del: Int? = null,
    /**
     * upload icon resource
     */
    var resource_id_upload: Int?
) :
    BaseQuickAdapter<ImagePickerBasicBean, BaseViewHolder>(
        layout_item,
        data
    ) {

    override fun convert(helper: BaseViewHolder, item: ImagePickerBasicBean) {
        helper.apply {
            val iv = getView<ImageView>(id_iv_img)
            addOnClickListener(id_iv_img)

            id_iv_del?.let {
                val ivDel = getView<ImageView>(it)
                addOnClickListener(it)
                if (item.isPhotoAdd()) {
                    ivDel.visibility = View.INVISIBLE
                } else {
                    if (mIsEdit) {
                        ivDel.visibility = View.VISIBLE
                    } else {
                        ivDel.visibility = View.INVISIBLE
                    }
                }
            }

            if (item.isPhotoAdd()) {
                resource_id_upload?.let {
                    iv.setImageResource(it)
                }
            } else {
                if (engine == null) {
                    Log.d("nine_grid", "no image engine")
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

    fun setImages(data: MutableList<ImagePickerBasicBean>) {
        var temp = data.filter {
            !it.isPhotoAdd()
        }.toList()
        val data = ArrayList(temp)
        if (mIsEdit && itemCount < MAX_SIZE) {
            data.add(ImagePickerBasicBean.getPhotoAddBean())
        }
        setNewData(data)
    }

    fun setImageEngine(imagePickerEngine: ImagePickerEngine?) {
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

        fun getInstance(url: String): ImagePickerBasicBean {
            return object : ImagePickerBasicBean {
                override fun getImagePickerUrl(): String {
                    return url
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