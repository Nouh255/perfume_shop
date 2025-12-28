package com.example.perfume_shop.utils;

import android.util.Patterns;

public class ValidationUtils {

    /**
     * Validate if a string is empty or null
     */
    public static boolean isEmpty(String text) {
        return text == null || text.trim().isEmpty();
    }

    /**
     * Validate email format
     */
    public static boolean isValidEmail(String email) {
        return !isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     * Validate password length (minimum 6 characters)
     */
    public static boolean isValidPassword(String password) {
        return !isEmpty(password) && password.length() >= 6;
    }

    /**
     * Validate if passwords match
     */
    public static boolean doPasswordsMatch(String password, String confirmPassword) {
        return !isEmpty(password) && !isEmpty(confirmPassword) && password.equals(confirmPassword);
    }

    /**
     * Validate phone number (10 digits)
     */
    public static boolean isValidPhone(String phone) {
        return !isEmpty(phone) && phone.replaceAll("[^0-9]", "").length() == 10;
    }

    /**
     * Validate price (must be numeric and greater than zero)
     */
    public static boolean isValidPrice(String priceStr) {
        if (isEmpty(priceStr)) {
            return false;
        }
        try {
            double price = Double.parseDouble(priceStr);
            return price > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Validate full name (not empty and contains at least 2 characters)
     */
    public static boolean isValidName(String name) {
        return !isEmpty(name) && name.trim().length() >= 2;
    }
}
