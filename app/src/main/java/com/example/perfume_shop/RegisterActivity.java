package com.example.perfume_shop;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.perfume_shop.database.DatabaseHelper;
import com.example.perfume_shop.models.User;
import com.example.perfume_shop.utils.ValidationUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout tilFullName, tilEmail, tilPassword, tilConfirmPassword, tilPhone;
    private TextInputEditText etFullName, etEmail, etPassword, etConfirmPassword, etPhone;
    private MaterialButton btnRegister;
    private TextView tvLogin;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize views
        initializeViews();

        // Initialize database
        databaseHelper = new DatabaseHelper(this);

        // Set click listeners
        btnRegister.setOnClickListener(v -> validateAndRegister());
        tvLogin.setOnClickListener(v -> navigateToLogin());
    }

    private void initializeViews() {
        tilFullName = findViewById(R.id.tilFullName);
        tilEmail = findViewById(R.id.tilEmail);
        tilPassword = findViewById(R.id.tilPassword);
        tilConfirmPassword = findViewById(R.id.tilConfirmPassword);
        tilPhone = findViewById(R.id.tilPhone);

        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        etPhone = findViewById(R.id.etPhone);

        btnRegister = findViewById(R.id.btnRegister);
        tvLogin = findViewById(R.id.tvLogin);
    }

    private void validateAndRegister() {
        // Clear previous errors
        clearErrors();

        String fullName = etFullName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();

        boolean isValid = true;

        // Validate full name
        if (!ValidationUtils.isValidName(fullName)) {
            tilFullName.setError(getString(R.string.error_empty_field));
            isValid = false;
        }

        // Validate email
        if (!ValidationUtils.isValidEmail(email)) {
            tilEmail.setError(getString(R.string.error_invalid_email));
            isValid = false;
        } else if (databaseHelper.checkEmailExists(email)) {
            tilEmail.setError(getString(R.string.error_email_exists));
            isValid = false;
        }

        // Validate password
        if (!ValidationUtils.isValidPassword(password)) {
            tilPassword.setError(getString(R.string.error_password_length));
            isValid = false;
        }

        // Validate confirm password
        if (!ValidationUtils.doPasswordsMatch(password, confirmPassword)) {
            tilConfirmPassword.setError(getString(R.string.error_password_mismatch));
            isValid = false;
        }

        // Validate phone
        if (!ValidationUtils.isValidPhone(phone)) {
            tilPhone.setError(getString(R.string.error_phone_length));
            isValid = false;
        }

        if (!isValid) {
            return;
        }

        // Create user and insert into database
        User user = new User(fullName, email, password, phone);
        boolean success = databaseHelper.insertUser(user);

        if (success) {
            Toast.makeText(this, getString(R.string.registration_success), Toast.LENGTH_SHORT).show();
            navigateToLogin();
        } else {
            Toast.makeText(this, "Registration failed. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearErrors() {
        tilFullName.setError(null);
        tilEmail.setError(null);
        tilPassword.setError(null);
        tilConfirmPassword.setError(null);
        tilPhone.setError(null);
    }

    private void navigateToLogin() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
