package com.example.tycohanx.emsv2.Fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.tycohanx.emsv2.Adapters.ExpenseRecyclerAdapter;
import com.example.tycohanx.emsv2.Adapters.ExpenseRecyclerAdapter.ItemClickListener;
import com.example.tycohanx.emsv2.Adapters.SpinnerAdapter;
import com.example.tycohanx.emsv2.Models.Category;
import com.example.tycohanx.emsv2.Models.SubCategory;
import com.example.tycohanx.emsv2.R;
import com.example.tycohanx.emsv2.Managers.ExpenseManager;
import com.example.tycohanx.emsv2.Models.Expense;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;

public class ExpenseListFragment extends Fragment implements ItemClickListener {
    int amount;
    TextView amountdetTV;
    TextView balancedetTV;
    TextView catdetTV;
    TextView datedetTV;
    TextView descrpdetTV;
    ArrayList<Expense> expenseList;
    String finDate;
    Button fromDateBtn;
    Calendar calendar=Calendar.getInstance();
    long fromTime = 0;
    ItemClickListener itemClick;
    ListView listView;
    ExpenseManager manager;
    int pos;
    ExpenseRecyclerAdapter recyclerAdapter;
    RecyclerView recyclerView;
    Button refreshBtn;
    TextView subcatdetTV;
    Button toDateBtn;
    long toTime =  0;
    TextView totalTV;
    Spinner catFilterSpinner;
    Spinner subcatFilterSpinner;
    SpinnerAdapter spinnerAdapter;
    ArrayList<String>catList;
    ArrayList<String>subcatList;
    int catId=-1000;
    int subCatId=-1000;
    String catString="";
    String subCatString="";
    SimpleDateFormat format = new SimpleDateFormat("dd-MMMM-yyyy");




    /* renamed from: com.tycohanx.ems.Fragments.ExpenseListFragment$1 */
    class C02711 implements OnClickListener {
        C02711() {
        }

