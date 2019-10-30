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
import com.stolica.models.Person;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ClearAllBloodPressureDataBottomSheet extends BottomSheetDialogFragment {

    private Listener listener;
    private Person person;

    private Button clearAllDataButton;
    private Button goBackButton;
    private TextView description;

    public ClearAllBloodPressureDataBottomSheet(Person person, Listener listener) {
        setCancelable(true);
        this.listener = listener;
        this.person = person;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.clear_all_blood_pressure_data, container, false);

        clearAllDataButton = view.findViewById(R.id.clear_all_data);
        goBackButton = view.findViewById(R.id.go_back);
        description = view.findViewById(R.id.clear_all_data_description);

        description.setText(String.format(getString(R.string.delete_all_blood_pressure_data_title), person.getFullName()));

        clearAllDataButton
                .setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                listener.onClearAllDataClicked();
                            }});

        goBackButton
                .setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                listener.onGoBackClicked();
                            }});

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
        void onClearAllDataClicked();

        void onGoBackClicked();
    }
}
