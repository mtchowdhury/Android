package com.example.tycohanx.emsv2.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.tycohanx.emsv2.Adapters.SpinnerAdapter;
import com.example.tycohanx.emsv2.R;
import com.example.tycohanx.emsv2.Managers.ExpenseManager;
import com.example.tycohanx.emsv2.Models.Balance;
import com.example.tycohanx.emsv2.Models.Category;
import com.example.tycohanx.emsv2.Models.Expense;
import com.example.tycohanx.emsv2.Models.SubCategory;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;

public class HomeFragment extends Fragment {
    int amount;
    ArrayList<String> catList;
    Spinner catSpinner;
    String category;
    String description;
    EditText descriptionEt;
    Expense expense;
    EditText expenseEt;
    TextView mainBalanceTV;
    ExpenseManager manager;
    Button saveExpense;
    Button setDate;
    ArrayList<String> sourceList;
    Spinner sourceSpinner;
    TextView specBalanceTV;
    String subCategory;
    ArrayList<String> subCatlist;
    Spinner subcatSpinner;
    SpinnerAdapter subspinnerAdapter;
    long time = 0;

    /* renamed from: com.tycohanx.ems.Fragments.HomeFragment$1 */
    class C02771 implements OnItemSelectedListener {
        C02771() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
            int id = 0;
            Iterator it = HomeFragment.this.manager.getAllCategory().iterator();
            while (it.hasNext()) {
                Category cat = (Category) it.next();
                if (cat.getName().equals(HomeFragment.this.catList.get(position))) {
                    id = cat.getId();
                    break;
                }
            }
            HomeFragment.this.subCatlist = HomeFragment.this.getStringListFromSubCategory(HomeFragment.this.manager.getSubCategoryByCategoryId(id));
            HomeFragment.this.subspinnerAdapter = new SpinnerAdapter(HomeFragment.this.getContext(), HomeFragment.this.subCatlist);
            HomeFragment.this.subspinnerAdapter.setDropDownViewResource(17367049);
            HomeFragment.this.subcatSpinner.setAdapter(HomeFragment.this.subspinnerAdapter);
            HomeFragment.this.subspinnerAdapter.notifyDataSetChanged();
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
            HomeFragment.this.subCatlist = HomeFragment.this.getStringListFromSubCategory(HomeFragment.this.manager.getSubCategoryByCategoryId(1));
        }
    }

    /* renamed from: com.tycohanx.ems.Fragments.HomeFragment$2 */
    class C02782 implements OnItemSelectedListener {
        C02782() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if (i == 0) {
                HomeFragment.this.specBalanceTV.setText(String.valueOf(HomeFragment.this.manager.getLastBalance().getCash()));
            } else if (i == 1) {
                HomeFragment.this.specBalanceTV.setText(String.valueOf(HomeFragment.this.manager.getLastBalance().getAccount()));
            } else {
                HomeFragment.this.specBalanceTV.setText(String.valueOf(HomeFragment.this.manager.getLastBalance().getCash() + HomeFragment.this.manager.getLastBalance().getAccount()));
            }
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    /* renamed from: com.tycohanx.ems.Fragments.HomeFragment$3 */
    class C02793 implements OnClickListener {
        C02793() {
        }

        public void onClick(View view) {
            if (HomeFragment.this.saveExpense()) {
                HomeFragment.this.adjustBalance();
            }
        }
    }

    /* renamed from: com.tycohanx.ems.Fragments.HomeFragment$4 */
    class C02814 implements OnClickListener {
        C02814() {
        }

        public void onClick(View view) {
            final View dialogView = View.inflate(HomeFragment.this.getContext(), R.layout.date_time_picker, null);
            final AlertDialog alertDialog = new Builder(HomeFragment.this.getContext()).create();
            dialogView.findViewById(R.id.date_time_set).setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.date_picker);
                    Calendar calendar = new GregorianCalendar(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
                    HomeFragment.this.time = calendar.getTimeInMillis();
                    alertDialog.dismiss();
                }
            });
            alertDialog.setView(dialogView);
            alertDialog.show();
        }
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_layout, container, false);
        this.expenseEt = (EditText) view.findViewById(R.id.expenseET);
        this.descriptionEt = (EditText) view.findViewById(R.id.descriptionET);
        this.catSpinner = (Spinner) view.findViewById(R.id.catSpinner);
        this.subcatSpinner = (Spinner) view.findViewById(R.id.subcatSpinner);
        this.sourceSpinner = (Spinner) view.findViewById(R.id.sourceSpinner);
        this.saveExpense = (Button) view.findViewById(R.id.saveExpense);
        this.setDate = (Button) view.findViewById(R.id.dateBtn);
        this.mainBalanceTV = (TextView) view.findViewById(R.id.mainBalanceTV);
        this.specBalanceTV = (TextView) view.findViewById(R.id.specBalanceTV);
        this.manager = new ExpenseManager(getActivity().getApplicationContext());
        this.catList = getStringListFromCategory();
        this.subCatlist = getStringListFromSubCategory(this.manager.getSubCategoryByCategoryId(1));
        this.sourceList = new ArrayList();
        this.sourceList.add("CASH");
        this.sourceList.add("ACCOUNT");
        this.sourceList.add("TOTAL");
        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(getContext(), this.catList);
        SpinnerAdapter sourceSpinnerAdapter = new SpinnerAdapter(getContext(), this.sourceList);
        spinnerAdapter.setDropDownViewResource(17367049);
        sourceSpinnerAdapter.setDropDownViewResource(17367049);
        this.catSpinner.setAdapter(spinnerAdapter);
        spinnerAdapter.notifyDataSetChanged();
        this.sourceSpinner.setAdapter(sourceSpinnerAdapter);
        this.specBalanceTV.setText(String.valueOf(this.manager.getLastBalance().getCash() + this.manager.getLastBalance().getAccount()));
        this.catSpinner.setOnItemSelectedListener(new C02771());
        this.sourceSpinner.setOnItemSelectedListener(new C02782());
        this.saveExpense.setOnClickListener(new C02793());
        this.setDate.setOnClickListener(new C02814());
        return view;
    }

    public boolean saveExpense() {
        if (this.expenseEt.getText().toString().length() < 1) {
            Toast.makeText(getContext(), "please insert the expnese amount!", 0).show();
            return false;
        } else if (this.manager.getLastBalance().getId() == 0) {
            Toast.makeText(getContext(), "please insert balance from admin panel first!", 0).show();
            this.expenseEt.getText().clear();
            this.descriptionEt.getText().clear();
            return false;
        } else {
            this.amount = Integer.valueOf(this.expenseEt.getText().toString()).intValue();
            this.category = this.catSpinner.getSelectedItem().toString();
            this.subCategory = this.subcatSpinner.getSelectedItem().toString();
            this.description = this.descriptionEt.getText().toString();
            long date = Calendar.getInstance().getTime().getTime();
            Balance cBalance = this.manager.getLastBalance();
            int balance = (cBalance.getCash() + cBalance.getAccount()) - this.amount;
            if (this.time != 0) {
                date = this.time;
            }
            this.expense = new Expense(this.amount, this.category, this.subCategory, this.description, date, balance, 0);
            boolean inserted = this.manager.addExpense(this.expense);
            if (inserted) {
                Toast.makeText(getContext(), "expense Saved", 0).show();
                this.expenseEt.getText().clear();
                this.descriptionEt.getText().clear();
                return inserted;
            }
            Toast.makeText(getActivity().getApplicationContext(), "expense not Saved", 0).show();
            return inserted;
        }
    }

    public void getExpense(View view) {
        ArrayList<Expense> exp = this.manager.getAllExpense();
    }

    public ArrayList<String> getStringListFromCategory() {
        ArrayList<Category> catList = this.manager.getAllCategory();
        ArrayList<String> catListString = new ArrayList();
        Iterator it = catList.iterator();
        while (it.hasNext()) {
            catListString.add(((Category) it.next()).getName());
        }
        return catListString;
    }

    public ArrayList<String> getStringListFromSubCategory(ArrayList<SubCategory> subList) {
        ArrayList<SubCategory> subcatList = subList;
        ArrayList<String> subcatListString = new ArrayList();
        Iterator it = subcatList.iterator();
        while (it.hasNext()) {
            subcatListString.add(((SubCategory) it.next()).getName());
        }
        return subcatListString;
    }

    public void adjustBalance() {
        Balance currentBalance = this.manager.getLastBalance();
        currentBalance.setCash(currentBalance.getCash() - this.expense.getAmount());
        boolean updated = this.manager.updateBalance(currentBalance);
        this.specBalanceTV.setText(String.valueOf(this.manager.getLastBalance().getCash()));
        if (updated) {
            Toast.makeText(getContext(), "Balance Adjusted!", 0).show();
        } else {
            Toast.makeText(getContext(), "Balance Adjust Failed!", 0).show();
        }
    }
}
