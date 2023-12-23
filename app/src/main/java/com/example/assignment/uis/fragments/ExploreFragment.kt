package com.example.assignment.uis.fragments

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
import androidx.lifecycle.ViewModelProvider
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
import com.example.assignment.viewmodels.CategoryViewModel
import com.example.assignment.viewmodels.ComicViewModel
import java.util.*
import kotlin.collections.ArrayList

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
    private var binding: FragmentExploreBinding? = null

    //view model
    private val categoryViewModel: CategoryViewModel by lazy {
        ViewModelProvider(
            this,
            CategoryViewModel.CategoryViewModelFactory(requireActivity().application)
        )[CategoryViewModel::class.java]
    }

    private val comicViewModel: ComicViewModel by lazy {
        ViewModelProvider(
            this,
            ComicViewModel.ComicViewModelFactory(requireActivity().application)
        )[ComicViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentExploreBinding.inflate(inflater, container, false)
        return binding!!.root
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
        fetchData()
    }

    private fun setUpListCategory() {
        adapterCategory = AdapterCategory(
            requireContext(),
            ArrayList(),
            object : AdapterCategory.CategoryHandlerListener {
                override fun onItemClick(category: Category) {
                    Log.d(
                        TAG,
                        "onItemClick: " + category.title
                    )
                }
            })
        binding?.categoryList?.adapter = adapterCategory
        val gridLayoutManager: StaggeredGridLayoutManager =
            object : StaggeredGridLayoutManager(5, VERTICAL) {
                override fun canScrollVertically(): Boolean {
                    return false
                }
            }

        binding!!.categoryList.layoutManager = gridLayoutManager
        binding!!.categoryList.addItemDecoration(
            GridSpacingItemDecoration(
                5,
                resources.getDimension(R.dimen.card_radius).toInt(),
                false
            )
        )
    }

    private fun handleAutoScrollSlide() {
        /*After setting the adapter use the timer */
        sliderAdapter?.getList()?.let {
            val handler = Handler()
            val Update = Runnable {
                if (!isPause) {
                    if (currentPage == it.size - 1) {
                        currentPage = 0
                    }
                    binding!!.pagerSlider.setCurrentItem(currentPage++, true)
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
    }

    private fun setUpPopularList() {
        val gridLayoutManager: StaggeredGridLayoutManager =
            object : StaggeredGridLayoutManager(3, VERTICAL) {
                override fun canScrollVertically(): Boolean {
                    return false
                }
            }
        binding!!.popularList.layoutManager = gridLayoutManager
        binding!!.popularList.addItemDecoration(
            GridSpacingItemDecoration(
                3,
                resources.getDimension(R.dimen.card_radius).toInt(),
                false
            )
        )
        adapterComicVertical = AdapterComicVertical(
            requireContext(),
            ArrayList(),
            object : AdapterComicVertical.ComicListenerHandler {
                override fun onItemClick(comic: Comic) {
                    Log.d(TAG, "onItemClick: " + comic.title)
                }
            })
        binding!!.popularList.adapter = adapterComicVertical
    }

    private fun setUpSlider() {
        sliderAdapter = SliderAdapter(requireContext(), ArrayList(), object : HandleItemClick {
            override fun onItemClick(comic: Comic) {
                Log.d(TAG, "onItemClick: " + comic.title)
            }

            override fun onActionDown(currentPos: Int) {
                isPause = true
            }
        })
        binding!!.pagerSlider.adapter = sliderAdapter
        binding!!.pagerSlider.addOnPageChangeListener(object : OnPageChangeListener {
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
        binding!!.dotIndicator.setViewPager(binding!!.pagerSlider)
    }

    private fun fetchData() {
        //for vertical list
        comicViewModel.getAllComic(onSuccess = { result ->
            adapterComicVertical?.setListComic(result.result as java.util.ArrayList<Comic>)
        }, onError = { error ->
            Log.e(TAG, "addListener: $error")
        })

        //for slider
        comicViewModel.getSliderComic(onSuccess = { result ->
            sliderAdapter?.setList(result.result as java.util.ArrayList<Comic>)
        }, onError = { error ->
            Log.e(TAG, "addListener: $error")
        })

        //for category list
        categoryViewModel.getCategory(onSuccess = { res ->
            adapterCategory?.setListCategory(res.result as ArrayList<Category>)
        }, onError = { error ->
            Log.e(TAG, "fetchData: $error")
        })
    }


    @SuppressLint("ClickableViewAccessibility")
    fun addListener() {
        binding!!.pagerSlider.setOnTouchListener { _, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                isPause = false
            }
            false
        }
        binding!!.swipeRefreshLayout.setOnRefreshListener {
            Timer().schedule(object : TimerTask() {
                override fun run() {
                    binding!!.swipeRefreshLayout.isRefreshing = false
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

    companion object {
        private val TAG = ExploreFragment::class.java.simpleName

        @JvmStatic
        fun newInstance(): ExploreFragment {
            return ExploreFragment()
        }
    }
}