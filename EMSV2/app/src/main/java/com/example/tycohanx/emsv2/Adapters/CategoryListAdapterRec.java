package com.example.tycohanx.emsv2.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.tycohanx.emsv2.R;
import com.example.tycohanx.emsv2.Models.Category;
import java.util.Collections;
import java.util.List;

public class CategoryListAdapterRec extends Adapter<CategoryListAdapterRec.ViewHolder> {
    private List<Category> categoryList = Collections.emptyList();
    private ItemClickListener mClickListener;
    private LayoutInflater mInflater;

    public interface ItemClickListener {
        void onButtonClick(View view, int i);

        void onItemClick(View view, int i);
    }

    public class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder implements OnClickListener {
        public TextView catNameTV;
        public Button deleteCatBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            this.deleteCatBtn = (Button) itemView.findViewById(R.id.deleteBtn);
            this.catNameTV = (TextView) itemView.findViewById(R.id.dateTV);
            itemView.setOnClickListener(this);
        }

        public void onClick(View view) {
            if (CategoryListAdapterRec.this.mClickListener != null) {
                CategoryListAdapterRec.this.mClickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    public CategoryListAdapterRec(Context context, List<Category> categoryList) {
        this.mInflater = LayoutInflater.from(context);
        this.categoryList = categoryList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(this.mInflater.inflate(R.layout.expense_list_layout, parent, false));
    }

    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.catNameTV.setText(((Category) this.categoryList.get(position)).getName());
        holder.deleteCatBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (CategoryListAdapterRec.this.mClickListener != null) {
                    CategoryListAdapterRec.this.mClickListener.onButtonClick(view, holder.getAdapterPosition());
                }
            }
        });
    }

    public int getItemCount() {
        return this.categoryList.size();
    }

    public Category getItem(int id) {
        return (Category) this.categoryList.get(id);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
}
