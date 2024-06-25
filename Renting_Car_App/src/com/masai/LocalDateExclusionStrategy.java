package com.masai;
import java.time.LocalDate;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class LocalDateExclusionStrategy implements ExclusionStrategy {
    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        // Exclude java.time.LocalDate fields that Gson cannot access
        return f.getDeclaredClass() == LocalDate.class && f.getName().equals("year");
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }
}
