package com.stolica.models;

public class Date {

    private int year;
    private int month;
    private int day;

    public Date() {
        year = -1;
        month = -1;
        day = -1;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public boolean isComplete() {
        return year > 0 && month > 0 && day > 0;
    }

    // Overriding equals() to compare two Complex objects
    @Override
    public boolean equals(Object givenDateOfBirth) {

        // If the object is compared with itself then return true
        if (givenDateOfBirth == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(givenDateOfBirth instanceof Date)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        Date dateOfBirth = (Date) givenDateOfBirth;

        // Compare the data members and return accordingly
        return day == dateOfBirth.getDay()
                && month == dateOfBirth.getMonth()
                && year == dateOfBirth.getYear();
    }

    @Override
    public String toString() {
        return new StringBuilder().append("day:"+day).append("month:"+month).append("year:"+year).toString();
    }
}
