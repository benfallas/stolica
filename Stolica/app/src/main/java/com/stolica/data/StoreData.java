package com.stolica.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stolica.models.Person;
import com.stolica.models.Statica;
import com.stolica.models.StaticaType;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StoreData {

    private static final String TAG = "StoreData";

    private static final String PROFILE_ACCOUNTS = "com.stolica.data.PROFILE_ACCOUNTS";
    private static final String STATICAS = "com.stolica.data.STATICAS";

    private static SharedPreferences sharedPreferences;
    private static Gson gson;
    private static StoreData storeDataInstance;
    private HashMap<StaticaType, Statica> listOfStaticas;

    private static int currentPerson;

    public static void initializeStoreData(Context context) {
        Log.d(TAG, "initializeStoreData");
        if (storeDataInstance == null) {
            Log.d(TAG, "instantiateStoreData");

            storeDataInstance = new StoreData(context);
        }
    }

    public static StoreData getStoreDataInstance() {
        return storeDataInstance;
    }

    private StoreData(Context context) {
        sharedPreferences = context.getSharedPreferences("com.stolica.com", Context.MODE_PRIVATE);
        listOfStaticas = getDefaultList();
        gson = new Gson();
        currentPerson = 0;
    }

    public void updateCurrentProfile(int updatedCurrentProfile) {
        currentPerson = updatedCurrentProfile;
    }

    public ArrayList<Person> personList() {
        Log.d(TAG, "personList");

        String peopleString =  sharedPreferences.getString(PROFILE_ACCOUNTS, "");
        Log.d(TAG, "profileAccounts: " + peopleString);

        Type type = new TypeToken<ArrayList<Person>>(){}.getType();
        return gson.fromJson(peopleString, type);
    }

    public Person getCurrentPerson() {
        return personList().get(currentPerson);
    }

    public ArrayList<Statica> getPersonsStatica() {
        Log.d(TAG, "getPersonStatica");

        String peopleString =  sharedPreferences.getString(PROFILE_ACCOUNTS, "");
        Log.d(TAG, "profileAccounts: " + peopleString);

        Type type = new TypeToken<ArrayList<Person>>(){}.getType();
        ArrayList<Person> people = gson.fromJson(peopleString, type);
        if (people == null || people.isEmpty()) {
            Log.d(TAG, "profileAccountsEmpty");

            return new ArrayList<>();
        }

        Person person = people.get(currentPerson);
        if (person == null) {
            Log.d(TAG, "currentPersonNull");

            return new ArrayList<>();
        }


        if (person.getPersalStaticas() == null) {
            Log.d(TAG, "personalStaticasNull");

            return new ArrayList<>();
        }

        return person.getPersalStaticas();
    }

    public void addPersonalStatica(int topNumberSystolic, int bottomNumberDiastolic) {
        Log.d(TAG, "addPersonalStatica");
        Log.d(TAG, "topNum: " + topNumberSystolic + ", bottomNum: " + bottomNumberDiastolic);


        String peopleString =  sharedPreferences.getString(PROFILE_ACCOUNTS, "");
        Log.d(TAG, "profileAccounts: " + peopleString);

        Type type = new TypeToken<ArrayList<Person>>(){}.getType();
        ArrayList<Person> people = gson.fromJson(peopleString, type);

        if (people == null) {
            Log.d(TAG, "profileAccountsNull");

            people = new ArrayList<>();
        }
        Person person = people.get(currentPerson);

        if (person == null) {
            Log.d(TAG, "currentPersonNull");

            return;
        }

        if (person.getPersalStaticas() == null) {
            Log.d(TAG, "personalStaticasNull");

            ArrayList<Statica> staticas = new ArrayList<>();
            Statica statica = new Statica(topNumberSystolic, bottomNumberDiastolic);
            staticas.add(statica);
            person.setPersalStaticas(staticas);
        } else {
            Log.d(TAG, "personalStaticaNotNull");

            ArrayList<Statica> staticas = person.getPersalStaticas();
            Statica statica = new Statica(topNumberSystolic, bottomNumberDiastolic);
            staticas.add(0, statica);
            person.setPersalStaticas(staticas);
        }

        String updatedPeopleString = gson.toJson(people);
        Log.d(TAG, "updatedProfiles: " + updatedPeopleString);

        sharedPreferences.edit().putString(PROFILE_ACCOUNTS, updatedPeopleString).apply();

    }

    public void clearAllPersonalStatica() {
        Log.d(TAG, "clearAllPersonalStatica");

        String peopleString =  sharedPreferences.getString(PROFILE_ACCOUNTS, "");
        Log.d(TAG, "profileAccounts: " + peopleString);
        Type type = new TypeToken<ArrayList<Person>>(){}.getType();
        ArrayList<Person> people = gson.fromJson(peopleString, type);

        if (people == null) {
            Log.d(TAG, "profilesNull");

            return;
        }

        Log.d(TAG, "removeCurrentPerson");

        people.remove(currentPerson);
        currentPerson = 0;

        String updatedPeopleString = gson.toJson(people);
        Log.d(TAG, "updatedProfiles: " + updatedPeopleString);

        sharedPreferences.edit().putString(PROFILE_ACCOUNTS, updatedPeopleString).apply();
    }



    public HashMap<StaticaType, Statica> getListofLineGraphs() {
        String defaultListOfStaticas = gson.toJson(listOfStaticas);
        Type type = new TypeToken<HashMap<StaticaType, Statica>>(){}.getType();
        return gson.fromJson(defaultListOfStaticas, type);
    }

    public void updatePersonList(ArrayList<Person> personList) {
        Log.d(TAG, "updateProfilesLIst");

        String people = gson.toJson(personList);
        Log.d(TAG, "updatedProfiles: " + people);

        sharedPreferences.edit().putString(PROFILE_ACCOUNTS, people).apply();
    }

    public void addPerson(Person person) {
        Log.d(TAG, "addPerson");

        if (personList() == null) {
            Log.d(TAG, "profilesNull");

            ArrayList<Person> people = new ArrayList<>();
            people.add(person);
            updatePersonList(people);
            currentPerson = 0;
        } else {
            Log.d(TAG, "profilesNotNull");

            ArrayList<Person> currentPeople = personList();
            currentPeople.add(0, person);
            currentPerson = currentPeople.size() -1;
            updatePersonList(currentPeople);
        }
    }

    private HashMap<StaticaType, Statica> getDefaultList() {
        listOfStaticas = new HashMap<>();

        // Less than 1
        listOfStaticas.put(StaticaType.HIGH, new Statica(StaticaType.HIGH, 190, 100));
        listOfStaticas.put(StaticaType.PREHIGH, new Statica(StaticaType.PREHIGH, 140, 90));
        listOfStaticas.put(StaticaType.IDEAL, new Statica(StaticaType.IDEAL, 120, 80));
        listOfStaticas.put(StaticaType.LOW, new Statica(StaticaType.LOW, 90, 60));
        return listOfStaticas;
    }
}
