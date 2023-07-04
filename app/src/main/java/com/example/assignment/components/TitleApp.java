package com.example.assignment.components;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.assignment.R;

import java.util.Objects;

public class TitleApp extends LinearLayout implements View.OnClickListener {
    static private final String TAG = TitleApp.class.getSimpleName();
    private TextView title, subTitle;
    private LinearLayout moreButton;
    private TitleButtonHandler titleButtonHandler;

    public TitleApp(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.title_app_layout, this);
        TypedArray style = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.TitleApp, 0, 0);
        init(style);
    }

    public TitleApp(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.title_app_layout, this);
        TypedArray style = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.TitleApp, 0, 0);
        init(style);
    }

    public TitleApp(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        LayoutInflater.from(context).inflate(R.layout.title_app_layout, this);
        TypedArray style = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.TitleApp, 0, 0);
        init(style);
    }

    public void init(TypedArray style) {
        title = findViewById(R.id.title);
        subTitle = findViewById(R.id.subTitle);
        moreButton = findViewById(R.id.moreButton);

        moreButton.setOnClickListener(TitleApp.this);

        //init style for component
        if (style == null) return;
        try {
            String textTitle = style.getString(R.styleable.TitleApp_app_title);
            String textSubTitle = style.getString(R.styleable.TitleApp_app_subTitle);
            boolean isMore = style.getBoolean(R.styleable.TitleApp_app_isMore, true);

            Log.d(TAG, "init: has handle: " + textSubTitle + " " + textTitle);

            //handle text title
            title.setText(textTitle != null ? textTitle : "");

            //handle text sub title
            if (textSubTitle != null) {
                subTitle.setText(textSubTitle);
            } else {
                subTitle.setVisibility(GONE);
            }

            //handle more button
            moreButton.setVisibility(isMore ? VISIBLE : GONE);

        } catch (Exception e) {
            Log.d(TAG, "init: " + e.getMessage());
        } finally {
            style.recycle();
        }
    }

    public void setTextTitle(@NonNull String text) {
        title.setText(text);
        requestLayout();
    }

    public void setTextSubTitle(@NonNull String text) {
        subTitle.setText(text);
        requestLayout();
    }

    public void setVisibleMoreButton(boolean visible) {
        moreButton.setVisibility(visible ? VISIBLE : GONE);
    }

    public void setTitleButtonHandler(TitleButtonHandler titleButtonHandler) {
        this.titleButtonHandler = titleButtonHandler;
        requestLayout();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.moreButton) {
            if (titleButtonHandler != null) titleButtonHandler.onMoreButtonClick();
        }
    }

    public interface TitleButtonHandler {
        void onMoreButtonClick();
    }
}
