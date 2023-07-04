package com.example.assignment.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.assignment.R;
import com.example.assignment.components.HeaderApp;
import com.example.assignment.fragments.ReadingBottomSheetFragment;
import com.example.assignment.models.Chapter;
import com.example.assignment.utils.Convert;

import java.util.ArrayList;

public class ReadingActivity extends AppCompatActivity {
    private final static String TAG = ReadingActivity.class.getSimpleName();
    private HeaderApp headerApp;
    private TextView textContent;
    private float textSize;
    private Chapter currentChapter;
    private int currentChapterSelectedIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);

        init();
        addEventListener();
    }

    public void init() {
        headerApp = findViewById(R.id.header_app);
        textContent = findViewById(R.id.text_content);

        textSize = Convert.pixelsToSp(ReadingActivity.this, textContent.getTextSize());

        currentChapterSelectedIndex = 0;
        currentChapter = getData().get(currentChapterSelectedIndex);
    }

    public void addEventListener() {
        headerApp.setHeaderButtonHandler(new HeaderApp.HeaderButtonHandler() {
            @Override
            public void onLeftButtonClick() {

            }

            @Override
            public void onRightButtonClick() {

            }

            @Override
            public void onUserContainerClick() {

            }

            @Override
            public void onCenterContainerClick() {
                ReadingBottomSheetFragment readingBottomSheetFragment = ReadingBottomSheetFragment.newInstance(getData(), currentChapterSelectedIndex, ReadingActivity.this.textSize)
                        .setHandlerFontSizeChangeListener(new ReadingBottomSheetFragment.HandlerFontSizeChangeListener() {
                            @Override
                            public void onChange(float _textSize) {
                                textContent.setTextSize(_textSize);
                                textSize = _textSize;
                            }

                            @Override
                            public void onChangeChapter(int newChapterIndex, boolean isNothing, ReadingBottomSheetFragment readingBottomSheetFragment) {
                                if (!isNothing) {
                                    currentChapter = getData().get(newChapterIndex);
                                    if (currentChapterSelectedIndex != newChapterIndex) {
                                        readingBottomSheetFragment.dismiss();
                                        currentChapterSelectedIndex = newChapterIndex;
                                    }
                                    Log.d(TAG, "onChangeChapter: " + currentChapter.getTitle());
                                }
                            }
                        });
                readingBottomSheetFragment.show(getSupportFragmentManager(), readingBottomSheetFragment.getTag());
            }
        });
    }

    public ArrayList<Chapter> getData() {
        ArrayList<Chapter> list = new ArrayList<>();

        list.add(new Chapter("àasfsaf", "Chương 1", "kashkfhaskhfkhasfhkashkfhkashfkhasdf", 0));
        list.add(new Chapter("àasfsaf", "Chương 2", "kashkfhaskhfkhasfhkashkfhkashfkhasdf", 1));
        list.add(new Chapter("àasfsaf", "Chương 3", "kashkfhaskhfkhasfhkashkfhkashfkhasdf", 2));
        list.add(new Chapter("àasfsaf", "Chương 4", "kashkfhaskhfkhasfhkashkfhkashfkhasdf", 3));
        list.add(new Chapter("àasfsaf", "Chương 5", "kashkfhaskhfkhasfhkashkfhkashfkhasdf", 4));
        list.add(new Chapter("àasfsaf", "Chương 6", "kashkfhaskhfkhasfhkashkfhkashfkhasdf", 5));
        list.add(new Chapter("àasfsaf", "Chương 7", "kashkfhaskhfkhasfhkashkfhkashfkhasdf", 6));
        list.add(new Chapter("àasfsaf", "Chương 8", "kashkfhaskhfkhasfhkashkfhkashfkhasdf", 7));

        return list;
    }
}