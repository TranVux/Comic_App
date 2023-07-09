package com.example.assignment.uis.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment.R
import com.example.assignment.adapters.AdapterComicHorizontal
import com.example.assignment.databinding.FragmentUserBinding
import com.example.assignment.models.Author
import com.example.assignment.models.Category
import com.example.assignment.models.Comic
import com.example.assignment.models.User
import java.util.*
import kotlin.collections.ArrayList

class UserFragment : Fragment(), View.OnClickListener {
    private var fragmentUserBinding: FragmentUserBinding? = null
    private var adapterComicHistory: AdapterComicHorizontal? = null
    private var adapterComicContinue: AdapterComicHorizontal? = null
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
        fragmentUserBinding = FragmentUserBinding.inflate(inflater, container, false)
        return fragmentUserBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        addEventListener()
    }

    fun init() {
        setUpHistoryList()
        setUpContinueList()
    }

    private fun addEventListener() {
        fragmentUserBinding!!.optionChangePass.setOnClickListener(this@UserFragment)
        fragmentUserBinding!!.optionLogInOut.setOnClickListener(this@UserFragment)
        fragmentUserBinding!!.swipeRefreshLayout.setOnRefreshListener {
            Timer().schedule(object : TimerTask() {
                override fun run() {
                    fragmentUserBinding!!.swipeRefreshLayout.isRefreshing = false
                }
            }, 200)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    fun setUpContinueList() {
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        fragmentUserBinding!!.continueList.layoutManager = layoutManager
        adapterComicContinue = AdapterComicHorizontal(
            requireContext(),
            data,
            object : AdapterComicHorizontal.ComicListenerHandler {
                override fun onItemClick(comic: Comic) {
                    Log.d(TAG, "onItemClick: " + comic.title)
                }

                override fun onTouchStart() {}
            })
        fragmentUserBinding!!.continueList.adapter = adapterComicContinue
    }

    @SuppressLint("ClickableViewAccessibility")
    fun setUpHistoryList() {
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        fragmentUserBinding!!.historyList.layoutManager = layoutManager
        adapterComicHistory = AdapterComicHorizontal(
            requireContext(),
            data,
            object : AdapterComicHorizontal.ComicListenerHandler {
                override fun onItemClick(comic: Comic) {
                    Log.d(TAG, "onItemClick: " + comic.title)
                }

                override fun onTouchStart() {}
            })
        fragmentUserBinding!!.historyList.adapter = adapterComicHistory
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
                    dataCategory,
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
                    dataCategory,
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
                    dataCategory,
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
                    dataCategory,
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
                    dataCategory,
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
                    dataCategory,
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
                    dataCategory,
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
                    dataCategory,
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
                    dataCategory,
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

    @SuppressLint("NonConstantResourceId")
    override fun onClick(view: View) {
        when (view.id) {
            R.id.option_change_pass -> Log.d(TAG, "onClick: changepass")
            R.id.option_log_in_out -> Log.d(TAG, "onClick: log inout")
        }
    }

    companion object {
        private val TAG = UserFragment::class.java.simpleName

        @JvmStatic
        fun newInstance(): UserFragment {
            return UserFragment()
        }
    }
}