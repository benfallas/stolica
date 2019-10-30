package com.stolica.screens.bottom_sheets;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.stolica.R;
import com.stolica.models.Date;
import com.stolica.models.Person;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AddNewAccountBottomSheet extends BottomSheetDialogFragment implements View.OnClickListener, TextWatcher, DatePicker.OnDateChangedListener {

    private NewAccountBottomSheetListener newAccountBottomSheetListener;

    private EditText nameTextView;
    private EditText lastNameTextView;
    private DatePicker datePicker;
    private Button continueButton;
    private Person person;
    private Date dateOfBirth;
    private Calendar calendar;

    public AddNewAccountBottomSheet(NewAccountBottomSheetListener newAccountBottomSheetListener) {
        this.newAccountBottomSheetListener = newAccountBottomSheetListener;
        dateOfBirth = new Date();
        person = new Person();
        calendar = Calendar.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_new_account_bottom_sheet, container, false);

        nameTextView = view.findViewById(R.id.name_input_field);
        lastNameTextView = view.findViewById(R.id.last_name_input_field);
        continueButton = view.findViewById(R.id.add_new_account_continue_button);
        datePicker = view.findViewById(R.id.date_of_birth_picker);
        datePicker.init(calendar.get(Calendar.YEAR) - 30, calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), this);

        continueButton.setOnClickListener(this);
        continueButton.setEnabled(false);
        nameTextView.addTextChangedListener(this);
        lastNameTextView.addTextChangedListener(this);

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

    @Override
    public void onClick(View v) {
        person.setName(nameTextView.getText().toString());
        person.setLastName(lastNameTextView.getText().toString());
        person.setDateOfBirth(dateOfBirth);
        newAccountBottomSheetListener.onContinueButtonClicked(person);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) { }

    @Override
    public void afterTextChanged(Editable s) {
        checkForContinue();
    }

    private void updateContinueButton(boolean enableButton) {
        continueButton.setEnabled(enableButton);
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        dateOfBirth.setDay(dayOfMonth);
        dateOfBirth.setMonth(monthOfYear);
        dateOfBirth.setYear(year);
        checkForContinue();
    }

    private void checkForContinue() {
        updateContinueButton(
                !TextUtils.isEmpty(nameTextView.getText())
                        && !TextUtils.isEmpty(lastNameTextView.getText())
                        && dateOfBirth.isComplete());
    }

    public interface NewAccountBottomSheetListener {
        void onContinueButtonClicked(Person person);
    }
}
