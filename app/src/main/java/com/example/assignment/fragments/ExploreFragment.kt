package com.example.assignment.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.assignment.R
import com.example.assignment.adapters.AdapterCategory
import com.example.assignment.adapters.AdapterComicVertical
import com.example.assignment.adapters.SliderAdapter
import com.example.assignment.adapters.SliderAdapter.HandleItemClick
import com.example.assignment.databinding.FragmentExploreBinding
import com.example.assignment.models.Author
import com.example.assignment.models.Category
import com.example.assignment.models.Comic
import com.example.assignment.models.User
import com.example.assignment.utils.GridSpacingItemDecoration
import java.util.*

class ExploreFragment : Fragment() {
    private var context: Context? = null

    //slide
    private var sliderAdapter: SliderAdapter? = null
    private var timer: Timer? = null
    private var currentPage = 0
    private var isPause = false

    //slide
    private var adapterComicVertical: AdapterComicVertical? = null
    private var adapterCategory: AdapterCategory? = null
    private var fragmentExploreBinding: FragmentExploreBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragmentExploreBinding = FragmentExploreBinding.inflate(inflater, container, false)
        return fragmentExploreBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        addListener()
    }

    fun init() {
        setUpPopularList()
        setUpSlider()
        setUpListCategory()
        handleAutoScrollSlide()
    }

    private fun setUpListCategory() {
        adapterCategory = AdapterCategory(
            requireContext(),
            dataCategory,
            object : AdapterCategory.CategoryHandlerListener {
                override fun onItemClick(category: Category) {
                    Log.d(
                        TAG,
                        "onItemClick: " + category.title
                    )
                }
            })
        fragmentExploreBinding!!.categoryList.adapter = adapterCategory
        val gridLayoutManager: StaggeredGridLayoutManager =
            object : StaggeredGridLayoutManager(5, VERTICAL) {
                override fun canScrollVertically(): Boolean {
                    return false
                }
            }
        fragmentExploreBinding!!.categoryList.layoutManager = gridLayoutManager
        fragmentExploreBinding!!.categoryList.addItemDecoration(
            GridSpacingItemDecoration(
                5,
                resources.getDimension(R.dimen.card_radius).toInt(),
                false
            )
        )
    }

    private fun handleAutoScrollSlide() {
        /*After setting the adapter use the timer */
        val handler = Handler()
        val Update = Runnable {
            if (!isPause) {
                if (currentPage == data.size - 1) {
                    currentPage = 0
                }
                fragmentExploreBinding!!.pagerSlider.setCurrentItem(currentPage++, true)
            }
        }
        timer = Timer() // This will create a new Thread
        timer!!.schedule(object : TimerTask() {
            // task to be scheduled
            override fun run() {
                handler.post(Update)
            }
        }, 5000, 5000)
    }

    private fun setUpPopularList() {
        val gridLayoutManager: StaggeredGridLayoutManager =
            object : StaggeredGridLayoutManager(3, VERTICAL) {
                override fun canScrollVertically(): Boolean {
                    return false
                }
            }
        fragmentExploreBinding!!.popularList.layoutManager = gridLayoutManager
        fragmentExploreBinding!!.popularList.addItemDecoration(
            GridSpacingItemDecoration(
                3,
                resources.getDimension(R.dimen.card_radius).toInt(),
                false
            )
        )
        adapterComicVertical = AdapterComicVertical(
            requireContext(),
            data,
            object : AdapterComicVertical.ComicListenerHandler {
                override fun onItemClick(comic: Comic) {
                    Log.d(TAG, "onItemClick: " + comic.title)
                }
            })
        fragmentExploreBinding!!.popularList.adapter = adapterComicVertical
    }

    private fun setUpSlider() {
        sliderAdapter = SliderAdapter(requireContext(), data, object : HandleItemClick {
            override fun onItemClick(comic: Comic) {
                Log.d(TAG, "onItemClick: " + comic.title)
            }

            override fun onActionDown(currentPos: Int) {
                isPause = true
            }
        })
        fragmentExploreBinding!!.pagerSlider.adapter = sliderAdapter
        fragmentExploreBinding!!.pagerSlider.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                currentPage = position
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
        fragmentExploreBinding!!.dotIndicator.setViewPager(fragmentExploreBinding!!.pagerSlider)
    }

    @SuppressLint("ClickableViewAccessibility")
    fun addListener() {
        fragmentExploreBinding!!.pagerSlider.setOnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                isPause = false
            }
            false
        }
        fragmentExploreBinding!!.swipeRefreshLayout.setOnRefreshListener {
            Timer().schedule(object : TimerTask() {
                override fun run() {
                    fragmentExploreBinding!!.swipeRefreshLayout.isRefreshing = false
                }
            }, 200)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context
    }

    override fun onStop() {
        super.onStop()
        if (timer != null) {
            timer!!.cancel()
            timer = null
        }
    }

    override fun onResume() {
        super.onResume()
        if (timer == null) {
            handleAutoScrollSlide()
        }
    }

    private val data: ArrayList<Comic>
        get() {
            val listData = ArrayList<Comic>()
            listData.add(
                Comic(
                    "465465464",
                    "Đấu la đại lục",
                    "Đấu la đại lục",
                    "China",
                    requireContext().resources.getString(R.string.lorem),
                    "https://hhhkungfu.tv/wp-content/uploads/Dau-La-Dai-Luc-2.jpg",
                    "Action, Romance",
                    Author("112", "Tran Anh Vu", "Xinc aho cá ạn"),
                    User()
                )
            )
            listData.add(
                Comic(
                    "465465464",
                    "Sword Art Online",
                    "Sword Art Online",
                    "Japan",
                    requireContext().resources.getString(R.string.lorem),
                    "https://channel.mediacdn.vn/428462621602512896/2023/2/6/photo-1-16756724304802069391968.jpg",
                    "Action, Romance",
                    Author("112", "Tran Anh Vu", "Xinc aho cá ạn"),
                    User()
                )
            )
            listData.add(
                Comic(
                    "465465464",
                    "Đấu phá thương khung",
                    "Đấu phá thương khung",
                    "China",
                    requireContext().resources.getString(R.string.lorem),
                    "https://hhhkungfu.tv/wp-content/uploads/Dau-Pha-Thuong-Khung-5-320x449.jpg",
                    "Action, Romance",
                    Author("112", "Tran Anh Vu", "Xinc aho cá ạn"),
                    User()
                )
            )
            listData.add(
                Comic(
                    "465465464",
                    "Jujutsu Kaisen",
                    "Chú thuật hồi chiến",
                    "Japan",
                    requireContext().resources.getString(R.string.lorem),
                    "https://m.media-amazon.com/images/M/MV5BMTMwMDM4N2EtOTJiYy00OTQ0LThlZDYtYWUwOWFlY2IxZGVjXkEyXkFqcGdeQXVyNjAwNDUxODI@._V1_FMjpg_UX1000_.jpg",
                    "Action, Romance",
                    Author("112", "Tran Anh Vu", "Xinc aho cá ạn"),
                    User()
                )
            )
            listData.add(
                Comic(
                    "465465464",
                    "Tamako: Love Story",
                    "Tamako: Love Story",
                    "Japan",
                    requireContext().resources.getString(R.string.lorem),
                    "https://m.media-amazon.com/images/M/MV5BMTYzOGU4OTItNmU4NC00MmViLWEyNzctMmZkMzgxZjM5MjExXkEyXkFqcGdeQXVyMzgxODM4NjM@._V1_FMjpg_UX1000_.jpg",
                    "Romance",
                    Author("112", "Tran Anh Vu", "Xinc aho cá ạn"),
                    User()
                )
            )
            listData.add(
                Comic(
                    "465465464",
                    "Sword Art Online Progressive 1",
                    "Sword Art Online Progressive 1",
                    "Japan",
                    requireContext().resources.getString(R.string.lorem),
                    "https://upload.wikimedia.org/wikipedia/vi/e/ec/%C3%81p_ph%C3%ADch_phim_Kuruki_Yuuyami_no_Scherzo.jpg",
                    "Action, Romance",
                    Author("112", "Tran Anh Vu", "Xinc aho cá ạn"),
                    User()
                )
            )
            listData.add(
                Comic(
                    "465465464",
                    "Sword Art Online Progressive 2",
                    "Sword Art Online Progressive 2",
                    "Japan",
                    requireContext().resources.getString(R.string.lorem),
                    "https://cdn.galaxycine.vn/media/2022/2/18/img-6357_1645152824607.JPG",
                    "Action, Romance",
                    Author("112", "Tran Anh Vu", "Xinc aho cá ạn"),
                    User()
                )
            )
            listData.add(
                Comic(
                    "465465464",
                    "Mê cung huyền thoại",
                    "Mê cung huyền thoại",
                    "Japan",
                    requireContext().resources.getString(R.string.lorem),
                    "https://upload.wikimedia.org/wikipedia/vi/c/cd/MagiCover01.jpg",
                    "Action, Romance",
                    Author("112", "Tran Anh Vu", "Xinc aho cá ạn"),
                    User()
                )
            )
            listData.add(
                Comic(
                    "465465464",
                    "Đấu phá thương khung",
                    "Đấu phá thương khung",
                    "China",
                    requireContext().resources.getString(R.string.lorem),
                    "https://photo2.tinhte.vn/data/attachment-files/2022/08/6098799_dau-pha-thuong-khung-phan-5-300x450_2.jpg",
                    "Action, Romance",
                    Author("112", "Tran Anh Vu", "Xinc aho cá ạn"),
                    User()
                )
            )
            return listData
        }

    @get:SuppressLint("ResourceType")
    val dataCategory: ArrayList<Category>
        get() {
            val listData = ArrayList<Category>()
            listData.add(
                Category(
                    "55444546",
                    "Hành động",
                    "https://cdn-icons-png.flaticon.com/512/182/182085.png",
                    resources.getString(R.color.category_blue_dark)
                )
            )
            listData.add(
                Category(
                    "55444546",
                    "Trinh thám",
                    "https://cdn-icons-png.flaticon.com/512/182/182085.png",
                    resources.getString(R.color.category_green)
                )
            )
            listData.add(
                Category(
                    "55444546",
                    "Lạng mãn",
                    "https://cdn-icons-png.flaticon.com/512/182/182085.png",
                    resources.getString(R.color.category_green_dark)
                )
            )
            listData.add(
                Category(
                    "55444546",
                    "Học tập",
                    "https://cdn-icons-png.flaticon.com/512/182/182085.png",
                    resources.getString(R.color.category_green_light)
                )
            )
            listData.add(
                Category(
                    "55444546",
                    "Thể thao",
                    "https://cdn-icons-png.flaticon.com/512/182/182085.png",
                    resources.getString(R.color.category_yellow)
                )
            )
            listData.add(
                Category(
                    "55444546",
                    "Trường học",
                    "https://cdn-icons-png.flaticon.com/512/182/182085.png",
                    resources.getString(R.color.category_yellow_dark)
                )
            )
            listData.add(
                Category(
                    "55444546",
                    "Trẻ em",
                    "https://cdn-icons-png.flaticon.com/512/182/182085.png",
                    resources.getString(R.color.category_blue_sky)
                )
            )
            listData.add(
                Category(
                    "55444546",
                    "Tưởng tượng",
                    "https://cdn-icons-png.flaticon.com/512/182/182085.png",
                    resources.getString(R.color.category_pink_dark)
                )
            )
            listData.add(
                Category(
                    "55444546",
                    "Kinh dị",
                    "https://cdn-icons-png.flaticon.com/512/182/182085.png",
                    resources.getString(R.color.category_pink_light)
                )
            )
            listData.add(
                Category(
                    "55444546",
                    "Phép thuật",
                    "https://cdn-icons-png.flaticon.com/512/182/182085.png",
                    resources.getString(R.color.category_red_light)
                )
            )
            return listData
        }

    companion object {
        private val TAG = ExploreFragment::class.java.simpleName

        @JvmStatic
        fun newInstance(): ExploreFragment {
            return ExploreFragment()
        }
    }
}