package com.stolica.screens.bloodPressure;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stolica.R;
import com.stolica.data.StoreData;
import com.stolica.models.Person;
import com.stolica.models.Statica;
import com.stolica.models.StaticaType;
import com.stolica.screens.bottom_sheets.AccountDetailsBottomSheet;
import com.stolica.screens.bottom_sheets.AddNewAccountBottomSheet;
import com.stolica.screens.bottom_sheets.BloodPressureBottomSheet;
import com.stolica.screens.bottom_sheets.BloodPressureDetailsBottomSheet;
import com.stolica.screens.bottom_sheets.ChangeAccountBottomSheet;
import com.stolica.screens.bottom_sheets.ClearAllBloodPressureDataBottomSheet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BloodPressureActivity
        extends AppCompatActivity
        implements BloodPressureBottomSheet.BottomSheetListener,
        ListOfBloodPressureAdapter.Listener,
        ClearAllBloodPressureDataBottomSheet.Listener,
        AddNewAccountBottomSheet.NewAccountBottomSheetListener, AccountDetailsBottomSheet.Listener, ChangeAccountBottomSheet.Listener, BloodPressureDetailsBottomSheet.Listener {

    private static final String TAG = "BPActivity";

    private BloodPressureBottomSheet bloodPressureBottomSheet;
    private ClearAllBloodPressureDataBottomSheet clearAllBloodPressureDataBottomSheet;
    private AddNewAccountBottomSheet addNewAccountBottomSheet;
    private BloodPressureDetailsBottomSheet bloodPressureDetailsBottomSheet;
    private AccountDetailsBottomSheet accountDetailsBottomSheet;
    private ListOfBloodPressureAdapter listOfBloodPressureAdapter;
    private ChangeAccountBottomSheet changeAccountBottomSheet;

    LinearLayout bottomSheetLayout;
    LinearLayout accountHolderName;
    RecyclerView staticasRecyclerView;
    private Button clearAllButton;
    private Button addBloodPressureButton;
    private TextView fullName;

    private StoreData storeData;
    private HashMap<StaticaType, Statica> lineGraphs;
    ArrayList<Statica> personsStaticas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_pressure);

        bottomSheetLayout = findViewById(R.id.blood_pressure_bottom_sheet);
        staticasRecyclerView = findViewById(R.id.list_of_past_bp_data);
        clearAllButton = findViewById(R.id.clear_all_action);
        addBloodPressureButton = findViewById(R.id.add_blood_pressure_action);
        accountHolderName = findViewById(R.id.account_name_holder);
        fullName = findViewById(R.id.account_full_name);

        clearAllButton
                .setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                clearAllBloodPressureDataBottomSheet.show(getSupportFragmentManager(), ""); }});

        addBloodPressureButton
                .setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                bloodPressureBottomSheet.show(getSupportFragmentManager(), ""); }});

        accountHolderName
                .setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                accountDetailsBottomSheet.show(getSupportFragmentManager(), "");
                            }
                        }
                );


        personsStaticas = new ArrayList<>();
        listOfBloodPressureAdapter = new ListOfBloodPressureAdapter(new ArrayList<Statica>(), this);
        staticasRecyclerView.setAdapter(listOfBloodPressureAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        staticasRecyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(staticasRecyclerView.getContext(),
                linearLayoutManager.getOrientation());
        staticasRecyclerView.addItemDecoration(dividerItemDecoration);

        storeData = StoreData.getStoreDataInstance();
        lineGraphs = storeData.getListofLineGraphs();
        personsStaticas.addAll(storeData.getPersonsStatica());
        listOfBloodPressureAdapter.updateStaticas(personsStaticas);

        bloodPressureBottomSheet = new BloodPressureBottomSheet(this);
        addNewAccountBottomSheet = new AddNewAccountBottomSheet(this);
        changeAccountBottomSheet = new ChangeAccountBottomSheet(this);

        ArrayList<Person> peopleAccounts = new ArrayList<>();
        if (storeData.personList() != null) {
            peopleAccounts.addAll(storeData.personList());
        }

        if (peopleAccounts.isEmpty()) {
            addNewAccountBottomSheet.show(getSupportFragmentManager(), "");
            addNewAccountBottomSheet.setCancelable(false);
        }

        if (storeData.personList() != null && !storeData.personList().isEmpty()) {
            fullName.setText(storeData.getCurrentPerson().getFullName());
            clearAllBloodPressureDataBottomSheet = new ClearAllBloodPressureDataBottomSheet(storeData.getCurrentPerson(), this);
            accountDetailsBottomSheet = new AccountDetailsBottomSheet(storeData.getCurrentPerson(), this);
        }
    }

    private void refresh() {
        Log.d("BloodPressureActivity", "refresh");

        storeData = StoreData.getStoreDataInstance();

        ArrayList<Person> peopleAccounts = new ArrayList<>();
        if (!storeData.personList().isEmpty()) {
            Log.d("BloodPressureActivity", "personListNotEmpty");

            personsStaticas.clear();
            personsStaticas.addAll(storeData.getPersonsStatica());
            listOfBloodPressureAdapter.updateStaticas(personsStaticas);
            peopleAccounts.addAll(storeData.personList());
            fullName.setText(storeData.getCurrentPerson().getFullName());
        } else {
            fullName.setText("");
        }

        if (peopleAccounts.isEmpty()) {
            addNewAccountBottomSheet.setCancelable(false);
            addNewAccountBottomSheet.show(getSupportFragmentManager(), "");
        }

        if (storeData.personList() != null && !storeData.personList().isEmpty()) {
            fullName.setText(storeData.getCurrentPerson().getFullName());
            clearAllBloodPressureDataBottomSheet = new ClearAllBloodPressureDataBottomSheet(storeData.getCurrentPerson(), this);
            accountDetailsBottomSheet = new AccountDetailsBottomSheet(storeData.getCurrentPerson(), this);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.add_blood_pressure_action) {
            bloodPressureBottomSheet.show(getSupportFragmentManager(), "");
        } else if (id == R.id.clear_all_action) {
            clearAllBloodPressureDataBottomSheet.show(getSupportFragmentManager(), "");
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAddButtonClicked(int systolic, int diastolic) {
        bloodPressureBottomSheet.dismiss();
        storeData.addPersonalStatica(systolic, diastolic);
        refrestListOfPersonalStaticas();
    }

    @Override
    public void onStaticaClicked(Statica statica) {
        bloodPressureDetailsBottomSheet = new BloodPressureDetailsBottomSheet(this, statica);
        bloodPressureDetailsBottomSheet.show(getSupportFragmentManager(), "");
    }

    @Override
    public void onClearAllDataClicked() {
        storeData.clearAllPersonalStatica();
        refrestListOfPersonalStaticas();
        clearAllBloodPressureDataBottomSheet.dismiss();
        refresh();
    }

    @Override
    public void onGoBackClicked() {
        clearAllBloodPressureDataBottomSheet.dismiss();
    }

    private void refrestListOfPersonalStaticas() {
        personsStaticas.clear();
        personsStaticas.addAll(storeData.getPersonsStatica());
        listOfBloodPressureAdapter.updateStaticas(personsStaticas);
    }

    @Override
    public void onContinueButtonClicked(Person person) {
        storeData.addPerson(person);
        addNewAccountBottomSheet.dismiss();
        refresh();
    }

    @Override
    public void onAddNewAccountClicked() {
        Log.d(TAG, "onAddNewAccountClicked");
        accountDetailsBottomSheet.dismiss();
        refresh();
        addNewAccountBottomSheet.setCancelable(true);
        addNewAccountBottomSheet.show(getSupportFragmentManager(), "");
    }

    @Override
    public void onChangeAccountClicked() {
        accountDetailsBottomSheet.dismiss();
        changeAccountBottomSheet.refreshData();
        changeAccountBottomSheet.show(getSupportFragmentManager(), "");


    }

    @Override
    public void onProfileSelected(int selectedProfilePosition) {
        Log.d(TAG, "onProfileSelected: " + selectedProfilePosition);
        storeData.updateCurrentProfile(selectedProfilePosition);
        changeAccountBottomSheet.dismiss();
        refresh();
    }

    @Override
    public void onBackButtonTapped() {
        changeAccountBottomSheet.dismiss();
    }

    @Override
    public void onBackClicked() {
        bloodPressureDetailsBottomSheet.dismiss();
    }
}
