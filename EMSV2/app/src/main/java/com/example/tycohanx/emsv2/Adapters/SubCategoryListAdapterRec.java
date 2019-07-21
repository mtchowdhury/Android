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
import com.example.tycohanx.emsv2.Managers.ExpenseManager;
import com.example.tycohanx.emsv2.Models.Category;
import com.example.tycohanx.emsv2.Models.SubCategory;
import java.util.Collections;
import java.util.List;

public class SubCategoryListAdapterRec extends Adapter<SubCategoryListAdapterRec.ViewHolder> {
    private List<Category> categories = Collections.emptyList();
    private ItemClickListener mClickListener;
    private LayoutInflater mInflater;
    private ExpenseManager manager;
    private List<SubCategory> subcategoryList = Collections.emptyList();

    public interface ItemClickListener {
        void onButtonClickSub(View view, int i);

        void onItemClickSub(View view, int i);
    }

    public class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder implements OnClickListener {
        public TextView catNameTV;
        public Button deleteCatBtn;
        public TextView subCatNameTV;

        public ViewHolder(View itemView) {
            super(itemView);
            this.deleteCatBtn = (Button) itemView.findViewById(R.id.deleteBtn);
            this.catNameTV = (TextView) itemView.findViewById(R.id.dateTV);
            this.subCatNameTV = (TextView) itemView.findViewById(R.id.balanceTV);
            itemView.setOnClickListener(this);
        }

        public void onClick(View view) {
            if (SubCategoryListAdapterRec.this.mClickListener != null) {
                SubCategoryListAdapterRec.this.mClickListener.onItemClickSub(view, getAdapterPosition());
            }
        }
    }

    public SubCategoryListAdapterRec(Context context, List<SubCategory> subCategoryList) {
        this.mInflater = LayoutInflater.from(context);
        this.subcategoryList = subCategoryList;
        this.manager = new ExpenseManager(context);
        this.categories = this.manager.getAllCategory();
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(this.mInflater.inflate(R.layout.expense_list_layout, parent, false));
    }

    public void onBindViewHolder(final ViewHolder holder, int position) {
        SubCategory subCategory = (SubCategory) this.subcategoryList.get(position);
        String catName = "";
        for (Category cat : this.categories) {
            if (cat.getId() == subCategory.getCatId()) {
                catName = cat.getName();
            }
        }
        holder.catNameTV.setText(subCategory.getName());
        holder.subCatNameTV.setText(catName);
        holder.deleteCatBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (SubCategoryListAdapterRec.this.mClickListener != null) {
                    SubCategoryListAdapterRec.this.mClickListener.onButtonClickSub(view, holder.getAdapterPosition());
                }
            }
        });
    }

    public int getItemCount() {
        return this.subcategoryList.size();
    }

    public SubCategory getItem(int id) {
        return (SubCategory) this.subcategoryList.get(id);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
}
