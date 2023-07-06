package com.example.assignment.views;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.assignment.R;
import com.example.assignment.databinding.ActivityDetailBinding;
import com.example.assignment.fragments.OptionBottomSheetDialog;
import com.example.assignment.fragments.OptionBottomSheetDialog.*;
import com.example.assignment.models.Category;

import java.util.ArrayList;
import java.util.Objects;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = DetailActivity.class.getSimpleName();
    private ActivityDetailBinding activityDetailBinding;
    private int defaultSelectedItemCategoryIndex = 0;
    private int defaultSelectedItemCountryIndex = 0;
    private String textCategorySelected = "";
    private String textCountrySelected = "";
    private String textDecoration = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDetailBinding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(activityDetailBinding.getRoot());

        init();
        addEventListener();
    }

    public void init() {
    }

    public void addEventListener() {
        activityDetailBinding.buttonCountry.setOnClickListener(DetailActivity.this);
        activityDetailBinding.buttonCategory.setOnClickListener(DetailActivity.this);
    }

    public void handleBottomSheetOption(OptionBottomSheetType type) {
        OptionBottomSheetDialog optionBottomSheetDialog = OptionBottomSheetDialog
                .newInstance(
                        type == OptionBottomSheetType.COUNTRY ? "Quốc gia" : "Thể loại",
                        type == OptionBottomSheetType.COUNTRY ? getCountries() : getDataCategory(),
                        type,
                        type == OptionBottomSheetType.COUNTRY ? defaultSelectedItemCountryIndex : defaultSelectedItemCategoryIndex
                ).setHandlerDismissListener(new HandlerDismissListener() {
                    @Override
                    public void onDismiss(OptionBottomSheetType type, Object value, int selectedItemIndex) {
                        if (type == OptionBottomSheetType.CATEGORY) {
                            defaultSelectedItemCategoryIndex = selectedItemIndex;
                            Log.d(TAG, "onDismiss: " + ((Category) value).getTitle());
                            handleOption(type, value);
                        } else if (type == OptionBottomSheetType.COUNTRY) {
                            defaultSelectedItemCountryIndex = selectedItemIndex;
                            handleOption(type, value);
                            Log.d(TAG, "onDismiss: " + ((String) value));
                        }
                    }
                });

        optionBottomSheetDialog.show(getSupportFragmentManager(), optionBottomSheetDialog.getTag());
    }

    public void handleOption(OptionBottomSheetType type, Object value) {
        if (type == OptionBottomSheetType.CATEGORY) {
            if (!Objects.equals(((Category) value).getTitle(), "Mặc định")) {
                textCategorySelected = ((Category) value).getTitle();
            } else {
                textCategorySelected = "";
            }
        } else if (type == OptionBottomSheetType.COUNTRY) {
            if (!Objects.equals((value.toString()), "Mặc định")) {
                textCountrySelected = value.toString();
            } else {
                textCountrySelected = "";
            }
        }

        if (Objects.equals(textCountrySelected, "") && Objects.equals(textCategorySelected, "")) {
            textDecoration = "";
            activityDetailBinding.choice.setVisibility(View.GONE);
        } else {
            activityDetailBinding.choice.setVisibility(View.VISIBLE);
            if (Objects.equals(textCountrySelected, "") || Objects.equals(textCategorySelected, "")) {
                textDecoration = "";
                activityDetailBinding.choice.setText("Đã chọn: ".concat(textCategorySelected).concat(textDecoration).concat(textCountrySelected));
            } else {
                textDecoration = " • ";
                activityDetailBinding.choice.setText("Đã chọn: ".concat(textCategorySelected).concat(textDecoration).concat(textCountrySelected));
            }
        }
    }

    public ArrayList<String> getCountries() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Mặc định");
        list.add("Japan");
        list.add("China");
        list.add("Việt Nam");
        return list;
    }

    @SuppressLint("ResourceType")
    public ArrayList<Category> getDataCategory() {
        ArrayList<Category> listData = new ArrayList<>();

        listData.add(new Category("55444546", "Mặc định", "https://cdn-icons-png.flaticon.com/512/182/182085.png", getResources().getString(R.color.category_blue_dark)));
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_category:
                Log.d(TAG, "onClick: button_category");
                handleBottomSheetOption(OptionBottomSheetType.CATEGORY);
                break;
            case R.id.button_country:
                Log.d(TAG, "onClick: button_country");
                handleBottomSheetOption(OptionBottomSheetType.COUNTRY);
                break;
        }
    }
}