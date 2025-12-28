package com.example.perfume_shop;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

public class DashboardSelectionActivity extends AppCompatActivity {

    private MaterialCardView cardPublicApi, cardMyCollection;
    private MaterialButton btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_selection);

        // Initialize views
        cardPublicApi = findViewById(R.id.cardPublicApi);
        cardMyCollection = findViewById(R.id.cardMyCollection);
        btnLogout = findViewById(R.id.btnLogout);

        // Set click listeners
        cardPublicApi.setOnClickListener(v -> navigateToPublicApi());
        cardMyCollection.setOnClickListener(v -> navigateToMyCollection());
        btnLogout.setOnClickListener(v -> logout());
    }

    private void navigateToPublicApi() {
        Intent intent = new Intent(this, PublicApiActivity.class);
        startActivity(intent);
    }

    private void navigateToMyCollection() {
        Intent intent = new Intent(this, AdminDashboardActivity.class);
        startActivity(intent);
    }

    private void logout() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
