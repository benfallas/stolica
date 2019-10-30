package com.stolica.models;

public class Statica {

    private StaticaType staticaType;
    private int topNumberSystolic;
    private int bottomNumberDiastolic;

    public Statica(StaticaType staticaType, int topNumberSystolic, int bottomNumberDiastolic) {
        this.staticaType = staticaType;
        this.topNumberSystolic = topNumberSystolic;
        this.bottomNumberDiastolic = bottomNumberDiastolic;
    }

    public Statica(int topNumberSystolic, int bottomNumberDiastolic) {
        this.topNumberSystolic = topNumberSystolic;
        this.bottomNumberDiastolic = bottomNumberDiastolic;
        this.staticaType = StaticaType.NONE;
    }

    public StaticaType getStaticaType() {
        return staticaType;
    }

    public int getTopNumberSystolic() {
        return topNumberSystolic;
    }

    public int getBottomNumberDiastolic() {
        return bottomNumberDiastolic;
    }

    @Override
    public String toString() {
        return new StringBuilder().append(topNumberSystolic).append(bottomNumberDiastolic).append(staticaType).toString();
    }
}
