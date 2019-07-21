
package com.example.tycohanx.emsv2.Fragments;
import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.tycohanx.emsv2.Adapters.CategoryListAdapterRec;
import com.example.tycohanx.emsv2.Adapters.CategoryListAdapterRec.ItemClickListener;
import com.example.tycohanx.emsv2.Adapters.SpinnerAdapter;
import com.example.tycohanx.emsv2.Adapters.SubCategoryListAdapterRec;
import com.example.tycohanx.emsv2.R;
import com.example.tycohanx.emsv2.Managers.ExpenseManager;
import com.example.tycohanx.emsv2.Managers.ExportImportDB;
import com.example.tycohanx.emsv2.Models.Balance;
import com.example.tycohanx.emsv2.Models.Category;
import com.example.tycohanx.emsv2.Models.MainBalance;
import com.example.tycohanx.emsv2.Models.SubCategory;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;

public class AdminFragment extends Fragment implements ItemClickListener, SubCategoryListAdapterRec.ItemClickListener {
    ArrayList<String> AdjustSourceList;
    CategoryListAdapterRec adapter;
    EditText addBalanceAmountET;
    long addBalanceDate;
    Button addPartialBalanceBtn;
    Spinner adjustAmount;
    Button adjustBalanceBtn;
    Spinner adjustSourceSpinner;
    SpinnerAdapter adjustSourceSpinnerAdapter;
    EditText adjustedAmountET;
    Button backupBtn;
    EditText balanceAmountET;
    EditText balanceDescriptionET;
    ItemClickListener cItemClick;
    EditText catET;
    ArrayList<String> catList;
    Spinner catSpinner;
    ArrayList<Category> categoryList;
    Button closeBtn;
    ExportImportDB exportImportDB;
    Button getCategoryBtn;
    Button getSubCatergoryBtn;
    ExpenseManager manager;
    int pos;
    RecyclerView recyclerView;
    Button restoreBtn;
    SubCategoryListAdapterRec.ItemClickListener sItemClick;
    Button saveBalanceBtn;
    Button saveCategoryBtn;
    Button saveSubCategoryBtn;
    Button setDateBtn;
    ArrayList<String> sourceList;
    Spinner sourceSpinner;
    SpinnerAdapter sourceSpinnerAdapter;
    SpinnerAdapter spinnerAdapter;
    SubCategoryListAdapterRec subadapter;
    EditText subcatET;
    ArrayList<String> subcatList;
    ArrayList<SubCategory> subcategoryList;
    Balance balance;

    /* renamed from: com.tycohanx.ems.Fragments.AdminFragment$1 */
    class C02601 implements OnClickListener {
        C02601() {
        }

        public void onClick(View view) {
            if (AdminFragment.this.adjustedAmountET.getText().toString().length() < 1) {
                Toast.makeText(AdminFragment.this.getContext(), "please insert the amount to be adjusted first!", 0).show();
            } else if (AdminFragment.this.adjustSourceSpinner.getSelectedItem().toString() == "Cash") {
                balance = AdminFragment.this.manager.getLastBalance();
                balance.setCash(balance.getCash() + Integer.valueOf(AdminFragment.this.adjustedAmountET.getText().toString()).intValue());
                balance.setAccount(balance.getAccount() - Integer.valueOf(AdminFragment.this.adjustedAmountET.getText().toString()).intValue());
                if (AdminFragment.this.manager.updateBalance(balance)) {
                    Toast.makeText(AdminFragment.this.getContext(), "balance adjusted!", 0).show();
                } else {
                    Toast.makeText(AdminFragment.this.getContext(), "something went wrong!", 0).show();
                }
            } else {
                balance = AdminFragment.this.manager.getLastBalance();
                balance.setCash(balance.getCash() - Integer.valueOf(AdminFragment.this.adjustedAmountET.getText().toString()).intValue());
                balance.setAccount(balance.getAccount() + Integer.valueOf(AdminFragment.this.adjustedAmountET.getText().toString()).intValue());
                if (AdminFragment.this.manager.updateBalance(balance)) {
                    Toast.makeText(AdminFragment.this.getContext(), "balance adjusted!", 0).show();
                } else {
                    Toast.makeText(AdminFragment.this.getContext(), "something went wrong!", 0).show();
                }
            }
        }
    }

