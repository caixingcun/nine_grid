# 封装九宫图展示

#说明 
~~~
 九图选择，可以自定义 删除按钮 添加按钮及图片大小等（整个item条目）
 框架主要协助处理 删除 添加 逻辑及 删除按钮/添加按钮显示逻辑 
 支持图片框架（添加imageEngine）
~~~

# 依赖
~~~
        project build.gradle
          		maven { url 'https://jitpack.io' }  
            
        app build.gradle
        
                implementation 'com.github.caixingcun:nine_grid:v1.5'
        
~~~

#函数
~~~
    fun setInit
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
     
     //获取图片
       getImages()
      
      // 添加图片
      addImages(mutableListOf())
      
      // 移除特定位置图片
      nineGridView.removeImage(0)
      
      //刷新页面
      nineGridView.notifyAdapter()
~~~

# use

~~~     
        xml 
        
        <com.cxc.nine_grid.NineGridView
        android:id="@+id/nine_grid_view"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />
        
        
        java 
        
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
                /**
                 * @param imagesWithoutAdd 图片集合
                 * @param pos 点击位置
                 * @param view 当前位置 view 用于专场动画
                 */
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
                /**
                 * @param count 剩余支持添加的图片数
                 */
                override fun addNewPicNotify(count: Int) {
                    nineGridView.addImages(
                        mutableListOf(
                            ImagePickerBasicBean.getInstance("https://www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png"),
                            ImagePickerBasicBean.getInstance("https://www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png")
                        )
                    )
                }
                /**
                 * @param pos 剩余支持添加的图片数
                 * @param imagePickerBasicBean 删除的数据
                 */
                override fun delPicNotify(pos: Int, imagePickerBasicBean: ImagePickerBasicBean) {
                    log("${imagePickerBasicBean.getImagePickerUrl()}")
                    log("$pos")
                }
            },
            imagePickerEngine =  object : ImagePickerEngine {
                override fun load(context: Context, image: ImageView, url: String) {
                    Glide.with(this@MainActivity).load(url).into(image)
                }
            }
~~~

