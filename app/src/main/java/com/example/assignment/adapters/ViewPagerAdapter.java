package com.example.assignment.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.assignment.fragments.ExploreFragment;
import com.example.assignment.fragments.FavoriteFragment;
import com.example.assignment.fragments.HomeFragment;
import com.example.assignment.fragments.UserFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return HomeFragment.newInstance();
            case 1:
                return ExploreFragment.newInstance();
            case 2:
                return FavoriteFragment.newInstance();
            case 3:
                return UserFragment.newInstance();
        }
        return HomeFragment.newInstance();
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
