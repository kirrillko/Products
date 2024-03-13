package com.example.lab1products;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.Objects;

public class InfoProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_info_product);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.info), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Bundle extras = getIntent().getExtras();
        assert extras != null;
        int idx = extras.getInt("Idx");
        if (idx != -1) {
            ProductLoader.loadProduct(idx, product -> {
                // Обновляем RecyclerView из основного потока
                runOnUiThread(() -> {
                    // Отображаем данные о продукте в соответствующих TextView с подписями
                    TextView textProductName = findViewById(R.id.text_product_name);
                    textProductName.setText(product.getProduct_name());

                    TextView textCalories = findViewById(R.id.text_calories);
                    textCalories.setText(String.valueOf(product.getCalories_per_serving()));

                    NutritionalValue nv = product.getNutritional_value();
                    TextView textNutritionalValue = findViewById(R.id.text_nutritional_value);
                    textNutritionalValue.setText("" + nv.getFat() + ", " + nv.getProtein() + ",  " + nv.getCarbohydrates());

                    TextView textIngredients = findViewById(R.id.text_ingredients);
                    textIngredients.setText(product.getIngredients());

                    TextView textHarmfulInformation = findViewById(R.id.text_harmful_information);
                    textHarmfulInformation.setText(product.getHarmful_information());

                    TextView textContraindications = findViewById(R.id.text_contraindications);
                    textContraindications.setText(product.getContraindications());

                    TextView textCategory = findViewById(R.id.text_category);
                    textCategory.setText(product.getCategory());
                });
            });
        }

        Toolbar toolbar = findViewById(R.id.toolbar_info);
        toolbar.setTitle("Продукт");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
            //startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        });
    }
}