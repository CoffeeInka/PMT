package com.xite.core;


import java.util.Calendar;

public class Helpers {

    public static String getUniqueString(String prefix) {
        Calendar cal = Calendar.getInstance();
        return String.format(prefix + "_%tN", cal);

    }

    public static String getDate() {
        Calendar cal = Calendar.getInstance();
        int year = Integer.parseInt(String.format("%tY", cal)) - 10;
        return String.format("%td%tm" + year, cal, cal);
    }
}
