package com.example.assignment.utils;

import androidx.recyclerview.widget.DiffUtil;

import com.example.assignment.fragments.OptionBottomSheetDialog.*;
import com.example.assignment.models.Category;

import java.util.ArrayList;
import java.util.Objects;

public class DiffUtilOptionAdapter extends DiffUtil.Callback {
    private ArrayList newList;
    private ArrayList oldList;
    private OptionBottomSheetType type;

    public DiffUtilOptionAdapter(ArrayList newList, ArrayList oldList, OptionBottomSheetType type) {
        this.newList = newList;
        this.oldList = oldList;
        this.type = type;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        if (type == OptionBottomSheetType.CATEGORY) {
            return Objects.equals(((Category) newList.get(newItemPosition)).getTitle(), ((Category) oldList.get(oldItemPosition)).getTitle());
        } else {
            return Objects.equals((String) newList.get(newItemPosition), (String) oldList.get(oldItemPosition));
        }
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        if (type == OptionBottomSheetType.CATEGORY) {
            return Objects.equals(((Category) newList.get(newItemPosition)).getTitle(), ((Category) oldList.get(oldItemPosition)).getTitle()) &&
                    Objects.equals(((Category) newList.get(newItemPosition)).getThumbnail(), ((Category) oldList.get(oldItemPosition)).getThumbnail()) &&
                    Objects.equals(((Category) newList.get(newItemPosition)).getId(), ((Category) oldList.get(oldItemPosition)).getId()) &&
                    Objects.equals(((Category) newList.get(newItemPosition)).getDisplayColor(), ((Category) oldList.get(oldItemPosition)).getDisplayColor());
        } else {
            return Objects.equals((String) newList.get(newItemPosition), (String) oldList.get(oldItemPosition));
        }
    }
}
