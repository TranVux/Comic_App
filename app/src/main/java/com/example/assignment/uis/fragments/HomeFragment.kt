package com.example.assignment.uis.fragments

import android.annotation.SuppressLint
import android.content.Intent
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
import com.example.assignment.adapters.AdapterComicVertical
import com.example.assignment.adapters.SliderAdapter
import com.example.assignment.adapters.SliderAdapter.HandleItemClick
import com.example.assignment.databinding.FragmentHomeBinding
import com.example.assignment.models.Comic
import com.example.assignment.uis.activities.DetailComicActivity
import com.example.assignment.utils.GridSpacingItemDecoration
import com.example.assignment.viewmodels.ComicViewModel
import java.util.*

class HomeFragment : Fragment() {
    private var fragmentHomeBinding: FragmentHomeBinding? = null

    //slider
    private var sliderAdapter: SliderAdapter? = null
    private var timer: Timer? = null
    private var currentPage = 0
    private var isPause = false

    // end slider
    private var adapterComicVertical: AdapterComicVertical? = null

    //comic viewModel
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
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return fragmentHomeBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        addListener()
        fetchData();
    }

    fun init() {
        setUpPopularList()
        setUpSlider()
        handleAutoScrollSlide()
    }

    private fun handleAutoScrollSlide() {
        /*After setting the adapter use the timer */
        sliderAdapter?.let {
            val handler = Handler()
            val update = Runnable {
                if (!isPause) {
                    if (currentPage == it.getList().size - 1) {
                        currentPage = 0
                    }
                    fragmentHomeBinding!!.pagerSlider.setCurrentItem(currentPage++, true)
                }
            }
            timer = Timer() // This will create a new Thread
            timer!!.schedule(object : TimerTask() {
                // task to be scheduled
                override fun run() {
                    handler.post(update)
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
        fragmentHomeBinding!!.popularList.layoutManager = gridLayoutManager
        fragmentHomeBinding!!.popularList.addItemDecoration(
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
                    val dataIntent: Intent =
                        Intent(requireContext(), DetailComicActivity::class.java)
                    dataIntent.putExtra("comic_model", comic)
                    requireContext().startActivity(dataIntent)
                }
            })
        fragmentHomeBinding!!.popularList.adapter = adapterComicVertical
    }

    @SuppressLint("ClickableViewAccessibility")
    fun setUpSlider() {
        sliderAdapter = SliderAdapter(requireContext(), ArrayList(), object : HandleItemClick {
            override fun onItemClick(comic: Comic) {
                val dataIntent: Intent = Intent(requireContext(), DetailComicActivity::class.java)
                dataIntent.putExtra("comic_model", comic)
                requireContext().startActivity(dataIntent)
            }

            override fun onActionDown(currentPos: Int) {
                //handle scroll for viewpager
                isPause = true
                fragmentHomeBinding!!.swipeRefreshLayout.isEnabled = false
            }
        })
        fragmentHomeBinding!!.pagerSlider.adapter = sliderAdapter
        fragmentHomeBinding!!.pagerSlider.addOnPageChangeListener(object : OnPageChangeListener {
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
        fragmentHomeBinding!!.dotIndicator.setViewPager(fragmentHomeBinding!!.pagerSlider)
    }

    @SuppressLint("ClickableViewAccessibility")
    fun addListener() {
        fragmentHomeBinding!!.pagerSlider.setOnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                isPause = false
            }
            false
        }
        fragmentHomeBinding!!.swipeRefreshLayout.setOnRefreshListener { //fake fetch data
            Timer().schedule(object : TimerTask() {
                override fun run() {
                    fragmentHomeBinding!!.swipeRefreshLayout.isRefreshing = false
                }
            }, 200)
        }
    }

    private fun fetchData() {
        //for vertical list
        comicViewModel.getAllComic(onSuccess = { result ->
            adapterComicVertical?.setListComic(result.result as ArrayList<Comic>)
        }, onError = { error ->
            Log.e(TAG, "addListener: $error")
        })

        //for slider
        comicViewModel.getSliderComic(onSuccess = { result ->
            sliderAdapter?.setList(result.result as ArrayList<Comic>)
        }, onError = { error ->
            Log.e(TAG, "addListener: $error")
        })
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
        private val TAG = HomeFragment::class.java.simpleName

        @JvmStatic
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }
}