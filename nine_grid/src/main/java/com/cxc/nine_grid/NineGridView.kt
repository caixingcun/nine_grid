package com.cxc.nine_grid

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.properties.Delegates

/**
 * @author caixingcun
 * @date 2021/11/29
 * Description : nine grid view format
 */
class NineGridView : RelativeLayout {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
    }

    var maxSize: Int = 9
    var isEdit: Boolean = false
    var spanCount: Int = 3
    var nineGridViewListener: NineGridViewListener? = null
    var imagePickerEngine: ImagePickerEngine? = null
    var data: MutableList<ImagePickerBasicBean>? = null
    var layout_item_resource by Delegates.notNull<Int>()
    var id_iv_img by Delegates.notNull<Int>()
    var id_iv_del: Int? = null
    var resource_upload_img: Int? = null

    var layout_recycler_view_resource: Int? = null
    var recycler_view_id: Int? = null

    /**
     * @param maxSize 最大选图数量
     * @param spanCount 每行数量
     * @param isEdit 是否可以编辑
     * @param data 数据集合 必须继承 ImagePickerBasicBean
     * @param id_iv 图片item id
     * @param id_iv_del 删除按钮 id
     * @param resource_upload_img 上传背景图片
     * @param nineGridViewListener 监听回调
     * @param imagePickerEngine 图片加载引擎
     */
    fun setInit(
        maxSize: Int = 9,
        spanCount: Int = 3,
        isEdit: Boolean = false,
        data: MutableList<ImagePickerBasicBean> = mutableListOf(),
        layout_item_resource: Int,
        id_iv: Int,
        id_iv_del: Int? = null,
        resource_upload_img: Int? = null,
        layout_recycler_view_resource: Int,
        recycler_view_id: Int,
        nineGridViewListener: NineGridViewListener,
        imagePickerEngine: ImagePickerEngine
    ) {
        this.layout_item_resource = layout_item_resource
        this.id_iv_img = id_iv
        this.id_iv_del = id_iv_del
        this.resource_upload_img = resource_upload_img
        this.maxSize = maxSize
        this.isEdit = isEdit
        this.data = data
        this.spanCount = spanCount
        this.nineGridViewListener = nineGridViewListener
        this.imagePickerEngine = imagePickerEngine
        this.layout_recycler_view_resource = layout_recycler_view_resource
        this.recycler_view_id =recycler_view_id
        LayoutInflater.from(context)
            .inflate(layout_recycler_view_resource, this, true)
        initView()
    }


    lateinit var mAdapter: ImagePickerAdapter
    lateinit var mRv: RecyclerView

    /**
     * add extra image
     * if over max_size , only set images from 0 to maxSize-1
     */
    fun addImages(moreImages: MutableList<ImagePickerBasicBean>) {
        var result = mAdapter.getImagesWithoutAdd()
        result.addAll(moreImages)
        if (result.size > maxSize) {
            result = result.subList(0, maxSize)
        }
        mAdapter.setImages(result)
    }

    /**
     * provider the real images
     * @return return images have
     */
    fun getImages(): MutableList<ImagePickerBasicBean> {
        return mAdapter.getImagesWithoutAdd()
    }

    /**
     * notify adapter
     */
    fun notifyAdapter() {
        mAdapter.notifyDataSetChanged()
    }

    /**
     * remove a pos image
     */
    fun removeImage(position: Int) {
        if (position >= maxSize || position >= getImages().size) {
            Log.d("nine_grid", "removeImage 参数不合规")
            return
        }
        delImage(position)
    }

    private fun delImage(position: Int) {
        val result = mAdapter.data[position]
        mAdapter.remove(position)
        mAdapter.setImages(mAdapter.getImagesWithoutAdd())
        nineGridViewListener?.delPicNotify(position, result)
    }


    private fun initView() {
        this.maxSize = maxSize
        mRv = findViewById<RecyclerView>(this.recycler_view_id!!)
        if (mRv == null) {
            return
        }
        mRv.layoutManager = GridLayoutManager(context, spanCount)
        mAdapter = ImagePickerAdapter(
            mIsEdit = isEdit,
            data = data!!,
            MAX_SIZE = maxSize,
            layout_item = layout_item_resource,
            id_iv_img = id_iv_img,
            id_iv_del = id_iv_del,
            resource_id_upload = resource_upload_img
        )
        mAdapter.setImageEngine(imagePickerEngine)
        mRv.adapter = mAdapter
        mAdapter.setImages(data!!)
        mAdapter.setOnItemChildClickListener { _, view, position ->
            when (view.id) {
                //删除当前图片
                id_iv_del -> {
                    delImage(position)
                }
                id_iv_img -> {
                    nineGridViewListener?.apply {
                        if (mAdapter.getImageWithAdd()[position].isPhotoAdd()) {
                            // 添加
                            addNewPicNotify(maxSize - mAdapter.getImagesWithoutAdd().size)
                        } else {
                            //大图展示
                            bigPicShowNotify(
                                mAdapter.getImagesWithoutAdd(),
                                position,
                                mAdapter.getViewByPosition(mRv, position, id_iv_img)
                            )
                        }
                    }
                }
            }
        }
    }
}