    /* renamed from: com.tycohanx.ems.Fragments.AdminFragment$2 */
    class C02612 implements OnClickListener {
        C02612() {
        }

        public void onClick(View view) {
            AdminFragment.this.saveCategory();
        }
    }

    /* renamed from: com.tycohanx.ems.Fragments.AdminFragment$3 */
    class C02623 implements OnClickListener {
        C02623() {
        }

        public void onClick(View view) {
            AdminFragment.this.getCategory();
        }
    }

    /* renamed from: com.tycohanx.ems.Fragments.AdminFragment$4 */
    class C02634 implements OnClickListener {
        C02634() {
        }

        public void onClick(View view) {
            AdminFragment.this.savesubcategory();
        }
    }

    /* renamed from: com.tycohanx.ems.Fragments.AdminFragment$5 */
    class C02645 implements OnClickListener {
        C02645() {
        }

        public void onClick(View view) {
            AdminFragment.this.getsubcategory();
        }
    }

    /* renamed from: com.tycohanx.ems.Fragments.AdminFragment$6 */
    class C02666 implements OnClickListener {
        C02666() {
        }

        public void onClick(View view) {
            final View dialogView = View.inflate(AdminFragment.this.getContext(), R.layout.date_time_picker, null);
            final AlertDialog alertDialog = new Builder(AdminFragment.this.getContext()).create();
            dialogView.findViewById(R.id.date_time_set).setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.date_picker);
                    Calendar calendar = new GregorianCalendar(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
                    AdminFragment.this.addBalanceDate = calendar.getTimeInMillis();
                    alertDialog.dismiss();
                }
            });
            alertDialog.setView(dialogView);
            alertDialog.show();
        }
    }

    /* renamed from: com.tycohanx.ems.Fragments.AdminFragment$7 */
    class C02677 implements OnClickListener {
        C02677() {
        }

        public void onClick(View view) {
            if (AdminFragment.this.balanceAmountET.getText().toString().length() < 1) {
                Toast.makeText(AdminFragment.this.getContext(), "please insert the balance amount to be added!", 0).show();
                return;
            }
            String description = AdminFragment.this.balanceDescriptionET.getText().toString();
            String source = AdminFragment.this.sourceSpinner.getSelectedItem().toString();
            long date = Calendar.getInstance().getTime().getTime();
            if (AdminFragment.this.addBalanceDate != 0) {
                date = AdminFragment.this.addBalanceDate;
            }
            int amount = Integer.valueOf(AdminFragment.this.balanceAmountET.getText().toString()).intValue();
            if (AdminFragment.this.manager.addMainBalance(new MainBalance(source, description, amount, date, 0, 0))) {
                Balance balance = AdminFragment.this.manager.getLastBalance();
                if (source == "Cash") {
                    balance.setCash(balance.getCash() + amount);
                    if (AdminFragment.this.manager.updateBalance(balance)) {
                        Toast.makeText(AdminFragment.this.getContext(), "Balance inserted & updated!", 0).show();
                    }
                } else if (source == "Account") {
                    balance.setAccount(balance.getAccount() + amount);
                    if (AdminFragment.this.manager.updateBalance(balance)) {
                        Toast.makeText(AdminFragment.this.getContext(), "Balance inserted & updated! ", 0).show();
                    }
                }
            }
        }
    }

    /* renamed from: com.tycohanx.ems.Fragments.AdminFragment$8 */
    class C02688 implements OnClickListener {
        C02688() {
        }

        public void onClick(View view) {

           // Toast.makeText(AdminFragment.this.getContext(), "Hoise kam!", Toast.LENGTH_SHORT).show();
           // ActivityCompat.requestPermissions(AdminFragment.this.getActivity(), new String[]{Manifest.permission.GET_ACCOUNTS, Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);


            if (ContextCompat.checkSelfPermission(AdminFragment.this.getContext(), "android.permission.WRITE_EXTERNAL_STORAGE") == 0 || ContextCompat.checkSelfPermission(AdminFragment.this.getContext(), "android.permission.READ_EXTERNAL_STORAGE") == 0) {
                AdminFragment.this.exportImportDB = new ExportImportDB(AdminFragment.this.getContext());
                if (AdminFragment.this.exportImportDB.exportDBData()) {
                    Toast.makeText(AdminFragment.this.getContext(), "export successful!", 0).show();
                    return;
                } else {
                    Toast.makeText(AdminFragment.this.getContext(), "backup failed!", 0).show();
                    return;
                }
            }
            ActivityCompat.requestPermissions(AdminFragment.this.getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 101:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(AdminFragment.this.getContext(), "Hoise kam!", Toast.LENGTH_SHORT).show();
                } else {
                    //not granted
                }
                break;
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(AdminFragment.this.getContext(), "Hoise kam!", Toast.LENGTH_SHORT).show();
                } else {
                    //not granted
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }

    /* renamed from: com.tycohanx.ems.Fragments.AdminFragment$9 */
    class C02699 implements OnClickListener {
        C02699() {
        }

        public void onClick(View view) {
            if (ContextCompat.checkSelfPermission(AdminFragment.this.getContext(), "android.permission.WRITE_EXTERNAL_STORAGE") == 0 || ContextCompat.checkSelfPermission(AdminFragment.this.getContext(), "android.permission.READ_EXTERNAL_STORAGE") == 0) {
                AdminFragment.this.exportImportDB = new ExportImportDB(AdminFragment.this.getContext());
                if (AdminFragment.this.exportImportDB.importDBData()) {
                    Toast.makeText(AdminFragment.this.getContext(), "successfully restored data!", 0).show();
                    return;
                } else {
                    Toast.makeText(AdminFragment.this.getContext(), "restore failed!", 0).show();
                    return;
                }
            }

            //  ActivityCompat.requestPermissions(AdminFragment.this.getActivity(), new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"}, 1);
            ActivityCompat.requestPermissions(AdminFragment.this.getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        }
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_fragment_layout, container, false);
        this.sItemClick = this;
        this.cItemClick = this;
        this.manager = new ExpenseManager(getContext());
        this.catET = (EditText) view.findViewById(R.id.catadminET);
        this.subcatET = (EditText) view.findViewById(R.id.subcatETadmin);
        this.adjustedAmountET = (EditText) view.findViewById(R.id.amountadjustET);
        this.balanceAmountET = (EditText) view.findViewById(R.id.blamountET);
        this.balanceDescriptionET = (EditText) view.findViewById(R.id.bldescriptionET);
        this.adjustAmount = (Spinner) view.findViewById(R.id.sourceToAdjustSpinneradmin);
        this.catSpinner = (Spinner) view.findViewById(R.id.catSpinneradmin);
        this.sourceSpinner = (Spinner) view.findViewById(R.id.blsourceSpinneradmin);
        this.adjustSourceSpinner = (Spinner) view.findViewById(R.id.sourceToAdjustSpinneradmin);
        this.saveCategoryBtn = (Button) view.findViewById(R.id.saveCategory);
        this.saveSubCategoryBtn = (Button) view.findViewById(R.id.saveSubCategory);
        this.getCategoryBtn = (Button) view.findViewById(R.id.getCategory);
        this.getSubCatergoryBtn = (Button) view.findViewById(R.id.getSubCategory);
        this.backupBtn = (Button) view.findViewById(R.id.backup);
        this.restoreBtn = (Button) view.findViewById(R.id.restore);
        this.adjustBalanceBtn = (Button) view.findViewById(R.id.saveAdjust);
        this.saveBalanceBtn = (Button) view.findViewById(R.id.bladdAdminBtn);
        this.setDateBtn = (Button) view.findViewById(R.id.bldateBtn);
        this.catList = getStringListFromCategory();
        this.subcatList = getStringListFromCategory();
        this.spinnerAdapter = new SpinnerAdapter(getContext(), this.catList);
        this.spinnerAdapter.setDropDownViewResource(17367049);
        this.catSpinner.setAdapter(this.spinnerAdapter);
        this.spinnerAdapter.notifyDataSetChanged();
        this.sourceList = new ArrayList();
        this.sourceList.add("Cash");
        this.sourceList.add("Account");
        this.sourceSpinnerAdapter = new SpinnerAdapter(getContext(), this.sourceList);
        this.sourceSpinnerAdapter.setDropDownViewResource(17367049);
        this.sourceSpinner.setAdapter(this.sourceSpinnerAdapter);
        this.sourceSpinnerAdapter.notifyDataSetChanged();
        this.AdjustSourceList = new ArrayList();
        this.adjustSourceSpinnerAdapter = new SpinnerAdapter(getContext(), this.AdjustSourceList);
        this.adjustSourceSpinnerAdapter.setDropDownViewResource(17367049);
        this.adjustSourceSpinner.setAdapter(this.sourceSpinnerAdapter);
        this.adjustSourceSpinnerAdapter.notifyDataSetChanged();
        this.adjustBalanceBtn.setOnClickListener(new C02601());
        this.saveCategoryBtn.setOnClickListener(new C02612());
        this.getCategoryBtn.setOnClickListener(new C02623());
        this.saveSubCategoryBtn.setOnClickListener(new C02634());
        this.getSubCatergoryBtn.setOnClickListener(new C02645());
        this.setDateBtn.setOnClickListener(new C02666());
        this.saveBalanceBtn.setOnClickListener(new C02677());
        this.backupBtn.setOnClickListener(new C02688());
        this.restoreBtn.setOnClickListener(new C02699());
        return view;
    }

    public ArrayList<Category> getAllCategory() {
        return this.manager.getAllCategory();
    }

    public ArrayList<SubCategory> getAllSubCategory() {
        return this.manager.getAllSubCategory();
    }

    public void saveCategory() {
        String name = this.catET.getText().toString();
        if (name.length() < 1) {
            Toast.makeText(getContext(), "please insert category name first!", 0).show();
            return;
        }
        Iterator it = this.catList.iterator();
        while (it.hasNext()) {
            if (name == ((String) it.next())) {
                Toast.makeText(getContext(), "Category Name already exists!", 0).show();
                return;
            }
        }
        if (!false) {
            if (this.manager.addCategory(new Category(name))) {
                Toast.makeText(getContext(), "Category added!", 0).show();
            }
            this.catList = getStringListFromCategory();
            this.spinnerAdapter = new SpinnerAdapter(getContext(), this.catList);
            this.spinnerAdapter.setDropDownViewResource(17367049);
            this.catSpinner.setAdapter(this.spinnerAdapter);
            this.spinnerAdapter.notifyDataSetChanged();
            this.catET.getText().clear();
        }
    }

    public void getCategory() {
        this.categoryList = getAllCategory();
        View dialogView = View.inflate(getContext(), R.layout.category_list_show, null);
        final AlertDialog alertDialog = new Builder(getContext()).create();
        this.recyclerView = (RecyclerView) dialogView.findViewById(R.id.catlistView);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.closeBtn = (Button) dialogView.findViewById(R.id.closeBtn);
        this.closeBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertDialog.setView(dialogView);
        alertDialog.setTitle("Categories");
        alertDialog.show();
        this.adapter = new CategoryListAdapterRec(getContext(), this.categoryList);
        this.adapter.setClickListener(this.cItemClick);
        this.recyclerView.setAdapter(this.adapter);
        this.adapter.notifyDataSetChanged();
    }

    public void savesubcategory() {
        boolean booly = false;
        String name = this.subcatET.getText().toString();
        if (name.length() < 1) {
            Toast.makeText(getContext(), "please insert subcategory name first!", 0).show();
            return;
        }
        String catName = this.catSpinner.getSelectedItem().toString();
        int catId = 0;
        Iterator it = this.manager.getAllCategory().iterator();
        while (it.hasNext()) {
            Category cat = (Category) it.next();
            if (cat.getName().toString().equals(catName)) {
                booly = false;
                catId = cat.getId();
                break;
            }
            booly = true;
        }
        if (booly) {
            Toast.makeText(getContext(), "Please select a valid category!", 0).show();
            return;
        }
        if (this.manager.addSubCategory(new SubCategory(catId, name))) {
            Toast.makeText(getContext(), "SubCategory Inserted!", 0).show();
        }
        this.subcatET.getText().clear();
    }

    public void getsubcategory() {
        this.subcategoryList = getAllSubCategory();
        View dialogView = View.inflate(getContext(), R.layout.category_list_show, null);
        final AlertDialog alertDialog = new Builder(getContext()).create();
        this.recyclerView = (RecyclerView) dialogView.findViewById(R.id.catlistView);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.closeBtn = (Button) dialogView.findViewById(R.id.closeBtn);
        this.closeBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertDialog.setView(dialogView);
        alertDialog.setTitle("Categories");
        alertDialog.show();
        this.subadapter = new SubCategoryListAdapterRec(getContext(), this.subcategoryList);
        this.subadapter.setClickListener(this.sItemClick);
        this.recyclerView.setAdapter(this.subadapter);
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

    public ArrayList<String> getStringListFromSubCategory() {
        ArrayList<SubCategory> subcatList = this.manager.getAllSubCategory();
        ArrayList<String> subcatListString = new ArrayList();
        Iterator it = subcatList.iterator();
        while (it.hasNext()) {
            subcatListString.add(((SubCategory) it.next()).getName());
        }
        return subcatListString;
    }

    public void onItemClick(View view, int position) {
    }

    public void onButtonClick(View view, final int position) {
        new Builder(getContext()).setTitle((CharSequence) "Delete entry").setMessage((CharSequence) "Are you sure you want to delete this entry?").setPositiveButton(17039379, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (AdminFragment.this.manager.deleteCategoryById(((Category) AdminFragment.this.categoryList.get(position)).getId())) {
                    Toast.makeText(AdminFragment.this.getContext(), "category deleted!", 0).show();
                    AdminFragment.this.categoryList = AdminFragment.this.manager.getAllCategory();
                    AdminFragment.this.adapter = new CategoryListAdapterRec(AdminFragment.this.getContext(), AdminFragment.this.categoryList);
                    AdminFragment.this.adapter.setClickListener(AdminFragment.this.cItemClick);
                    AdminFragment.this.recyclerView.setAdapter(AdminFragment.this.adapter);
                }
            }
        }).setNegativeButton(17039369, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        }).setIcon(17301543).show();
    }

    public void onItemClickSub(View view, int position) {
    }

    public void onButtonClickSub(View view, int position) {
        this.pos = position;
        new Builder(getContext()).setTitle((CharSequence) "Delete entry").setMessage((CharSequence) "Are you sure you want to delete this entry?").setPositiveButton(17039379, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (AdminFragment.this.manager.deleteSubCategoryById(((SubCategory) AdminFragment.this.subcategoryList.get(AdminFragment.this.pos)).getId())) {
                    Toast.makeText(AdminFragment.this.getContext(), "subcategory deleted!", 0).show();
                    AdminFragment.this.subcategoryList = AdminFragment.this.manager.getAllSubCategory();
                    AdminFragment.this.subadapter = new SubCategoryListAdapterRec(AdminFragment.this.getContext(), AdminFragment.this.subcategoryList);
                    AdminFragment.this.subadapter.setClickListener(AdminFragment.this.sItemClick);
                    AdminFragment.this.recyclerView.setAdapter(AdminFragment.this.subadapter);
                }
            }
        }).setNegativeButton(17039369, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        }).setIcon(17301543).show();
    }
}
