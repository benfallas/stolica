package com.stolica.models;

import java.util.ArrayList;

public class Person {

    private String name;
    private String lastName;
    private Date dateOfBirth;
    private ArrayList<Statica> persalStaticas;

    public Person() {
        name = null;
        lastName = null;
        dateOfBirth = new Date();
        persalStaticas = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return name + " " + lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getFormattedDateOfBirth() {
        return (dateOfBirth.getMonth() + 1) + "/" + dateOfBirth.getDay() + "/" + dateOfBirth.getYear();
    }

    public ArrayList<Statica> getPersalStaticas() {
        return persalStaticas;
    }

    public void setPersalStaticas(ArrayList<Statica> persalStaticas) {
        this.persalStaticas = persalStaticas;
    }

    // Overriding equals() to compare two Complex objects
    @Override
    public boolean equals(Object givenPerson) {

        // If the object is compared with itself then return true
        if (givenPerson == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(givenPerson instanceof Person)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        Person person = (Person) givenPerson;

        // Compare the data members and return accordingly
        return name.equalsIgnoreCase(person.name)
                && lastName.equalsIgnoreCase(person.lastName)
                && dateOfBirth.equals(person.dateOfBirth);
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("name:"+name).append("lastName:"+lastName)
                .append("dateOfBirth:"+dateOfBirth)
                .append("personalStaticas: " +persalStaticas)
                .toString();
    }
}
