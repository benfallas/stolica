package com.stolica.screens.bottom_sheets;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.stolica.R;
import com.stolica.data.StoreData;
import com.stolica.models.Statica;
import com.stolica.models.StaticaType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BloodPressureDetailsBottomSheet extends BottomSheetDialogFragment {

    private static final String TAG = "BPDetailsBottomSheet";

    private StoreData storeData;
    private CombinedChart chart;
    private CombinedData tableData;
    private HashMap<StaticaType, Statica> listOfStaticas;
    ArrayList<Statica> personsStaticas;
    private Statica selectedStatica;
    private Button goBackButton;
    private Listener listener;
    private View currentBloodPressureColor;

    public BloodPressureDetailsBottomSheet(Listener listener, Statica statica) {
        setCancelable(true);
        this.listener = listener;
        tableData = new CombinedData();
        personsStaticas = new ArrayList<>();
        storeData = StoreData.getStoreDataInstance();
        listOfStaticas = storeData.getListofLineGraphs();
        personsStaticas.addAll(storeData.getPersonsStatica());
        selectedStatica = statica;
        Log.d(TAG, "constructor");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.blood_pressure_details, container, false);
        chart = view.findViewById(R.id.blood_pressure_details_chart);
        goBackButton = view.findViewById(R.id.go_back_button);
        currentBloodPressureColor = view.findViewById(R.id.current_blood_pressure_view);

        goBackButton
                .setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                listener.onBackClicked(); }});
        Log.d(TAG, "onCreateView");

        currentBloodPressureColor.setBackgroundResource(
                getBackgroundColor(
                        selectedStatica.getTopNumberSystolic(),
                        selectedStatica.getBottomNumberDiastolic()));

        setupChart();

        return view;
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

    private void setupChart() {
        Log.d(TAG, "setupChart");

        chart.getDescription().setEnabled(false);
        chart.setBackgroundColor(Color.WHITE);
        chart.setDrawGridBackground(false);
        chart.setDrawBarShadow(false);
        chart.setHighlightFullBarEnabled(false);


        // draw bars behind lines
        chart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.LINE});

        Legend l = chart.getLegend();
        l.setWordWrapEnabled(true);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setEnabled(false);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);

        tableData = new CombinedData();


        chart.setTouchEnabled(true);
        chart.setPinchZoom(true);

        tableData.setData(generateLineData());
        chart.setData(tableData);
        chart.invalidate();
    }

    private LineData generateLineData() {

        LineData lineData= new LineData();

        for (Statica statica : listOfStaticas.values()) {
            LineDataSet set = new LineDataSet(getEntriesForStatica(statica.getTopNumberSystolic(), statica.getBottomNumberDiastolic()), "");

            set.setLineWidth(0);
            set.setMode(LineDataSet.Mode.LINEAR);


            set.setFillColor(Color.RED);
            set.setDrawValues(false);
            set.setDrawFilled(true);
            set.setDrawCircles(false);
            set.setValueTextColor(Color.rgb(240, 238, 70));

            set.setAxisDependency(YAxis.AxisDependency.LEFT);


            if (statica.getStaticaType() == StaticaType.LOW) {
                set.setFillColor(Color.BLUE);
            } else if (statica.getStaticaType() == StaticaType.IDEAL) {
                set.setFillColor(Color.GREEN);
            } else if (statica.getStaticaType() == StaticaType.PREHIGH) {
                set.setFillColor(Color.YELLOW);
            } else if (statica.getStaticaType() == StaticaType.HIGH) {
                set.setFillColor(Color.RED);
            }

            lineData.addDataSet(set);
        }

        ArrayList<Entry> entries = new ArrayList<>();
        if (!personsStaticas.isEmpty()) {
            entries.add(new Entry(
                    selectedStatica.getBottomNumberDiastolic(), selectedStatica.getTopNumberSystolic()));
        }

        if (!entries.isEmpty()) {

            LineDataSet bPExample = new LineDataSet(entries, "Current BP");
            bPExample.setLineWidth(5);
            bPExample.setColor(Color.WHITE);
            bPExample.setMode(LineDataSet.Mode.LINEAR);
            bPExample.setDrawValues(true);
            bPExample.setCircleRadius(10);
            bPExample.setValueTextSize(0f);


            bPExample.setAxisDependency(YAxis.AxisDependency.LEFT);
            lineData.addDataSet(bPExample);
        }

        return lineData;
    }

    List<Entry> getEntriesForStatica(int topNumberSystolic, int bottomNumberDiastolic) {
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0, topNumberSystolic));
        entries.add(new Entry(bottomNumberDiastolic, topNumberSystolic));
        entries.add(new Entry(bottomNumberDiastolic, 0));
        return entries;
    }

    private int getBackgroundColor(int topNumberSystolic, int bottomNumberDiastolic) {
        Log.d(TAG, "topNum: " + topNumberSystolic + ", bottomNum: " + bottomNumberDiastolic);

        if (topNumberSystolic < 90 && bottomNumberDiastolic < 60) {
            Log.d(TAG, "BLUE ");

            return R.color.blue_blood_pressure;
        } else if (topNumberSystolic < 120 && bottomNumberDiastolic < 80) {
            Log.d(TAG, "GREEN ");
            return R.color.green_blood_pressure;
        } else if (topNumberSystolic < 140 && bottomNumberDiastolic < 90) {
            Log.d(TAG, "YELLOW ");
            return R.color.orange_blood_pressure;
        } else {
            Log.d(TAG, "RED ");

            return R.color.red_blood_pressure;
        }
    }

    public interface Listener {
        void onBackClicked();
    }
}
