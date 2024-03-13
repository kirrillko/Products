package com.example.lab1products;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class MainActivity extends AppCompatActivity  implements ProductAdapter.OnClickListener  {
    private RecyclerView recyclerView;
    private ProductAdapter adapter;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        progressBar = findViewById(R.id.progressBar);

        ProductLoader.loadAndSortProducts(productList -> {
            // Обновляем RecyclerView из основного потока
            runOnUiThread(() -> {
                recyclerView = findViewById(R.id.recycler_view);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                adapter = new ProductAdapter(MainActivity.this, productList, MainActivity.this);
                recyclerView.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);
            });
        });

        EditText editTextSearch = findViewById(R.id.editTextSearch);
        Button ButtonSearch = findViewById(R.id.ButtonSearch);
        ButtonSearch.setOnClickListener(v -> {
            String find = editTextSearch.getText().toString();
            adapter.filterByCategory(find);
        });

        // Инициализируем меню
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Справочник продуктов");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Инфлейтим меню; добавляем элементы в панель действий, если она присутствует.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.favorite_Item){
            Intent i = new Intent(this, FavoriteActivity.class);
            startActivity(i);
            return true;
        }
        return false;
    }


    @Override
    public void onItemClick(Product product) {
        Intent i = new Intent(this, InfoProductActivity.class);
        i.putExtra("Idx", product.getProduct_id());
        startActivity(i);
    }
}