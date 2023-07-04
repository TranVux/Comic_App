package com.example.assignment.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.assignment.R;
import com.example.assignment.adapters.AdapterSpinner;
import com.example.assignment.models.Chapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.ramotion.fluidslider.FluidSlider;

import java.util.ArrayList;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

public class ReadingBottomSheetFragment extends BottomSheetDialogFragment {
    private static final String TAG = ReadingBottomSheetFragment.class.getSimpleName();
    private ArrayList<Chapter> listChapter;
    private int initChapterSelected;
    private float initFontSize;
    private final int minFontSize = 14;
    private final int maxFontSize = 22;
    private final int totalFontSize = maxFontSize - minFontSize;

    private FluidSlider fluidSlider;
    private TextView titleSlider;
    private Spinner spinnerChapter;
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
        View view = LayoutInflater.from(getContext()).inflate(R.layout.bottom_sheet_reading_layout, null, false);
        bottomSheetDialog.setContentView(view);

        init(view);
        listenerEvent();
        return bottomSheetDialog;
    }

    public void init(View view) {
        fluidSlider = view.findViewById(R.id.fluidSlider);
        titleSlider = view.findViewById(R.id.title_slider);
        spinnerChapter = view.findViewById(R.id.chapter_spinner);

        adapterSpinner = new AdapterSpinner(requireContext(), R.layout.item_chapter_layout, listChapter);
        spinnerChapter.setAdapter(adapterSpinner);
        spinnerChapter.setSelection(initChapterSelected);

        fluidSlider.setPosition(((initFontSize - minFontSize) / totalFontSize));
        fluidSlider.setBubbleText(String.valueOf((int) initFontSize));
        fluidSlider.setStartText(String.valueOf(minFontSize));
        fluidSlider.setEndText(String.valueOf(maxFontSize));
    }

    public void listenerEvent() {
        spinnerChapter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                handlerFontSizeChangeListener.onChangeChapter(i, false, ReadingBottomSheetFragment.this);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                handlerFontSizeChangeListener.onChangeChapter(initChapterSelected, true, ReadingBottomSheetFragment.this);
            }
        });

        fluidSlider.setBeginTrackingListener(new Function0<Unit>() {
            @Override
            public Unit invoke() {
                titleSlider.setTextColor(getResources().getColor(android.R.color.transparent));
                return Unit.INSTANCE;
            }
        });

        fluidSlider.setEndTrackingListener(new Function0<Unit>() {
            @Override
            public Unit invoke() {
                titleSlider.setTextColor(getResources().getColor(R.color.white));
                return Unit.INSTANCE;
            }
        });

        fluidSlider.setPositionListener(new Function1<Float, Unit>() {
            @Override
            public Unit invoke(Float pos) {
                Log.d(TAG, "invoke: " + pos);
                final String value = String.valueOf((int) (minFontSize + totalFontSize * pos));
                fluidSlider.setBubbleText(value);
                //handle callback
                handlerFontSizeChangeListener.onChange((int) (minFontSize + totalFontSize * pos));
                return Unit.INSTANCE;
            }
        });
    }

    public interface HandlerFontSizeChangeListener {
        void onChange(float fontSize);

        void onChangeChapter(int newChapterIndex, boolean isNothing, ReadingBottomSheetFragment readingBottomSheetFragment);
    }
}
