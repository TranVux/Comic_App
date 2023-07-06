package com.example.assignment.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.assignment.R;
import com.example.assignment.adapters.AdapterSpinner;
import com.example.assignment.databinding.BottomSheetReadingLayoutBinding;
import com.example.assignment.models.Chapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

import kotlin.Unit;

public class ReadingBottomSheetFragment extends BottomSheetDialogFragment {
    private static final String TAG = ReadingBottomSheetFragment.class.getSimpleName();
    private BottomSheetReadingLayoutBinding bottomSheetReadingLayoutBinding;
    private ArrayList<Chapter> listChapter;
    private int initChapterSelected;
    private float initFontSize;
    private final int minFontSize = 14;
    private final int maxFontSize = 22;
    private final int totalFontSize = maxFontSize - minFontSize;

    private AdapterSpinner adapterSpinner;

    private HandlerFontSizeChangeListener handlerFontSizeChangeListener;

    public static ReadingBottomSheetFragment newInstance(ArrayList<Chapter> listChapter, int initChapterSelected, float initFontSize) {
        ReadingBottomSheetFragment readingBottomSheetFragment = new ReadingBottomSheetFragment();
        Bundle bundle = new Bundle();

        bundle.putSerializable("LIST_CHAPTER", listChapter);
        bundle.putInt("INIT_CHAPTER_SELECTED", initChapterSelected);
        bundle.putFloat("INIT_FONT_SIZE", initFontSize);

        readingBottomSheetFragment.setArguments(bundle);
        return readingBottomSheetFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);

        Bundle data = getArguments();
        if (data != null) {
            listChapter = (ArrayList<Chapter>) data.getSerializable("LIST_CHAPTER");
            initFontSize = data.getFloat("INIT_FONT_SIZE");
            initChapterSelected = data.getInt("INIT_CHAPTER_SELECTED");
        }
    }

    public ReadingBottomSheetFragment setHandlerFontSizeChangeListener(HandlerFontSizeChangeListener handlerFontSizeChangeListener) {
        this.handlerFontSizeChangeListener = handlerFontSizeChangeListener;
        return this;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        bottomSheetReadingLayoutBinding = BottomSheetReadingLayoutBinding.inflate(LayoutInflater.from(requireContext()));
        bottomSheetDialog.setContentView(bottomSheetReadingLayoutBinding.getRoot());

        init();
        listenerEvent();
        return bottomSheetDialog;
    }

    public void init() {
        adapterSpinner = new AdapterSpinner(requireContext(), R.layout.item_chapter_layout, listChapter);
        bottomSheetReadingLayoutBinding.chapterSpinner.setAdapter(adapterSpinner);
        bottomSheetReadingLayoutBinding.chapterSpinner.setSelection(initChapterSelected);

        bottomSheetReadingLayoutBinding.fluidSlider.setPosition(((initFontSize - minFontSize) / totalFontSize));
        bottomSheetReadingLayoutBinding.fluidSlider.setBubbleText(String.valueOf((int) initFontSize));
        bottomSheetReadingLayoutBinding.fluidSlider.setStartText(String.valueOf(minFontSize));
        bottomSheetReadingLayoutBinding.fluidSlider.setEndText(String.valueOf(maxFontSize));
    }

    public void listenerEvent() {
        bottomSheetReadingLayoutBinding.chapterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                handlerFontSizeChangeListener.onChangeChapter(i, false, ReadingBottomSheetFragment.this);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                handlerFontSizeChangeListener.onChangeChapter(initChapterSelected, true, ReadingBottomSheetFragment.this);
            }
        });

        bottomSheetReadingLayoutBinding.fluidSlider.setBeginTrackingListener(() -> {
            bottomSheetReadingLayoutBinding.titleSlider.setTextColor(getResources().getColor(android.R.color.transparent));
            return Unit.INSTANCE;
        });

        bottomSheetReadingLayoutBinding.fluidSlider.setEndTrackingListener(() -> {
            bottomSheetReadingLayoutBinding.titleSlider.setTextColor(getResources().getColor(R.color.white));
            return Unit.INSTANCE;
        });

        bottomSheetReadingLayoutBinding.fluidSlider.setPositionListener(pos -> {
            Log.d(TAG, "invoke: " + pos);
            final String value = String.valueOf((int) (minFontSize + totalFontSize * pos));
            bottomSheetReadingLayoutBinding.fluidSlider.setBubbleText(value);
            //handle callback
            handlerFontSizeChangeListener.onChange((int) (minFontSize + totalFontSize * pos));
            return Unit.INSTANCE;
        });
    }

    public interface HandlerFontSizeChangeListener {
        void onChange(float fontSize);

        void onChangeChapter(int newChapterIndex, boolean isNothing, ReadingBottomSheetFragment readingBottomSheetFragment);
    }
}
