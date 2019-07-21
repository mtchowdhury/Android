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
import com.example.tycohanx.emsv2.Models.Expense;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ExpenseRecyclerAdapter extends Adapter<ExpenseRecyclerAdapter.ViewHolder> {
    private List<Expense> expenseList = Collections.emptyList();
    private ItemClickListener mClickListener;
    private LayoutInflater mInflater;

    public interface ItemClickListener {
        void onButtonClick(View view, int i);

        void onItemClick(View view, int i);
    }

    public class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder implements OnClickListener {
        public TextView AmountTV;
        public TextView BalanceTV;
        public TextView CategoryTV;
        public TextView SubcategoryTV;
        public TextView dateTV;
        public Button deleteExpenseBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            this.deleteExpenseBtn = (Button) itemView.findViewById(R.id.deleteBtn);
            this.dateTV = (TextView) itemView.findViewById(R.id.dateTV);
            this.CategoryTV = (TextView) itemView.findViewById(R.id.categoryTv);
            this.SubcategoryTV = (TextView) itemView.findViewById(R.id.subcategoryTv);
            this.AmountTV = (TextView) itemView.findViewById(R.id.amountTv);
            this.BalanceTV = (TextView) itemView.findViewById(R.id.balanceTV);
            itemView.setOnClickListener(this);
        }

        public void onClick(View view) {
            if (ExpenseRecyclerAdapter.this.mClickListener != null) {
                ExpenseRecyclerAdapter.this.mClickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    public ExpenseRecyclerAdapter(Context context, List<Expense> ExpenseList) {
        this.mInflater = LayoutInflater.from(context);
        this.expenseList = ExpenseList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(this.mInflater.inflate(R.layout.expense_list_layout, parent, false));
    }

    public void onBindViewHolder(final ViewHolder holder, int position) {
        Expense expense = (Expense) this.expenseList.get(position);
        holder.dateTV.setText("Date: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(expense.getDate())));
        holder.CategoryTV.setText("Category: " + expense.getCategory());
        holder.BalanceTV.setText("Balance: " + String.valueOf(expense.getBalance()));
        holder.SubcategoryTV.setText("S.Category: " + String.valueOf(expense.getSubCategory()));
        holder.AmountTV.setText("Expense: " + String.valueOf(expense.getAmount()));
        holder.deleteExpenseBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (ExpenseRecyclerAdapter.this.mClickListener != null) {
                    ExpenseRecyclerAdapter.this.mClickListener.onButtonClick(view, holder.getAdapterPosition());
                }
            }
        });
    }

    public int getItemCount() {
        return this.expenseList.size();
    }

    public Expense getItem(int id) {
        return (Expense) this.expenseList.get(id);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
}
