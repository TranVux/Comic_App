package com.example.assignment.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.assignment.R;
import com.example.assignment.adapters.AdapterItemOption;
import com.example.assignment.adapters.GridSpacingItemDecoration;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Objects;

public class OptionBottomSheetDialog extends BottomSheetDialogFragment {
    private static final String TAG = OptionBottomSheetDialog.class.getSimpleName();

    private String titleText;
    private int selectedItem;
    private TextView title;
    private MaterialButton buttonConfirm;
    private RecyclerView listViewOption;
    private ArrayList dataList;
    private OptionBottomSheetType optionBottomSheetType;
    private AdapterItemOption adapterItemOption;
    private HandlerDismissListener handlerDismissListener;

    public static OptionBottomSheetDialog newInstance(String titleText, ArrayList dataList, OptionBottomSheetType optionBottomSheetType, int defaultSelectedItemIndex) {
        OptionBottomSheetDialog optionBottomSheetDialog = new OptionBottomSheetDialog();
        Bundle data = new Bundle();
        data.putSerializable("DATA_LIST", dataList);
        data.putString("TITLE", titleText);
        data.putInt("DEFAULT_SELECTED_ITEM_INDEX", defaultSelectedItemIndex);
        data.putSerializable("OPTION_TYPE", optionBottomSheetType);

        optionBottomSheetDialog.setArguments(data);
        return optionBottomSheetDialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
        Bundle data = getArguments();
        if (data != null) {
            this.dataList = (ArrayList) data.getSerializable("DATA_LIST");
            this.optionBottomSheetType = (OptionBottomSheetType) data.getSerializable("OPTION_TYPE");
            this.titleText = data.getString("TITLE");
            this.selectedItem = data.getInt("DEFAULT_SELECTED_ITEM_INDEX");
        }
    }

    public OptionBottomSheetDialog setHandlerDismissListener(HandlerDismissListener handlerDismissListener) {
        this.handlerDismissListener = handlerDismissListener;
        return this;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.option_bottom_sheet_layout, null);
        bottomSheetDialog.setContentView(view);

        handleBottomSheet(view);
        return bottomSheetDialog;
    }

    public void handleBottomSheet(View view) {
        buttonConfirm = view.findViewById(R.id.button_confirm);
        title = view.findViewById(R.id.title);
        listViewOption = view.findViewById(R.id.list_item);

        title.setText(this.titleText);

        addEventListener();
        handleListOption();
    }

    public void handleListOption() {
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        adapterItemOption = new AdapterItemOption(this.dataList, new AdapterItemOption.ItemOptionHandler() {
            @Override
            public void onItemClick(String value, int index) {
                Log.d(TAG, "onItemClick: " + value);
                adapterItemOption.setItemSelected(index);
                selectedItem = index;
            }
        }, this.optionBottomSheetType, this.selectedItem);

        listViewOption.setLayoutManager(gridLayoutManager);
        listViewOption.addItemDecoration(new GridSpacingItemDecoration(3, 30, false));
        listViewOption.setAdapter(adapterItemOption);
    }

    public void addEventListener() {
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Confirm button");
                dismiss();
            }
        });
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        handlerDismissListener.onDismiss(optionBottomSheetType, dataList.get(selectedItem), selectedItem);
        super.onDismiss(dialog);
    }

    public interface HandlerDismissListener {
        void onDismiss(OptionBottomSheetType type, Object value, int selectedItemIndex);
    }

    public enum OptionBottomSheetType {
        COUNTRY, CATEGORY
    }
}
