package com.example.assignment.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.assignment.R;
import com.example.assignment.databinding.ActivityDetailBinding;
import com.example.assignment.databinding.ActivityDetailComicBinding;
import com.example.assignment.utils.AppAnimation;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.button.MaterialButton;

public class DetailComicActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = DetailComicActivity.class.getSimpleName();
    private ActivityDetailComicBinding activityDetailComicBinding;
    private boolean isExpandedSynopsisLayout = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDetailComicBinding = ActivityDetailComicBinding.inflate(getLayoutInflater());
        setContentView(activityDetailComicBinding.getRoot());

        init();
        addListenerEvent();
    }

    public void init() {

    }

    public void addListenerEvent() {
        activityDetailComicBinding.expandSynopsisButton.setOnClickListener(DetailComicActivity.this);
        activityDetailComicBinding.readButton.setOnClickListener(DetailComicActivity.this);
        activityDetailComicBinding.favoriteButton.setOnClickListener(DetailComicActivity.this);
    }

    public void handleExpandSynopsis() {
        if (isExpandedSynopsisLayout) {
            int duration = AppAnimation.collapse(activityDetailComicBinding.expandSynopsisLayout, 150, activityDetailComicBinding.maskSynopsis);
            AppAnimation.rotate(activityDetailComicBinding.expandSynopsisButton, 0, 500);
        } else {
            int duration = AppAnimation.expand(activityDetailComicBinding.expandSynopsisLayout, activityDetailComicBinding.maskSynopsis);
            AppAnimation.rotate(activityDetailComicBinding.expandSynopsisButton, 180, 500);
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