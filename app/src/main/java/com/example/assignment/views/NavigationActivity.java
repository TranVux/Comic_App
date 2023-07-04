package com.example.assignment.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.assignment.R;
import com.example.assignment.adapters.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class NavigationActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private ViewPager2 pager2;
    private static final String TAG = NavigationActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        init();
        addEvent();
    }

    public void init() {
        bottomNavigationView = findViewById(R.id.bottomTab);

        pager2 = findViewById(R.id.view_pager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(NavigationActivity.this);
        pager2.setAdapter(viewPagerAdapter);

        pager2.setUserInputEnabled(false);
    }

    public void addEvent() {
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.homeFragment:
                        pager2.setCurrentItem(0);
                        break;
                    case R.id.exploreFragment:
                        pager2.setCurrentItem(1);
                        break;
                    case R.id.favoriteFragment:
                        pager2.setCurrentItem(2);
                        break;
                    case R.id.userFragment:
                        pager2.setCurrentItem(3);
                        break;
                }
                return true;
            }
        });
    }
}