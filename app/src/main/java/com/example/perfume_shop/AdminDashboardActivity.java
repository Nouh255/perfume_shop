package com.example.perfume_shop;

import android.app.AlertDialog;
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

import com.example.perfume_shop.adapters.PerfumeAdapter;
import com.example.perfume_shop.database.DatabaseHelper;
import com.example.perfume_shop.models.Perfume;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class AdminDashboardActivity extends AppCompatActivity implements PerfumeAdapter.OnPerfumeClickListener {

    private RecyclerView rvPerfumes;
    private ProgressBar progressBar;
    private LinearLayout llEmptyState;
    private FloatingActionButton fabAdd;
    private PerfumeAdapter adapter;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        // Initialize views
        initializeViews();

        // Setup custom header
        setupCustomHeader();

        // Initialize database
        dbHelper = new DatabaseHelper(this);

        // Setup RecyclerView
        setupRecyclerView();

        // Load perfumes from database
        loadPerfumes();

        // FAB click listener
        fabAdd.setOnClickListener(v -> navigateToAddPerfume());
    }

    private void initializeViews() {
        rvPerfumes = findViewById(R.id.rvPerfumes);
        progressBar = findViewById(R.id.progressBar);
        llEmptyState = findViewById(R.id.llEmptyState);
        fabAdd = findViewById(R.id.fabAdd);
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
        adapter = new PerfumeAdapter(this, this);
        rvPerfumes.setLayoutManager(new LinearLayoutManager(this));
        rvPerfumes.setAdapter(adapter);
    }

    private void loadPerfumes() {
        showLoading(true);

        // Load from SQLite database
        List<Perfume> perfumes = dbHelper.getAllPerfumes();
        
        showLoading(false);
        
        if (perfumes.isEmpty()) {
            showEmptyState(true);
        } else {
            showEmptyState(false);
            adapter.setPerfumeList(perfumes);
        }
    }

    private void showLoading(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        rvPerfumes.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    private void showEmptyState(boolean show) {
        llEmptyState.setVisibility(show ? View.VISIBLE : View.GONE);
        rvPerfumes.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onEditClick(Perfume perfume) {
        Intent intent = new Intent(this, AddEditPerfumeActivity.class);
        intent.putExtra("PERFUME_ID", perfume.getId());
        intent.putExtra("PERFUME_NAME", perfume.getName());
        intent.putExtra("PERFUME_BRAND", perfume.getBrand());
        intent.putExtra("PERFUME_PRICE", perfume.getPrice());
        intent.putExtra("PERFUME_DESCRIPTION", perfume.getDescription());
        intent.putExtra("PERFUME_IMAGE_URL", perfume.getImageUrl());
        intent.putExtra("IS_EDIT_MODE", true);
        startActivity(intent);
    }

    @Override
    public void onDeleteClick(Perfume perfume) {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.delete_perfume))
                .setMessage(getString(R.string.confirm_delete))
                .setPositiveButton(getString(R.string.yes), (dialog, which) -> deletePerfume(perfume))
                .setNegativeButton(getString(R.string.no), null)
                .show();
    }

    private void deletePerfume(Perfume perfume) {
        int rowsDeleted = dbHelper.deletePerfume(perfume.getId());
        
        if (rowsDeleted > 0) {
            Toast.makeText(this, getString(R.string.perfume_deleted), Toast.LENGTH_SHORT).show();
            loadPerfumes(); // Reload list
        } else {
            Toast.makeText(this, "Failed to delete perfume", Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateToAddPerfume() {
        Intent intent = new Intent(this, AddEditPerfumeActivity.class);
        intent.putExtra("IS_EDIT_MODE", false);
        startActivity(intent);
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
        // Reload perfumes when returning to this activity
        loadPerfumes();
    }
}
