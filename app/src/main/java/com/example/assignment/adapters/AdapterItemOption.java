package com.example.assignment.adapters;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment.R;
import com.example.assignment.fragments.OptionBottomSheetDialog.OptionBottomSheetType;
import com.example.assignment.models.Category;
import com.example.assignment.utils.DiffUtilOptionAdapter;

import java.util.ArrayList;

public class AdapterItemOption extends RecyclerView.Adapter<AdapterItemOption.ItemOptionViewHolder> {

    private ArrayList listData;
    private OptionBottomSheetType type;

    private ItemOptionHandler itemOptionHandler;
    private int itemSelected;

    public AdapterItemOption(ArrayList listData, ItemOptionHandler itemOptionHandler, OptionBottomSheetType type, int defaultIndexItemSelected) {
        this.listData = listData;
        this.itemOptionHandler = itemOptionHandler;
        this.type = type;
        this.itemSelected = defaultIndexItemSelected;
    }

    public void setListData(ArrayList listData, OptionBottomSheetType type) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtilOptionAdapter(listData, this.listData, type));
        diffResult.dispatchUpdatesTo(this);
        this.listData.clear();
        this.listData.addAll(listData);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setItemSelected(int position) {
        this.itemSelected = position;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemOptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_option_layout, parent, false);
        return new ItemOptionViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if (listData == null) return 0;
        return listData.size();
    }

    public void onBindViewHolder(@NonNull ItemOptionViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (listData.get(position) == null) return;

        String valueString = "";

        if (type == OptionBottomSheetType.CATEGORY) {
            valueString = ((Category) listData.get(position)).getTitle();
        } else if (type == OptionBottomSheetType.COUNTRY) {
            valueString = ((String) listData.get(position));
        }
        holder.textItem.setText(valueString);

        holder.item.setCardBackgroundColor(
                position == itemSelected ?
                        Color.parseColor("#00AAD3") : Color.parseColor("#1E2027")
        );


        String finalValueString = valueString;
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemOptionHandler.onItemClick(finalValueString, position);
            }
        });
    }

    public class ItemOptionViewHolder extends RecyclerView.ViewHolder {
        CardView item;
        TextView textItem;

        public ItemOptionViewHolder(@NonNull View itemView) {
            super(itemView);

            item = itemView.findViewById(R.id.item_option);
            textItem = itemView.findViewById(R.id.text_option);
        }
    }

    public interface ItemOptionHandler {
        void onItemClick(String value, int index);
    }
}
