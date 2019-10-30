package com.stolica.screens.bloodPressure;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stolica.R;
import com.stolica.models.Statica;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListOfBloodPressureAdapter extends RecyclerView.Adapter<ListOfBloodPressureAdapter.ViewHolder> {

    private static final String TAG = "LBloodPressureAdapter";

    ArrayList<Statica> staticas;
    Listener listener;

    public ListOfBloodPressureAdapter(ArrayList<Statica> staticas, Listener listener) {
        Log.d(TAG, "ListOfBloodPressureAdapter");
        this.staticas = staticas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.pass_blood_pressure_holder, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: Position: " + position);

        final Statica statica = staticas.get(position);

        TextView systolicView = holder.systolicaValue;
        TextView diastolic = holder.diastolicaValue;

        Log.d(TAG, "topNumber: "  + statica.getTopNumberSystolic());
        Log.d(TAG, "bottomNumber: "  + statica.getBottomNumberDiastolic());

        systolicView.setText(String.valueOf(statica.getTopNumberSystolic()));
        diastolic.setText(String.valueOf(statica.getBottomNumberDiastolic()));
        systolicView.setBackgroundResource(getBackgroundColor(statica.getTopNumberSystolic(), statica.getBottomNumberDiastolic()));
        diastolic.setBackgroundResource(getBackgroundColor(statica.getTopNumberSystolic(), statica.getBottomNumberDiastolic()));

        holder.bloodPressureRow.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onStaticaClicked(statica);
                    }
                }
        );
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

    @Override
    public int getItemCount() {
        Log.d(TAG, "size: " + staticas.size());

        return staticas.size();
    }

    public void updateStaticas(ArrayList<Statica> givenStaticas) {
        staticas.clear();
        staticas.addAll(givenStaticas);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout bloodPressureRow;
        public TextView systolicaValue;
        public TextView diastolicaValue;

        public ViewHolder(View itemView) {
            super(itemView);

            systolicaValue = (TextView) itemView.findViewById(R.id.systolic_value);
            diastolicaValue = itemView.findViewById(R.id.diastolic_value);
            bloodPressureRow = itemView.findViewById(R.id.blood_pressure_row);
        }
    }

    public interface Listener {

        void onStaticaClicked(Statica statica);
    }
}
