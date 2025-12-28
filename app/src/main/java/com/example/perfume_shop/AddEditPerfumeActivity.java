package com.example.perfume_shop;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.perfume_shop.database.DatabaseHelper;
import com.example.perfume_shop.models.Perfume;
import com.example.perfume_shop.utils.ValidationUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AddEditPerfumeActivity extends AppCompatActivity {

    private TextInputLayout tilName, tilBrand, tilPrice, tilDescription, tilImageUrl;
    private TextInputEditText etName, etBrand, etPrice, etDescription, etImageUrl;
    private MaterialButton btnSave, btnCancel;
    
    private DatabaseHelper dbHelper;
    private boolean isEditMode = false;
    private int perfumeId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_perfume);

        // Initialize database
        dbHelper = new DatabaseHelper(this);

        // Initialize views
        initializeViews();

        // Check if edit mode
        checkEditMode();

        // Set click listeners
        btnSave.setOnClickListener(v -> savePerfume());
        btnCancel.setOnClickListener(v -> finish());
    }

    private void initializeViews() {
        tilName = findViewById(R.id.tilPerfumeName);
        tilBrand = findViewById(R.id.tilBrand);
        tilPrice = findViewById(R.id.tilPrice);
        tilDescription = findViewById(R.id.tilDescription);
        tilImageUrl = findViewById(R.id.tilImageUrl);

        etName = findViewById(R.id.etPerfumeName);
        etBrand = findViewById(R.id.etBrand);
        etPrice = findViewById(R.id.etPrice);
        etDescription = findViewById(R.id.etDescription);
        etImageUrl = findViewById(R.id.etImageUrl);

        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
    }

    private void checkEditMode() {
        isEditMode = getIntent().getBooleanExtra("IS_EDIT_MODE", false);

        if (isEditMode) {
            // Set title
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle("Edit Perfume");
            }

            // Get perfume data from intent
            perfumeId = getIntent().getIntExtra("PERFUME_ID", -1);
            String name = getIntent().getStringExtra("PERFUME_NAME");
            String brand = getIntent().getStringExtra("PERFUME_BRAND");
            double price = getIntent().getDoubleExtra("PERFUME_PRICE", 0.0);
            String description = getIntent().getStringExtra("PERFUME_DESCRIPTION");
            String imageUrl = getIntent().getStringExtra("PERFUME_IMAGE_URL");

            // Populate fields
            etName.setText(name);
            etBrand.setText(brand);
            etPrice.setText(String.valueOf(price));
            etDescription.setText(description);
            etImageUrl.setText(imageUrl);

            btnSave.setText("Update");
        } else {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle("Add Perfume");
            }
            btnSave.setText("Save");
        }
    }

    private void savePerfume() {
        // Clear previous errors
        tilName.setError(null);
        tilBrand.setError(null);
        tilPrice.setError(null);
        tilDescription.setError(null);
        tilImageUrl.setError(null);

        // Get input values
        String name = etName.getText().toString().trim();
        String brand = etBrand.getText().toString().trim();
        String priceStr = etPrice.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String imageUrl = etImageUrl.getText().toString().trim();

        // Validate inputs
        boolean isValid = true;

        if (ValidationUtils.isEmpty(name)) {
            tilName.setError(getString(R.string.error_field_required));
            isValid = false;
        }

        if (ValidationUtils.isEmpty(brand)) {
            tilBrand.setError(getString(R.string.error_field_required));
            isValid = false;
        }

        if (!ValidationUtils.isValidPrice(priceStr)) {
            tilPrice.setError(getString(R.string.error_invalid_price));
            isValid = false;
        }

        if (ValidationUtils.isEmpty(description)) {
            tilDescription.setError(getString(R.string.error_field_required));
            isValid = false;
        }

        if (ValidationUtils.isEmpty(imageUrl)) {
            tilImageUrl.setError(getString(R.string.error_field_required));
            isValid = false;
        }

        if (!isValid) {
            return;
        }

        // Parse price
        double price = Double.parseDouble(priceStr);

        // Create perfume object
        Perfume perfume = new Perfume(name, brand, price, description, imageUrl);

        if (isEditMode) {
            // Update existing perfume
            perfume.setId(perfumeId);
            int rowsUpdated = dbHelper.updatePerfume(perfume);
            
            if (rowsUpdated > 0) {
                Toast.makeText(this, "Perfume updated successfully!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to update perfume", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Add new perfume
            long id = dbHelper.insertPerfume(perfume);
            
            if (id > 0) {
                Toast.makeText(this, "Perfume added successfully!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to add perfume", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
