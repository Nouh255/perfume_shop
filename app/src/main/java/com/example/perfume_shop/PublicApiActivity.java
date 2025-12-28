package com.example.perfume_shop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.perfume_shop.adapters.PublicPerfumeAdapter;
import com.example.perfume_shop.api.ApiService;
import com.example.perfume_shop.api.RetrofitClient;
import com.example.perfume_shop.models.Perfume;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PublicApiActivity extends AppCompatActivity {

    private RecyclerView rvPerfumes;
    private ProgressBar progressBar;
    private LinearLayout llEmptyState;
    private PublicPerfumeAdapter adapter;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_api);

        // Initialize views
        initializeViews();

        // Setup custom header
        setupCustomHeader();

        // Initialize API service
        apiService = RetrofitClient.getInstance().getApiService();

        // Setup RecyclerView
        setupRecyclerView();

        // Load perfumes from API
        loadPerfumes();
    }

    private void initializeViews() {
        rvPerfumes = findViewById(R.id.rvPerfumes);
        progressBar = findViewById(R.id.progressBar);
        llEmptyState = findViewById(R.id.llEmptyState);
    }

    private void setupCustomHeader() {
        ImageView ivMenuIcon = findViewById(R.id.ivMenuIcon);
        ivMenuIcon.setOnClickListener(v -> showMenu(v));
    }
    
    private void showMenu(View view) {
        android.widget.PopupMenu popupMenu = new android.widget.PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_dashboard, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_logout) {
                logout();
                return true;
            }
            return false;
        });
        popupMenu.show();
    }

    private void setupRecyclerView() {
        adapter = new PublicPerfumeAdapter(this);
        rvPerfumes.setLayoutManager(new LinearLayoutManager(this));
        rvPerfumes.setAdapter(adapter);
    }

    private void loadPerfumes() {
        showLoading(true);

        // Search for perfumes from Fragella API
        apiService.getAllPerfumes("perfume").enqueue(new Callback<List<Perfume>>() {
            @Override
            public void onResponse(@NonNull Call<List<Perfume>> call, @NonNull Response<List<Perfume>> response) {
                showLoading(false);

                if (response.isSuccessful() && response.body() != null) {
                    List<Perfume> perfumes = response.body();
                    
                    if (perfumes.isEmpty()) {
                        showEmptyState(true);
                    } else {
                        showEmptyState(false);
                        adapter.setPerfumeList(perfumes);
                    }
                } else {
                    Toast.makeText(PublicApiActivity.this, 
                            getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Perfume>> call, @NonNull Throwable t) {
                showLoading(false);
                Toast.makeText(PublicApiActivity.this, 
                        getString(R.string.network_error) + ": " + t.getMessage(), 
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showLoading(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        rvPerfumes.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    private void showEmptyState(boolean show) {
        llEmptyState.setVisibility(show ? View.VISIBLE : View.GONE);
        rvPerfumes.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    private void logout() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPerfumes();
    }
}
