package com.example.skatespots;

import com.google.maps.GeoApiContext;

public final class GeoApiContextSingleton {

    private static GeoApiContextSingleton instance = null;

    private GeoApiContextSingleton() {
    }

    public static GeoApiContext context = new GeoApiContext.Builder()
            .apiKey("AIzaSyAHu2rc07DmlSD9dUSQAoevnexR8DWXTgE")
            .build();

    public static GeoApiContextSingleton getInstance() {
        if (instance == null) {

            if (instance == null) {
                instance = new GeoApiContextSingleton();
            }
        }
        return instance;
    }
}
