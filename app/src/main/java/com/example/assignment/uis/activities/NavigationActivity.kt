package com.example.assignment.uis.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.example.assignment.R
import com.example.assignment.adapters.ViewPagerAdapter
import com.example.assignment.apis.RetrofitInstance
import com.example.assignment.databinding.ActivityNavigationBinding
import com.example.assignment.data.ComicAppDatabase
import com.example.assignment.data.repositorys.TestRepository
import com.example.assignment.utils.Resource
import com.example.assignment.utils.Utils
import com.google.android.material.navigation.NavigationBarView
import kotlinx.coroutines.*

class NavigationActivity() : FragmentActivity() {
    private val activityNavigationBinding: ActivityNavigationBinding by lazy {
        ActivityNavigationBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityNavigationBinding.root)
        init()
        addEvent()
    }

    fun init() {
        val viewPagerAdapter = ViewPagerAdapter(this@NavigationActivity)
        activityNavigationBinding.viewPager.adapter = viewPagerAdapter
        activityNavigationBinding.viewPager.isUserInputEnabled = false
    }

    private fun testAPI() {
        //Test API
        val testRepository = TestRepository(
            RetrofitInstance.testApi,
            ComicAppDatabase.getInstance(this@NavigationActivity)
        )

        testRepository.getTest().asLiveData().observe(this, Observer {
            for (data in it.data!!) {
                Utils.logD(data.bank_name)
            }
        })
    }

    private fun addEvent() {
        activityNavigationBinding.bottomTab.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> activityNavigationBinding.viewPager.currentItem = 0
                R.id.exploreFragment -> activityNavigationBinding.viewPager.currentItem = 1
                R.id.favoriteFragment -> activityNavigationBinding.viewPager.currentItem = 2
                R.id.userFragment -> activityNavigationBinding.viewPager.currentItem = 3
            }
            handleHeaderApp(activityNavigationBinding.viewPager.currentItem)
            true
        })
    }

    private fun handleHeaderApp(currentTabIndex: Int) {
        if (currentTabIndex == 3) {
            activityNavigationBinding.headerApp.setHeaderAppVisibility(View.GONE)
        } else {
            activityNavigationBinding.headerApp.setHeaderAppVisibility(View.VISIBLE)
        }
    }

    companion object {
        private val TAG = NavigationActivity::class.java.simpleName
    }
}