package com.example.perfume_shop.api;

import com.example.perfume_shop.models.Perfume;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    
    /**
     * GET: Search fragrances by name or brand
     * @param search The name of the fragrance or brand (min 3 characters)
     * @param limit Number of results to return (default 10, max 20)
     */
    @GET("fragrances")
    Call<List<Perfume>> searchPerfumes(
            @Query("search") String search,
            @Query("limit") int limit
    );
    
    /**
     * Default search with limit of 20 results
     */
    @GET("fragrances")
    Call<List<Perfume>> getAllPerfumes(
            @Query("search") String search
    );
}
