package com.stolica.screens.bottom_sheets;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.stolica.R;

import org.w3c.dom.Text;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

public class BloodPressureBottomSheet extends BottomSheetDialogFragment implements TextWatcher {

    private BottomSheetListener bottomSheetListener;

    private EditText systolicInput;
    private EditText diastolicInput;
    private Button continueButton;

    public BloodPressureBottomSheet(BottomSheetListener bottomSheetListener) {
        this.bottomSheetListener = bottomSheetListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.blood_pressure_bottom_sheet, container, false);


        systolicInput = v.findViewById(R.id.systolic_input);
        diastolicInput = v.findViewById(R.id.diastolic_input);
        continueButton = v.findViewById(R.id.add_blood_pressure_button);
        continueButton.setEnabled(false);

        systolicInput.addTextChangedListener(this);
        diastolicInput.addTextChangedListener(this);

        continueButton
                .setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                bottomSheetListener.onAddButtonClicked(
                                        Integer.valueOf(systolicInput.getText().toString()),
                                        Integer.valueOf(diastolicInput.getText().toString()));
                            }
                        }
                );

        return v;
    }

    @NonNull @Override
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
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        continueButton.setEnabled(false);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) { }

    @Override
    public void afterTextChanged(Editable s) {
        checkForFilledData();
    }

    private void checkForFilledData() {
        continueButton.setEnabled(!TextUtils.isEmpty(systolicInput.getText().toString()) && !TextUtils.isEmpty(diastolicInput.getText().toString()));
    }

    public interface BottomSheetListener {
        void onAddButtonClicked(int systolic, int diastolic);
    }


}
