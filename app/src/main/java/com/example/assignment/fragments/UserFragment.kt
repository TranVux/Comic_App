package com.example.assignment.fragments

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