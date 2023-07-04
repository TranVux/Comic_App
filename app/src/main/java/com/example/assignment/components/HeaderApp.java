package com.example.assignment.components;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.assignment.R;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class HeaderApp extends RelativeLayout implements View.OnClickListener {
    static private final String TAG = HeaderApp.class.getSimpleName();

    private HeaderButtonHandler headerButtonHandler;
    private LinearLayout leftContainer, centerContainer, rightContainer, userContainer;
    private ImageView buttonLeft, buttonRight, avatar;
    private TextView username, textSession, titleHeader, subTitleHeader;

    private boolean isUserHeader;

    public HeaderApp(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.header_component, this);
        TypedArray style = context.getTheme().obtainStyledAttributes(attrs, R.styleable.HeaderApp, 0, 0);
        init(style);
    }

    public HeaderApp(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.header_component, this);
        TypedArray style = context.getTheme().obtainStyledAttributes(attrs, R.styleable.HeaderApp, 0, 0);
        init(style);
    }

    public HeaderApp(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        LayoutInflater.from(context).inflate(R.layout.header_component, this);
        TypedArray style = context.getTheme().obtainStyledAttributes(attrs, R.styleable.HeaderApp, 0, 0);
        init(style);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        clickListener();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void init(TypedArray style) {
        leftContainer = findViewById(R.id.left_container);
        rightContainer = findViewById(R.id.right_container);
        centerContainer = findViewById(R.id.center_container);
        userContainer = findViewById(R.id.user_container);

        buttonLeft = findViewById(R.id.button_left);
        buttonRight = findViewById(R.id.button_right);
        avatar = findViewById(R.id.avatar_image);

        titleHeader = findViewById(R.id.title_header);
        subTitleHeader = findViewById(R.id.sub_title_header);
        username = findViewById(R.id.user_name);
        textSession = findViewById(R.id.text_session);

        try {
            //handle left container
            isUserHeader = style.getBoolean(R.styleable.HeaderApp_app_is_user_container, false);
            if (isUserHeader) {
                //handle get init variable
                userContainer.setVisibility(VISIBLE);

                String userName = style.getString(R.styleable.HeaderApp_app_user_name_text);
                Drawable image_avatar = style.getDrawable(R.styleable.HeaderApp_app_avatar_src);

                username.setText(userName != null ? userName : "Guest!");
                avatar.setImageDrawable(image_avatar != null ? image_avatar : getResources().getDrawable(R.drawable.avtw, getContext().getTheme()));
                textSession.setText(createSessionText());

                //handle button left
                buttonLeft.setVisibility(GONE);

                //handle title header
                titleHeader.setVisibility(GONE);
                subTitleHeader.setVisibility(GONE);
            } else {
                Drawable image_button_left_src = style.getDrawable(R.styleable.HeaderApp_app_button_left_src);
                buttonLeft.setImageDrawable(
                        image_button_left_src != null ? image_button_left_src :
                                getResources().getDrawable(R.drawable.ic_back, getContext().getTheme())
                );

                buttonLeft.setVisibility(VISIBLE);
                userContainer.setVisibility(GONE);
            }
            //end handle left container

            //handler center container
            String titleText = style.getString(R.styleable.HeaderApp_app_header_title);
            String subText = style.getString(R.styleable.HeaderApp_app_sub_header_title);

            titleHeader.setText(titleText != null ? titleText : "");
            subTitleHeader.setText(subText != null ? subText : "");
            //end handle center container

            //handle right container
            boolean isEnable = style.getBoolean(R.styleable.HeaderApp_app_enable_right_button, true);
            Log.d(TAG, "init: " + isEnable);
            if (isEnable) {
                Drawable image_button_right_src = style.getDrawable(R.styleable.HeaderApp_app_button_right_src);
                buttonRight.setImageDrawable(
                        image_button_right_src != null ? image_button_right_src :
                                getResources().getDrawable(R.drawable.ic_search, getContext().getTheme())
                );
            } else {
                buttonRight.setVisibility(GONE);
            }
        } catch (Exception e) {
            Log.d(TAG, "init: " + e);
        } finally {
            style.recycle();
        }
    }

    public void clickListener() {
        buttonLeft.setOnClickListener(HeaderApp.this);
        buttonRight.setOnClickListener(HeaderApp.this);
        userContainer.setOnClickListener(HeaderApp.this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_left: {
                if (!isUserHeader && headerButtonHandler != null) {
                    headerButtonHandler.onLeftButtonClick();
                }
                break;
            }
            case R.id.button_right: {
                if (headerButtonHandler != null) {
                    headerButtonHandler.onRightButtonClick();
                }
                break;
            }
            case R.id.user_container: {
                if (isUserHeader && headerButtonHandler != null) {
                    headerButtonHandler.onUserContainerClick();
                }
                break;
            }
        }
    }

    public void setHeaderButtonHandler(HeaderButtonHandler headerButtonHandler) {
        this.headerButtonHandler = headerButtonHandler;
    }

    public void setUserAvatar(String url) {
        if (isUserHeader) {
            Glide.with(getContext()).load(url)
                    .override(70, 70).placeholder(R.drawable.placeholder_image)
                    .skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(avatar);

            requestLayout();
        }
    }

    public void setEnableButtonRight(boolean isEnable) {
        buttonRight.setVisibility(isEnable ? VISIBLE : GONE);
        requestLayout();
    }

    public void setUserAvatar(int resID) {
        if (isUserHeader) {
            avatar.setImageResource(resID);
            requestLayout();
        }
    }

    public void setTitle(@NonNull String title) {
        titleHeader.setText(title);
        requestLayout();
    }

    public void setSubTitle(@NonNull String subTitle) {
        subTitleHeader.setText(subTitle);
        requestLayout();
    }

    public String createSessionText() {
        @SuppressLint("SimpleDateFormat")
        int hours = Integer.parseInt(new SimpleDateFormat("HH").format(new Date(System.currentTimeMillis())));

        if (hours > 0 && hours <= 10) {
            return "Chào buổi sáng";
        } else if (hours > 10 && hours <= 18) {
            return "Chào buổi chiều";
        } else {
            return "Chào buổi tối";
        }
    }

    public interface HeaderButtonHandler {
        void onLeftButtonClick();

        void onRightButtonClick();

        void onUserContainerClick();
    }
}
