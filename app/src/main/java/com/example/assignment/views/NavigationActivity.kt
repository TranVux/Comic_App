package com.example.assignment.views

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import com.example.assignment.R
import com.example.assignment.adapters.ViewPagerAdapter
import com.example.assignment.databinding.ActivityNavigationBinding
import com.google.android.material.navigation.NavigationBarView

class NavigationActivity() : FragmentActivity() {
    private var activityNavigationBinding: ActivityNavigationBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityNavigationBinding = ActivityNavigationBinding.inflate(
            layoutInflater
        )
        setContentView(activityNavigationBinding!!.root)
        init()
        addEvent()
    }

    fun init() {
        val viewPagerAdapter = ViewPagerAdapter(this@NavigationActivity)
        activityNavigationBinding!!.viewPager.adapter = viewPagerAdapter
        activityNavigationBinding!!.viewPager.isUserInputEnabled = false
    }

    private fun addEvent() {
        activityNavigationBinding!!.bottomTab.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> activityNavigationBinding!!.viewPager.currentItem = 0
                R.id.exploreFragment -> activityNavigationBinding!!.viewPager.currentItem = 1
                R.id.favoriteFragment -> activityNavigationBinding!!.viewPager.currentItem = 2
                R.id.userFragment -> activityNavigationBinding!!.viewPager.currentItem = 3
            }
            handleHeaderApp(activityNavigationBinding!!.viewPager.currentItem)
            true
        })
    }

    private fun handleHeaderApp(currentTabIndex: Int) {
        if (currentTabIndex == 3) {
            activityNavigationBinding!!.headerApp.setHeaderAppVisibility(View.GONE)
        } else {
            activityNavigationBinding!!.headerApp.setHeaderAppVisibility(View.VISIBLE)
        }
    }

    companion object {
        private val TAG = NavigationActivity::class.java.simpleName
    }
}