package com.example.perfume_shop.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.perfume_shop.R;
import com.example.perfume_shop.models.Perfume;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PerfumeAdapter extends RecyclerView.Adapter<PerfumeAdapter.PerfumeViewHolder> {

    private Context context;
    private List<Perfume> perfumeList;
    private OnPerfumeClickListener listener;

    public interface OnPerfumeClickListener {
        void onEditClick(Perfume perfume);
        void onDeleteClick(Perfume perfume);
    }

    public PerfumeAdapter(Context context, OnPerfumeClickListener listener) {
        this.context = context;
        this.perfumeList = new ArrayList<>();
        this.listener = listener;
    }

    public void setPerfumeList(List<Perfume> perfumeList) {
        this.perfumeList = perfumeList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PerfumeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_perfume, parent, false);
        return new PerfumeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PerfumeViewHolder holder, int position) {
        Perfume perfume = perfumeList.get(position);
        holder.bind(perfume);
    }

    @Override
    public int getItemCount() {
        return perfumeList.size();
    }

    class PerfumeViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivPerfume;
        private TextView tvPerfumeName, tvBrand, tvPrice;
        private MaterialButton btnEdit, btnDelete;

        public PerfumeViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPerfume = itemView.findViewById(R.id.ivPerfume);
            tvPerfumeName = itemView.findViewById(R.id.tvPerfumeName);
            tvBrand = itemView.findViewById(R.id.tvBrand);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }

        public void bind(Perfume perfume) {
            tvPerfumeName.setText(perfume.getName());
            tvBrand.setText(perfume.getBrand());
            tvPrice.setText(String.format(Locale.US, "$%.2f", perfume.getPrice()));

            // Load image using Glide
            Glide.with(context)
                    .load(perfume.getImageUrl())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(ivPerfume);

            // Set click listeners
            btnEdit.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onEditClick(perfume);
                }
            });

            btnDelete.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onDeleteClick(perfume);
                }
            });
        }
    }
}
