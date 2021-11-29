package com.cxc.nine_grid

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * @author caixingcun
 * @date 2021/11/29
 * Description :
 */
class NineGridView : RelativeLayout {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        val attributes = context.obtainStyledAttributes(R.styleable.NineGridViewStyle)
        imageLimit = attributes.getInteger(R.styleable.NineGridViewStyle_image_limit, 9)
        LayoutInflater.from(context).inflate(R.layout.layout_nine_pic_view, this, true)
        initView()
    }

    fun setImageEngine(imagePickerEngine: ImagePickerEngine) {
        mAdapter.setImageEngine(imagePickerEngine)
    }

    var imageLimit: Int = 9
    var nineGridViewListener: NineGridViewListener? = null
    lateinit var mAdapter: ImagePickerAdapter
    lateinit var mRv: RecyclerView

    fun initView() {
        mRv = findViewById(R.id.recycler_view)
        mRv.layoutManager = GridLayoutManager(context, 3)
        mAdapter = ImagePickerAdapter(mIsEdit = true, data = ArrayList(), imageLimit)
        mRv.adapter = mAdapter
        mAdapter.setOnItemChildClickListener { _, view, position ->
            when (view.id) {
                //删除当前图片
                R.id.civ_del -> {
                    mAdapter.remove(position)
                    val temp = mAdapter.getImagesWithoutAdd()
                    mAdapter.setImages(temp)
                }
                R.id.iv_img -> {
                    nineGridViewListener?.apply {
                        if (mAdapter.getImageWithAdd()[position].isPhotoAdd()) {
                            // 添加
                            addNewPic(imageLimit - mAdapter.getImagesWithoutAdd().size)
                        } else {
                            //大图展示
                            bigPicShow(mAdapter.getImagesWithoutAdd(), position)
                        }
                    }
                }
            }
        }
    }
}

interface NineGridViewListener {

    fun bigPicShow(imagesWithoutAdd: List<ImagePickerBasicBean>, pos: Int)

    fun addNewPic(count: Int)

}

interface ImagePickerEngine {
    fun load(context: Context, image: ImageView, url: String)
}