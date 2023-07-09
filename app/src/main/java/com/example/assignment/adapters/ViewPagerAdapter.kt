package com.example.assignment.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.assignment.uis.fragments.ExploreFragment
import com.example.assignment.uis.fragments.FavoriteFragment
import com.example.assignment.uis.fragments.HomeFragment
import com.example.assignment.uis.fragments.UserFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return HomeFragment.newInstance()
            1 -> return ExploreFragment.newInstance()
            2 -> return FavoriteFragment.newInstance()
            3 -> return UserFragment.newInstance()
        }
        return HomeFragment.newInstance()
    }

    override fun getItemCount(): Int {
        return 4
    }
}