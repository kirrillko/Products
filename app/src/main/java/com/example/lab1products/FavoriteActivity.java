package com.example.lab1products;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

public class FavoriteActivity extends AppCompatActivity  implements ProductFavoriteAdapter.OnClickListener  {
    private RecyclerView recyclerView;
    private ProductFavoriteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_favorite);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ProductLoader.loadFavorites(productList -> {
            // Обновляем RecyclerView из основного потока
            runOnUiThread(() -> {
                recyclerView = findViewById(R.id.recyclerViewFavorite);
                recyclerView.setLayoutManager(new LinearLayoutManager(FavoriteActivity.this));
                adapter = new ProductFavoriteAdapter(FavoriteActivity.this, productList, FavoriteActivity.this);
                recyclerView.setAdapter(adapter);
            });
        });

        Toolbar toolbar = findViewById(R.id.toolbar_favorite);
        toolbar.setTitle("Избранное");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        });
    }

    @Override
    public void onItemClick(Product product) {
        Intent i = new Intent(this, InfoProductActivity.class);
        i.putExtra("Idx", product.getProduct_id());
        startActivity(i);
    }
}