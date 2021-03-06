package com.cxc.ninegridview

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.cxc.nine_grid.ImagePickerBasicBean
import com.cxc.nine_grid.ImagePickerEngine
import com.cxc.nine_grid.NineGridView
import com.cxc.nine_grid.NineGridViewListener

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.nine_show).setOnClickListener {
            initNineGridShow()
        }

        findViewById<Button>(R.id.nine).setOnClickListener {
            initNineGridImageSelect()
        }

    }

    private fun initNineGridShow() {
        val nineGridView = findViewById<NineGridView>(R.id.nine_grid_view)
        nineGridView.setInit(
            maxSize = 9,
            spanCount = 4,
            isEdit = false,
            data = mutableListOf(
                ImagePickerBasicBean.getInstance("https://www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png"),
                ImagePickerBasicBean.getInstance("https://www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png")
            ),
            layout_item_resource = R.layout.list_item_image,
            id_iv = R.id.iv_img,
            id_iv_del = R.id.iv_del,
            resource_upload_img = R.mipmap.ic_upload_pic,
            nineGridViewListener = object : NineGridViewListener {
                override fun bigPicShowNotify(
                    imagesWithoutAdd: List<ImagePickerBasicBean>,
                    pos: Int,
                    view: View?
                ) {
                    imagesWithoutAdd.forEach {
                        print(it.getImagePickerUrl())
                    }
                    log("$pos")
                }

                override fun addNewPicNotify(count: Int) {
                    nineGridView.addImages(
                        mutableListOf(
                            ImagePickerBasicBean.getInstance("https://www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png"),
                            ImagePickerBasicBean.getInstance("https://www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png")
                        )
                    )
                }

                override fun delPicNotify(pos: Int, imagePickerBasicBean: ImagePickerBasicBean) {
                    log("${imagePickerBasicBean.getImagePickerUrl()}")
                    log("$pos")
                }
            },
            imagePickerEngine = object : ImagePickerEngine {
                override fun load(context: Context, image: ImageView, url: String) {
                    Glide.with(this@MainActivity).load(url).into(image)
                }
            }
        )

        nineGridView.getImages()
        nineGridView.addImages(mutableListOf())
        nineGridView.removeImage(0)
        nineGridView.notifyAdapter()
    }

    private fun initNineGridImageSelect() {
        val nineGridView = findViewById<NineGridView>(R.id.nine_grid_view)
        nineGridView.setInit(
            9,
            4,
            true,
            mutableListOf(),
            R.layout.list_item_image,
            R.id.iv_img,
            R.id.iv_del,
            R.mipmap.ic_upload_pic,
            object : NineGridViewListener {
                override fun bigPicShowNotify(
                    imagesWithoutAdd: List<ImagePickerBasicBean>,
                    pos: Int,
                    view: View?
                ) {
                    imagesWithoutAdd.forEach {
                        print(it.getImagePickerUrl())
                    }
                    log("$pos")
                }

                override fun addNewPicNotify(count: Int) {
                    nineGridView.addImages(
                        mutableListOf(
                            ImagePickerBasicBean.getInstance("https://www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png"),
                            ImagePickerBasicBean.getInstance("https://www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png")
                        )
                    )
                }

                override fun delPicNotify(pos: Int, imagePickerBasicBean: ImagePickerBasicBean) {
                    log("${imagePickerBasicBean.getImagePickerUrl()}")
                    log("$pos")
                }
            },
            object : ImagePickerEngine {
                override fun load(context: Context, image: ImageView, url: String) {
                    Glide.with(this@MainActivity).load(url).into(image)
                }
            }
        )
    }

    fun log(msg: String) {
        Log.d("tag", msg)
    }
}
