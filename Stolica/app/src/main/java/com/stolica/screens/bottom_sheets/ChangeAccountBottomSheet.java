package com.stolica.screens.bottom_sheets;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
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
import com.stolica.models.Statica;

import org.w3c.dom.Text;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ChangeAccountBottomSheet extends BottomSheetDialogFragment implements ListOfProfilesAdapter.Listener {

    private static final String TAG = "ChangeAccountBS";

    TextView dateofBirth;
    TextView name;
    private StoreData storeData;
    private ArrayList<Person> profileAccountsList;
    private RecyclerView recyclerView;
    private ListOfProfilesAdapter listOfProfilesAdapter;
    private Listener listener;
    private Button backButton;

    public ChangeAccountBottomSheet(Listener listener) {
        Log.d(TAG, "ChangeAccountBottomSheet");
        storeData = StoreData.getStoreDataInstance();
        this.listener = listener;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.change_account_bottom_sheet, container, false);
        recyclerView = view.findViewById(R.id.list_of_profiles);
        backButton = view.findViewById(R.id.back);

        recyclerView.setAdapter(listOfProfilesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));

        backButton
                .setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                listener.onBackButtonTapped();
                            }
                        }
                );

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

    public void refreshData() {
        profileAccountsList = new ArrayList<>();
        if (storeData.personList() != null && !storeData.personList().isEmpty()) {
            Log.d(TAG, "profilesList");
            profileAccountsList.addAll(storeData.personList());
            Log.d(TAG, "profiles: " + profileAccountsList);
        }
        listOfProfilesAdapter = new ListOfProfilesAdapter(profileAccountsList, this);
    }

    @Override
    public void onProfileSelected(int currentPosition) {
        listener.onProfileSelected(currentPosition);
    }

    public interface Listener {

        void onProfileSelected(int selectedProfilePosition);

        void onBackButtonTapped();
    }
}
