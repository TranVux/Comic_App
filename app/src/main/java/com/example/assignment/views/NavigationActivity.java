package com.example.assignment.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.assignment.R;
import com.example.assignment.adapters.ViewPagerAdapter;
import com.example.assignment.components.HeaderApp;
import com.example.assignment.databinding.ActivityNavigationBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class NavigationActivity extends FragmentActivity {
    private static final String TAG = NavigationActivity.class.getSimpleName();
    private ActivityNavigationBinding activityNavigationBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityNavigationBinding = ActivityNavigationBinding.inflate(getLayoutInflater());
        setContentView(activityNavigationBinding.getRoot());

        init();
        addEvent();
    }

    public void init() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(NavigationActivity.this);
        activityNavigationBinding.viewPager.setAdapter(viewPagerAdapter);

        activityNavigationBinding.viewPager.setUserInputEnabled(false);
    }

    public void addEvent() {
        activityNavigationBinding.bottomTab.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.homeFragment:
                        activityNavigationBinding.viewPager.setCurrentItem(0);
                        break;
                    case R.id.exploreFragment:
                        activityNavigationBinding.viewPager.setCurrentItem(1);
                        break;
                    case R.id.favoriteFragment:
                        activityNavigationBinding.viewPager.setCurrentItem(2);
                        break;
                    case R.id.userFragment:
                        activityNavigationBinding.viewPager.setCurrentItem(3);
                        break;
                }
                handleHeaderApp(activityNavigationBinding.viewPager.getCurrentItem());
                return true;
            }
        });
    }

    public void handleHeaderApp(int currentTabIndex) {
        if (currentTabIndex == 3) {
            activityNavigationBinding.headerApp.setHeaderAppVisibility(View.GONE);
        } else {
            activityNavigationBinding.headerApp.setHeaderAppVisibility(View.VISIBLE);
        }
    }
}