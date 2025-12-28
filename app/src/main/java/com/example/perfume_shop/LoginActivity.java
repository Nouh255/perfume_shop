package com.example.perfume_shop;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.perfume_shop.database.DatabaseHelper;
import com.example.perfume_shop.utils.ValidationUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout tilEmail, tilPassword;
    private TextInputEditText etEmail, etPassword;
    private MaterialButton btnLogin;
    private TextView tvRegister;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views
        initializeViews();

        // Initialize database
        databaseHelper = new DatabaseHelper(this);

        // Set click listeners
        btnLogin.setOnClickListener(v -> validateAndLogin());
        tvRegister.setOnClickListener(v -> navigateToRegister());
    }

    private void initializeViews() {
        tilEmail = findViewById(R.id.tilEmail);
        tilPassword = findViewById(R.id.tilPassword);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);
    }

    private void validateAndLogin() {
        // Clear previous errors
        tilEmail.setError(null);
        tilPassword.setError(null);

        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        boolean isValid = true;

        // Validate email
        if (!ValidationUtils.isValidEmail(email)) {
            tilEmail.setError(getString(R.string.error_invalid_email));
            isValid = false;
        }

        // Validate password
        if (ValidationUtils.isEmpty(password)) {
            tilPassword.setError(getString(R.string.error_empty_field));
            isValid = false;
        }

        if (!isValid) {
            return;
        }

        // Check credentials in database
        if (databaseHelper.checkUserCredentials(email, password)) {
            Toast.makeText(this, getString(R.string.login_success), Toast.LENGTH_SHORT).show();
            navigateToDashboard();
        } else {
            Toast.makeText(this, getString(R.string.login_failed), Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateToRegister() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    private void navigateToDashboard() {
        Intent intent = new Intent(LoginActivity.this, DashboardSelectionActivity.class);
        startActivity(intent);
        finish();
    }
}