        public void onClick(View view) {
            final View dialogView = View.inflate(ExpenseListFragment.this.getContext(), R.layout.date_time_picker, null);
            final AlertDialog alertDialog = new Builder(ExpenseListFragment.this.getContext()).create();
            dialogView.findViewById(R.id.date_time_set).setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.date_picker);
                    Calendar calendar = new GregorianCalendar(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
                    ExpenseListFragment.this.fromTime = calendar.getTimeInMillis();
                    String date=format.format(calendar.getTime());
                    fromDateBtn.setText(date);
                    alertDialog.dismiss();
                }
            });
            alertDialog.setView(dialogView);
            alertDialog.show();
        }
    }

    /* renamed from: com.tycohanx.ems.Fragments.ExpenseListFragment$2 */
    class C02732 implements OnClickListener {
        C02732() {
        }

        public void onClick(View view) {
            final View dialogView = View.inflate(ExpenseListFragment.this.getContext(), R.layout.date_time_picker, null);
            final AlertDialog alertDialog = new Builder(ExpenseListFragment.this.getContext()).create();
            dialogView.findViewById(R.id.date_time_set).setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.date_picker);
                    Calendar calendar = new GregorianCalendar(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
                    ExpenseListFragment.this.toTime = calendar.getTimeInMillis() + 86400000;

                    SimpleDateFormat format = new SimpleDateFormat("dd-MMMM-yyyy");
                    String date=format.format(calendar.getTime());
                    toDateBtn.setText(date);

                    alertDialog.dismiss();
                }
            });
            alertDialog.setView(dialogView);
            alertDialog.show();
        }
    }

    /* renamed from: com.tycohanx.ems.Fragments.ExpenseListFragment$3 */
    class C02743 implements OnClickListener {
        C02743() {
        }

        public void onClick(View view) {
            /*if (ExpenseListFragment.this.toTime == 0 || ExpenseListFragment.this.fromTime == 0) {
                ExpenseListFragment.this.expenseList = ExpenseListFragment.this.manager.getAllExpense();
            } else {

            }*/
            if(ExpenseListFragment.this.fromTime>ExpenseListFragment.this.toTime) Toast.makeText(ExpenseListFragment.this.getContext(), "Please select proper date range!! ", 0).show();
            ExpenseListFragment.this.expenseList = ExpenseListFragment.this.manager.getExpenseByFilter(ExpenseListFragment.this.fromTime, ExpenseListFragment.this.toTime,
                    ExpenseListFragment.this.catString,ExpenseListFragment.this.subCatString);
            ExpenseListFragment.this.recyclerAdapter = new ExpenseRecyclerAdapter(ExpenseListFragment.this.getContext(), ExpenseListFragment.this.expenseList);
            ExpenseListFragment.this.recyclerAdapter.setClickListener(ExpenseListFragment.this.itemClick);
            ExpenseListFragment.this.recyclerView.setAdapter(ExpenseListFragment.this.recyclerAdapter);
            ExpenseListFragment.this.amount = 0;
            Iterator it = ExpenseListFragment.this.expenseList.iterator();
            while (it.hasNext()) {
                Expense exp = (Expense) it.next();
                ExpenseListFragment expenseListFragment = ExpenseListFragment.this;
                expenseListFragment.amount += exp.getAmount();
            }
            ExpenseListFragment.this.toTime = 0;
            ExpenseListFragment.this.fromTime = 0;
            ExpenseListFragment.this.totalTV.setText("total expense: " + String.valueOf(ExpenseListFragment.this.amount));
            Toast.makeText(ExpenseListFragment.this.getContext(), "All expenses loaded! ", 0).show();
        }
    }

    /* renamed from: com.tycohanx.ems.Fragments.ExpenseListFragment$4 */
    class C02754 implements DialogInterface.OnClickListener {
        C02754() {
        }

        public void onClick(DialogInterface dialog, int which) {
        }
    }

    /* renamed from: com.tycohanx.ems.Fragments.ExpenseListFragment$5 */
    class C02765 implements DialogInterface.OnClickListener {
        C02765() {
        }

        public void onClick(DialogInterface dialog, int which) {
            if (ExpenseListFragment.this.manager.deleteExpenseById(((Expense) ExpenseListFragment.this.expenseList.get(ExpenseListFragment.this.pos)).getId())) {
                Toast.makeText(ExpenseListFragment.this.getContext(), "expense deleted!", 0).show();
                ExpenseListFragment.this.expenseList = ExpenseListFragment.this.manager.getAllExpense();
                ExpenseListFragment.this.recyclerAdapter = new ExpenseRecyclerAdapter(ExpenseListFragment.this.getContext(), ExpenseListFragment.this.expenseList);
                ExpenseListFragment.this.recyclerAdapter.setClickListener(ExpenseListFragment.this.itemClick);
                ExpenseListFragment.this.recyclerView.setAdapter(ExpenseListFragment.this.recyclerAdapter);
                ExpenseListFragment.this.amount = 0;
                Iterator it = ExpenseListFragment.this.expenseList.iterator();
                while (it.hasNext()) {
                    Expense exp = (Expense) it.next();
                    ExpenseListFragment expenseListFragment = ExpenseListFragment.this;
                    expenseListFragment.amount += exp.getAmount();
                }
                ExpenseListFragment.this.totalTV.setText("total expense: " + String.valueOf(ExpenseListFragment.this.amount));
            }
        }
    }



    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.expense_list_fragment, container, false);
        this.manager = new ExpenseManager(getActivity().getApplicationContext());
        this.expenseList = getAllExpense();
        this.amount = 0;
        Iterator it = this.expenseList.iterator();
        while (it.hasNext()) {
            this.amount += ((Expense) it.next()).getAmount();
        }
        this.itemClick = this;
        this.totalTV = (TextView) view.findViewById(R.id.subTotalTV);
        this.refreshBtn = (Button) view.findViewById(R.id.refreshExpenseList);
        this.fromDateBtn = (Button) view.findViewById(R.id.fromDate);
        this.toDateBtn = (Button) view.findViewById(R.id.toDate);
        this.totalTV.setText("total expense: " + String.valueOf(this.amount));


        this.catList=new ArrayList<String>();
        this.subcatList=new ArrayList<String>();
        this.catList.add("All");
        this.subcatList.add("All");
        this.catList.addAll(getStringListFromCategory());
        ArrayList<String> sub=getStringListFromSubCategory(this.manager.getSubCategoryByCategoryId(1));
        this.subcatList.addAll(sub);
        //this.catList = getStringListFromCategory();
        //this.subcatList = getStringListFromSubCategory(this.manager.getSubCategoryByCategoryId(1));


        this.catFilterSpinner=view.findViewById(R.id.catFilterSpinner);
        this.subcatFilterSpinner=view.findViewById(R.id.subcatFilterSpinner);

        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(getContext(),this.catList);
        SpinnerAdapter subspinnerAdapter = new SpinnerAdapter(getContext(),this.subcatList);
        spinnerAdapter.setDropDownViewResource(17367049);
        subspinnerAdapter.setDropDownViewResource(17367049);

        this.catFilterSpinner.setAdapter(spinnerAdapter);
        this.subcatFilterSpinner.setAdapter(subspinnerAdapter);
        spinnerAdapter.notifyDataSetChanged();
        subspinnerAdapter.notifyDataSetChanged();


        Calendar calendar = Calendar.getInstance();
        toTime=calendar.getTimeInMillis();
        String date=format.format(calendar.getTime());
        toDateBtn.setText(date);
        if(calendar.get(Calendar.MONTH) == Calendar.JANUARY){
            calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR)-1);
            calendar.set(Calendar.MONTH, calendar.DECEMBER);
        }else{
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)-1);
        }
        fromTime=calendar.getTimeInMillis();
        date=format.format(calendar.getTime());
        fromDateBtn.setText(date);



        this.catFilterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               // int id = 0;
                Iterator it = ExpenseListFragment.this.manager.getAllCategory().iterator();
                while (it.hasNext()) {
                    Category cat = (Category) it.next();
                    if (cat.getName().equals(ExpenseListFragment.this.catList.get(i))) {
                        catId = cat.getId();
                        catString=cat.getName();
                        break;
                    }else{
                        catId=-1000;
                        catString="ALL";
                    }
                }
                ExpenseListFragment.this.subcatList=new ArrayList<String>();
                ExpenseListFragment.this.subcatList.add("All");
                ExpenseListFragment.this.subcatList.addAll(ExpenseListFragment.this.getStringListFromSubCategory(ExpenseListFragment.this.manager.getSubCategoryByCategoryId(catId))) ;
                SpinnerAdapter subspinnerAdapter = new SpinnerAdapter(getContext(),ExpenseListFragment.this.subcatList);
                subspinnerAdapter.setDropDownViewResource(17367049);
                ExpenseListFragment.this.subcatFilterSpinner.setAdapter(subspinnerAdapter);
                subspinnerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        this.subcatFilterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Iterator it=ExpenseListFragment.this.manager.getAllSubCategory().iterator();
                while(it.hasNext()){
                    SubCategory sCat=(SubCategory)it.next();
                    if(sCat.getName().equals(ExpenseListFragment.this.subcatList.get(i))){
                        subCatId=sCat.getId();
                        subCatString=sCat.getName();
                        break;
                    }else{
                        subCatId=-1000;
                        subCatString="ALL";
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        this.recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.recyclerAdapter = new ExpenseRecyclerAdapter(getContext(), this.expenseList);
        this.recyclerAdapter.setClickListener(this);
        this.recyclerView.setAdapter(this.recyclerAdapter);
        this.fromDateBtn.setOnClickListener(new C02711());
        this.toDateBtn.setOnClickListener(new C02732());
        this.refreshBtn.setOnClickListener(new C02743());

        return view;
    }

    public ArrayList<Expense> getAllExpense() {
        return this.manager.getAllExpense();
    }

    public void onItemClick(View view, int position) {
        View dialogView = View.inflate(getContext(), R.layout.expense_details, null);
        AlertDialog alertDialog = new Builder(getContext()).create();
        this.datedetTV = (TextView) dialogView.findViewById(R.id.detdateTV);
        this.catdetTV = (TextView) dialogView.findViewById(R.id.detcatTV);
        this.subcatdetTV = (TextView) dialogView.findViewById(R.id.detsubcatTV);
        this.descrpdetTV = (TextView) dialogView.findViewById(R.id.detdescripTV);
        this.amountdetTV = (TextView) dialogView.findViewById(R.id.detamountV);
        this.balancedetTV = (TextView) dialogView.findViewById(R.id.detBalanceTV);
        Expense current = (Expense) this.expenseList.get(position);
        this.datedetTV.setText("Date: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(current.getDate())));
        this.catdetTV.setText("Category: " + current.getCategory());
        this.subcatdetTV.setText("SubCategory: " + current.getSubCategory());
        this.descrpdetTV.setText("Description: " + current.getDescription());
        this.amountdetTV.setText("Amount: " + String.valueOf(current.getAmount()));
        this.balancedetTV.setText("Balance: " + String.valueOf(current.getBalance()));
        alertDialog.setView(dialogView);
        alertDialog.show();
    }

    public void onButtonClick(View view, int position) {
        this.pos = position;
        new Builder(getContext()).setTitle((CharSequence) "Delete entry").setMessage((CharSequence) "Are you sure you want to delete this entry?").setPositiveButton(17039379, new C02765()).setNegativeButton(17039369, new C02754()).setIcon(17301543).show();
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
}
