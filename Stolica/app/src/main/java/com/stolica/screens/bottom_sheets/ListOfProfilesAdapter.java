package com.stolica.screens.bottom_sheets;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stolica.R;
import com.stolica.models.Person;
import com.stolica.models.Statica;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListOfProfilesAdapter extends RecyclerView.Adapter<ListOfProfilesAdapter.ViewHolder> {

    private static final String TAG = "LBPressureAdapter";

    ArrayList<Person> profiles;
    Listener listener;

    public ListOfProfilesAdapter(ArrayList<Person> profiles, Listener listener) {
        Log.d(TAG, "profiles: " + profiles);

        this.profiles = profiles;
        this.listener = listener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        Log.d("ChangeAccount", "onCreateViewHolder");

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.account_profile_holder, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: Position: " + position);

        final Person person = profiles.get(position);
        Log.d(TAG, "person: " + person);

        TextView name = holder.name;
        TextView dateOfBirth = holder.dateOfBirth;

        name.setText(person.getFullName());
        dateOfBirth.setText(person.getFormattedDateOfBirth());
    }


    @Override
    public int getItemCount() {

        return profiles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView dateOfBirth;
        public TextView name;

        public ViewHolder(View itemView) {
            super(itemView);

            dateOfBirth = itemView.findViewById(R.id.date_of_birth);
            name = itemView.findViewById(R.id.name);

            itemView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            listener.onProfileSelected(getAdapterPosition());
                        }
                    }
            );
        }
    }

    public interface Listener {

        void onProfileSelected(int selectedAccount);
    }
}
