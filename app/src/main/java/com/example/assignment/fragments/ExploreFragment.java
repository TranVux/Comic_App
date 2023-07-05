package com.example.assignment.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
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
import com.example.assignment.adapters.AdapterCategory;
import com.example.assignment.adapters.AdapterComicVertical;
import com.example.assignment.adapters.GridSpacingItemDecoration;
import com.example.assignment.adapters.SliderAdapter;
import com.example.assignment.models.Author;
import com.example.assignment.models.Category;
import com.example.assignment.models.Comic;
import com.example.assignment.models.User;
import com.example.assignment.views.NavigationActivity;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ExploreFragment extends Fragment {

    private static final String TAG = ExploreFragment.class.getSimpleName();

    private Context context;
    private ViewPager slider;
    private DotsIndicator dotsIndicator;
    //slide
    private SliderAdapter sliderAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Timer timer;
    private int currentPage = 0;
    private boolean isPause = false;
    //slide
    private RecyclerView popularList, categoryList;
    private AdapterComicVertical adapterComicVertical;
    private AdapterCategory adapterCategory;

    public ExploreFragment() {
        // Required empty public constructor
    }

    public static ExploreFragment newInstance() {
        return new ExploreFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_explore, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);
        addListener();
    }

    public void init(View view) {
        slider = view.findViewById(R.id.pager_slider);
        dotsIndicator = view.findViewById(R.id.dot_indicator);
        popularList = view.findViewById(R.id.popular_list);
        categoryList = view.findViewById(R.id.category_list);
        swipeRefreshLayout = view.findViewById(R.id.swipe_layout);

        setUpPopularList();
        setUpSlider();
        setUpListCategory();

        handleAutoScrollSlide();
    }

    public void setUpListCategory() {
        adapterCategory = new AdapterCategory(requireContext(), getDataCategory(), new AdapterCategory.CategoryHandlerListener() {
            @Override
            public void onItemClick(Category category) {
                Log.d(TAG, "onItemClick: " + category.getTitle());
            }
        });

        categoryList.setAdapter(adapterCategory);

        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        categoryList.setLayoutManager(gridLayoutManager);

        categoryList.addItemDecoration(new GridSpacingItemDecoration(5, (int) getResources().getDimension(R.dimen.card_radius), false));
    }

    public void handleAutoScrollSlide() {
        /*After setting the adapter use the timer */
        final Handler handler = new Handler();
        final Runnable Update = () -> {
            if (!isPause) {
                if (currentPage == getData().size() - 1) {
                    currentPage = 0;
                }
                slider.setCurrentItem(currentPage++, true);
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


    public void setUpPopularList() {
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        popularList.setLayoutManager(gridLayoutManager);

        popularList.addItemDecoration(new GridSpacingItemDecoration(3, (int) getResources().getDimension(R.dimen.card_radius), false));
        adapterComicVertical = new AdapterComicVertical(requireContext(), getData(), new AdapterComicVertical.ComicListenerHandler() {
            @Override
            public void onItemClick(Comic comic) {
                Log.d(TAG, "onItemClick: " + comic.getTitle());
            }
        });
        popularList.setAdapter(adapterComicVertical);
    }

    public void setUpSlider() {
        sliderAdapter = new SliderAdapter(requireContext(), getData(), new SliderAdapter.HandleItemClick() {
            @Override
            public void onItemClick(Comic comic) {
                Log.d(TAG, "onItemClick: " + comic.getTitle());
            }

            @Override
            public void onActionDown(int currentPos) {
                isPause = true;
            }
        });

        slider.setAdapter(sliderAdapter);

        slider.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

        dotsIndicator.setViewPager(slider);
    }

    @SuppressLint("ClickableViewAccessibility")
    public void addListener() {
        slider.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    isPause = false;
                }
                return false;
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 200);
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
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
        listData.add(new Comic("465465464", "Đấu la đại lục", "Đấu la đại lục", "China", context.getResources().getString(R.string.lorem),
                "https://hhhkungfu.tv/wp-content/uploads/Dau-La-Dai-Luc-2.jpg", "Action, Romance", new Author(), new User()));
        listData.add(new Comic("465465464", "Sword Art Online", "Sword Art Online", "Japan", context.getResources().getString(R.string.lorem),
                "https://channel.mediacdn.vn/428462621602512896/2023/2/6/photo-1-16756724304802069391968.jpg", "Action, Romance", new Author(), new User()));
        listData.add(new Comic("465465464", "Đấu phá thương khung", "Đấu phá thương khung", "China", context.getResources().getString(R.string.lorem),
                "https://hhhkungfu.tv/wp-content/uploads/Dau-Pha-Thuong-Khung-5-320x449.jpg", "Action, Romance", new Author(), new User()));

        listData.add(new Comic("465465464", "Jujutsu Kaisen", "Chú thuật hồi chiến", "Japan", context.getResources().getString(R.string.lorem),
                "https://m.media-amazon.com/images/M/MV5BMTMwMDM4N2EtOTJiYy00OTQ0LThlZDYtYWUwOWFlY2IxZGVjXkEyXkFqcGdeQXVyNjAwNDUxODI@._V1_FMjpg_UX1000_.jpg", "Action, Romance", new Author(), new User()));

        listData.add(new Comic("465465464", "Tamako: Love Story", "Tamako: Love Story", "Japan", context.getResources().getString(R.string.lorem),
                "https://m.media-amazon.com/images/M/MV5BMTYzOGU4OTItNmU4NC00MmViLWEyNzctMmZkMzgxZjM5MjExXkEyXkFqcGdeQXVyMzgxODM4NjM@._V1_FMjpg_UX1000_.jpg", "Romance", new Author(), new User()));
        listData.add(new Comic("465465464", "Sword Art Online Progressive 1", "Sword Art Online Progressive 1", "Japan", context.getResources().getString(R.string.lorem),
                "https://upload.wikimedia.org/wikipedia/vi/e/ec/%C3%81p_ph%C3%ADch_phim_Kuruki_Yuuyami_no_Scherzo.jpg", "Action, Romance", new Author(), new User()));
        listData.add(new Comic("465465464", "Sword Art Online Progressive 2", "Sword Art Online Progressive 2", "Japan", context.getResources().getString(R.string.lorem),
                "https://cdn.galaxycine.vn/media/2022/2/18/img-6357_1645152824607.JPG", "Action, Romance", new Author(), new User()));
        listData.add(new Comic("465465464", "Mê cung huyền thoại", "Mê cung huyền thoại", "Japan", context.getResources().getString(R.string.lorem),
                "https://upload.wikimedia.org/wikipedia/vi/c/cd/MagiCover01.jpg", "Action, Romance", new Author(), new User()));
        listData.add(new Comic("465465464", "Đấu phá thương khung", "Đấu phá thương khung", "China", context.getResources().getString(R.string.lorem),
                "https://photo2.tinhte.vn/data/attachment-files/2022/08/6098799_dau-pha-thuong-khung-phan-5-300x450_2.jpg", "Action, Romance", new Author(), new User()));
        return listData;
    }

    @SuppressLint("ResourceType")
    public ArrayList<Category> getDataCategory() {
        ArrayList<Category> listData = new ArrayList<>();

        listData.add(new Category("55444546", "Hành động", "https://cdn-icons-png.flaticon.com/512/182/182085.png", getResources().getString(R.color.category_blue_dark)));
        listData.add(new Category("55444546", "Trinh thám", "https://cdn-icons-png.flaticon.com/512/182/182085.png", getResources().getString(R.color.category_green)));
        listData.add(new Category("55444546", "Lạng mãn", "https://cdn-icons-png.flaticon.com/512/182/182085.png", getResources().getString(R.color.category_green_dark)));
        listData.add(new Category("55444546", "Học tập", "https://cdn-icons-png.flaticon.com/512/182/182085.png", getResources().getString(R.color.category_green_light)));
        listData.add(new Category("55444546", "Thể thao", "https://cdn-icons-png.flaticon.com/512/182/182085.png", getResources().getString(R.color.category_yellow)));
        listData.add(new Category("55444546", "Trường học", "https://cdn-icons-png.flaticon.com/512/182/182085.png", getResources().getString(R.color.category_yellow_dark)));
        listData.add(new Category("55444546", "Trẻ em", "https://cdn-icons-png.flaticon.com/512/182/182085.png", getResources().getString(R.color.category_blue_sky)));
        listData.add(new Category("55444546", "Tưởng tượng", "https://cdn-icons-png.flaticon.com/512/182/182085.png", getResources().getString(R.color.category_pink_dark)));
        listData.add(new Category("55444546", "Kinh dị", "https://cdn-icons-png.flaticon.com/512/182/182085.png", getResources().getString(R.color.category_pink_light)));
        listData.add(new Category("55444546", "Phép thuật", "https://cdn-icons-png.flaticon.com/512/182/182085.png", getResources().getString(R.color.category_red_light)));

        return listData;
    }

}