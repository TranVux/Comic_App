package com.example.assignment.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.assignment.R;
import com.example.assignment.adapters.AdapterComicHorizontal;
import com.example.assignment.models.Author;
import com.example.assignment.models.Comic;
import com.example.assignment.models.User;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class UserFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = UserFragment.class.getSimpleName();
    private AdapterComicHorizontal adapterComicHistory, adapterComicContinue;
    private RecyclerView historyList, continueList;
    private LinearLayout optionChangePass, optionLogInOut;
    private SwipeRefreshLayout swipeRefreshLayout;

    public UserFragment() {
        // Required empty public constructor
    }

    public static UserFragment newInstance() {
        return new UserFragment();
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
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        addEventListener();
    }


    public void init(View view) {
        swipeRefreshLayout = view.findViewById(R.id.swipe_layout);
        continueList = view.findViewById(R.id.continue_list);
        historyList = view.findViewById(R.id.history_list);
        optionChangePass = view.findViewById(R.id.option_change_pass);
        optionLogInOut = view.findViewById(R.id.option_logout);

        setUpHistoryList();
        setUpContinueList();
    }

    public void addEventListener() {
        optionChangePass.setOnClickListener(UserFragment.this);
        optionLogInOut.setOnClickListener(UserFragment.this);

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

    @SuppressLint("ClickableViewAccessibility")
    public void setUpContinueList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        continueList.setLayoutManager(layoutManager);

        adapterComicContinue = new AdapterComicHorizontal(requireContext(), getData(), new AdapterComicHorizontal.ComicListenerHandler() {
            @Override
            public void onItemClick(Comic comic) {
                Log.d(TAG, "onItemClick: " + comic.getTitle());
            }

            @Override
            public void onTouchStart() {

            }
        });

        continueList.setAdapter(adapterComicContinue);
    }


    @SuppressLint("ClickableViewAccessibility")
    public void setUpHistoryList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        historyList.setLayoutManager(layoutManager);

        adapterComicHistory = new AdapterComicHorizontal(requireContext(), getData(), new AdapterComicHorizontal.ComicListenerHandler() {
            @Override
            public void onItemClick(Comic comic) {
                Log.d(TAG, "onItemClick: " + comic.getTitle());
            }

            @Override
            public void onTouchStart() {

            }
        });

        historyList.setAdapter(adapterComicHistory);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.option_change_pass:
                Log.d(TAG, "onClick: changepass");
                break;
            case R.id.option_logout:
                Log.d(TAG, "onClick: log inout");
                break;
        }
    }
}