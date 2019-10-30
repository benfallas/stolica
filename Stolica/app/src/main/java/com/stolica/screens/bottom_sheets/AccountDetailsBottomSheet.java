package com.stolica.screens.bottom_sheets;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.stolica.R;
import com.stolica.data.StoreData;
import com.stolica.models.Person;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AccountDetailsBottomSheet extends BottomSheetDialogFragment {

    private StoreData storeData;
    private Listener listener;

    private Person personProfile;
    private TextView fullNameTextView;
    private TextView dateOfBirth;
    private Button addNewAccount;
    private Button changeProfile;

    public AccountDetailsBottomSheet(Person person, Listener listener) {
        setCancelable(true);
        personProfile = person;
        storeData = StoreData.getStoreDataInstance();
        this.listener = listener;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.account_details_bottom_sheet, container, false);



        fullNameTextView = view.findViewById(R.id.account_full_name);
        dateOfBirth = view.findViewById(R.id.account_dob);
        addNewAccount = view.findViewById(R.id.add_new_account);
        changeProfile = view.findViewById(R.id.change_profile);

        fullNameTextView.setText(personProfile.getFullName());
        dateOfBirth.setText(personProfile.getFormattedDateOfBirth());

        addNewAccount
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onAddNewAccountClicked(); }});

        changeProfile.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onChangeAccountClicked(); }});


        if (storeData.personList().size() > 1) {
            changeProfile.setVisibility(View.VISIBLE);
        } else {
            changeProfile.setVisibility(View.GONE);
        }
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                BottomSheetDialog d = (BottomSheetDialog) dialog;

                FrameLayout bottomSheet = (FrameLayout) d.findViewById(com.google.android.material.R.id.design_bottom_sheet);
                BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        return dialog;
    }

    public interface Listener {

        void onAddNewAccountClicked();

        void onChangeAccountClicked();
    }


}
