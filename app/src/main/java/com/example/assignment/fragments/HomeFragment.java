package com.example.assignment.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.assignment.R;
import com.example.assignment.adapters.AdapterComicHorizontal;
import com.example.assignment.adapters.AdapterComicVertical;
import com.example.assignment.utils.GridSpacingItemDecoration;
import com.example.assignment.adapters.SliderAdapter;
import com.example.assignment.databinding.FragmentHomeBinding;
import com.example.assignment.models.Author;
import com.example.assignment.models.Comic;
import com.example.assignment.models.User;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {
    private static final String TAG = HomeFragment.class.getSimpleName();
    private FragmentHomeBinding fragmentHomeBinding;

    //slider
    private SliderAdapter sliderAdapter;
    private Timer timer;
    private int currentPage = 0;
    private boolean isPause = false;
    // end slider

    private AdapterComicVertical adapterComicVertical;
    private AdapterComicHorizontal adapterComicHorizontal;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false);
        return fragmentHomeBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        addListener();
    }

    public void init() {
        setUpPopularList();
        setupContinueList();
        setUpSlider();
        handleAutoScrollSlide();
    }

    public void handleAutoScrollSlide() {
        /*After setting the adapter use the timer */
        final Handler handler = new Handler();
        final Runnable Update = () -> {
            if (!isPause) {
                if (currentPage == getData().size() - 1) {
                    currentPage = 0;
                }
                fragmentHomeBinding.pagerSlider.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 5000, 5000);
    }


    @SuppressLint("ClickableViewAccessibility")
    public void setupContinueList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        fragmentHomeBinding.continueList.setLayoutManager(layoutManager);

        adapterComicHorizontal = new AdapterComicHorizontal(requireContext(), getData(), new AdapterComicHorizontal.ComicListenerHandler() {
            @Override
            public void onItemClick(Comic comic) {
                Log.d(TAG, "onItemClick: " + comic.getTitle());
            }

            @Override
            public void onTouchStart() {
            }
        });

        fragmentHomeBinding.continueList.setAdapter(adapterComicHorizontal);
    }

    public void setUpPopularList() {
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
       fragmentHomeBinding.popularList.setLayoutManager(gridLayoutManager);

        fragmentHomeBinding.popularList.addItemDecoration(new GridSpacingItemDecoration(3, (int) getResources().getDimension(R.dimen.card_radius), false));

        adapterComicVertical = new AdapterComicVertical(requireContext(), getData(), new AdapterComicVertical.ComicListenerHandler() {
            @Override
            public void onItemClick(Comic comic) {
                Log.d(TAG, "onItemClick: " + comic.getTitle());
            }
        });
        fragmentHomeBinding.popularList.setAdapter(adapterComicVertical);
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setUpSlider() {
        sliderAdapter = new SliderAdapter(requireContext(), getData(), new SliderAdapter.HandleItemClick() {
            @Override
            public void onItemClick(Comic comic) {
                Log.d(TAG, "onItemClick: " + comic.getTitle());
            }

            @Override
            public void onActionDown(int currentPos) {
                //handle scroll for viewpager
                isPause = true;
                fragmentHomeBinding.swipeRefreshLayout.setEnabled(false);
            }
        });

        fragmentHomeBinding.pagerSlider.setAdapter(sliderAdapter);
        fragmentHomeBinding.pagerSlider.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        fragmentHomeBinding.dotIndicator.setViewPager(fragmentHomeBinding.pagerSlider);
    }

    @SuppressLint("ClickableViewAccessibility")
    public void addListener() {
        fragmentHomeBinding.pagerSlider.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    isPause = false;
                }
                return false;
            }
        });

        fragmentHomeBinding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //fake fetch data
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        fragmentHomeBinding.swipeRefreshLayout.setRefreshing(false);
                    }
                }, 200);
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (timer == null) {
            handleAutoScrollSlide();
        }
    }

    public ArrayList<Comic> getData() {
        ArrayList<Comic> listData = new ArrayList<>();

        listData.add(new Comic("465465464", "Đấu la đại lục", "Đấu la đại lục", "China", requireContext().getResources().getString(R.string.lorem),
                "https://hhhkungfu.tv/wp-content/uploads/Dau-La-Dai-Luc-2.jpg", "Action, Romance", new Author(), new User()));
        listData.add(new Comic("465465464", "Sword Art Online", "Sword Art Online", "Japan", requireContext().getResources().getString(R.string.lorem),
                "https://channel.mediacdn.vn/428462621602512896/2023/2/6/photo-1-16756724304802069391968.jpg", "Action, Romance", new Author(), new User()));
        listData.add(new Comic("465465464", "Đấu phá thương khung", "Đấu phá thương khung", "China", requireContext().getResources().getString(R.string.lorem),
                "https://hhhkungfu.tv/wp-content/uploads/Dau-Pha-Thuong-Khung-5-320x449.jpg", "Action, Romance", new Author(), new User()));

        listData.add(new Comic("465465464", "Jujutsu Kaisen", "Chú thuật hồi chiến", "Japan", requireContext().getResources().getString(R.string.lorem),
                "https://m.media-amazon.com/images/M/MV5BMTMwMDM4N2EtOTJiYy00OTQ0LThlZDYtYWUwOWFlY2IxZGVjXkEyXkFqcGdeQXVyNjAwNDUxODI@._V1_FMjpg_UX1000_.jpg", "Action, Romance", new Author(), new User()));

        listData.add(new Comic("465465464", "Tamako: Love Story", "Tamako: Love Story", "Japan", requireContext().getResources().getString(R.string.lorem),
                "https://m.media-amazon.com/images/M/MV5BMTYzOGU4OTItNmU4NC00MmViLWEyNzctMmZkMzgxZjM5MjExXkEyXkFqcGdeQXVyMzgxODM4NjM@._V1_FMjpg_UX1000_.jpg", "Romance", new Author(), new User()));
        listData.add(new Comic("465465464", "Sword Art Online Progressive 1", "Sword Art Online Progressive 1", "Japan", requireContext().getResources().getString(R.string.lorem),
                "https://upload.wikimedia.org/wikipedia/vi/e/ec/%C3%81p_ph%C3%ADch_phim_Kuruki_Yuuyami_no_Scherzo.jpg", "Action, Romance", new Author(), new User()));
        listData.add(new Comic("465465464", "Sword Art Online Progressive 2", "Sword Art Online Progressive 2", "Japan", requireContext().getResources().getString(R.string.lorem),
                "https://cdn.galaxycine.vn/media/2022/2/18/img-6357_1645152824607.JPG", "Action, Romance", new Author(), new User()));
        listData.add(new Comic("465465464", "Mê cung huyền thoại", "Mê cung huyền thoại", "Japan", requireContext().getResources().getString(R.string.lorem),
                "https://upload.wikimedia.org/wikipedia/vi/c/cd/MagiCover01.jpg", "Action, Romance", new Author(), new User()));
        listData.add(new Comic("465465464", "Đấu phá thương khung", "Đấu phá thương khung", "China", requireContext().getResources().getString(R.string.lorem),
                "https://photo2.tinhte.vn/data/attachment-files/2022/08/6098799_dau-pha-thuong-khung-phan-5-300x450_2.jpg", "Action, Romance", new Author(), new User()));
        return listData;
    }
}