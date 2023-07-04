package com.example.assignment.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.assignment.R;
import com.example.assignment.utils.AppAnimation;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.button.MaterialButton;

public class DetailComicActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = DetailComicActivity.class.getSimpleName();

    private CardView synopsisLayout;
    private MaterialButton toggleExpandButton, readButton, favoriteButton;
    private View maskSynopsis;
    TextView countryText, authorText, subTitleText, synopsisText, titleText, publishByText, categoriesText;
    private boolean isExpandedSynopsisLayout = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_comic);

        init();
        addListenerEvent();
    }

    public void init() {
        favoriteButton = findViewById(R.id.favorite_button);
        readButton = findViewById(R.id.read_button);
        toggleExpandButton = findViewById(R.id.expand_synopsis_button);

        synopsisLayout = findViewById(R.id.expand_synopsis_layout);
        maskSynopsis = findViewById(R.id.mask_synopsis);

        countryText = findViewById(R.id.text_country);
        titleText = findViewById(R.id.title_comic);
        categoriesText = findViewById(R.id.category_comic);
        synopsisText = findViewById(R.id.text_synopsis);
        subTitleText = findViewById(R.id.text_sub_title);
        authorText = findViewById(R.id.text_author);
        publishByText = findViewById(R.id.publish_by);
    }

    public void addListenerEvent() {
        toggleExpandButton.setOnClickListener(DetailComicActivity.this);
        readButton.setOnClickListener(DetailComicActivity.this);
        favoriteButton.setOnClickListener(DetailComicActivity.this);
    }

    public void handleExpandSynopsis() {
        if (isExpandedSynopsisLayout) {
            int duration = AppAnimation.collapse(synopsisLayout, 150, maskSynopsis);
            AppAnimation.rotate(toggleExpandButton, 0, 500);
        } else {
            int duration = AppAnimation.expand(synopsisLayout, maskSynopsis);
            AppAnimation.rotate(toggleExpandButton, 180, 500);
        }
        isExpandedSynopsisLayout = !isExpandedSynopsisLayout;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.expand_synopsis_button:
                handleExpandSynopsis();
                break;
            case R.id.read_button:
                Log.d(TAG, "onClick: read_button");
                break;
            case R.id.favorite_button:
                Log.d(TAG, "onClick: favorite_button");
                break;
        }
    }
}