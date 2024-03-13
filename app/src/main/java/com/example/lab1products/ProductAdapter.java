package com.example.lab1products;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONObject;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private List<Product> productList;
    private List<Product> filteredProductList; // Список продуктов после фильтрации
    private Context context;
    private OnClickListener onClickListener;

    public ProductAdapter(Context context, List<Product> productList, OnClickListener onClickListener) {
        this.context = context;
        this.productList = productList;
        this.filteredProductList = new ArrayList<>(productList); // Копируем список для фильтрации
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_product, parent, false);
        return new ViewHolder(view);
    }

    public interface OnClickListener {
        void onItemClick(Product product);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.textCategory.setText(product.getProduct_name());
        holder.textName.setText(product.getCategory());
        holder.textCalories.setText("Калорий: " + product.getCalories_per_serving());

        // Обработка нажатия на изображение "Избранное"
        holder.imageFavorite.setOnClickListener(v -> {
            // Изменяем изображение "Избранное" и обновляем UI
            if (product.isFavorite()) {
                product.setFavorite(false);
                holder.imageFavorite.setImageResource(R.drawable.ic_favorite_empty);
            } else {
                product.setFavorite(true);
                holder.imageFavorite.setImageResource(R.drawable.ic_favorite_filled);
            }

            ProductLoader.updateProduct(product);
        });

        // Устанавливаем изображение "Избранное" в соответствии с состоянием продукта
        if (product.isFavorite()) {
            holder.imageFavorite.setImageResource(R.drawable.ic_favorite_filled);
        } else {
            holder.imageFavorite.setImageResource(R.drawable.ic_favorite_empty);
        }
    }

    public void filterByCategory(String category) {
        productList.clear();
        if (category.isEmpty()) {
            // Если категория пустая, отображаем все записи
            productList.addAll(filteredProductList);
            notifyDataSetChanged();
            return;
        }

        for (Product product : filteredProductList) {
            if (product.getCategory().toLowerCase().startsWith(category.toLowerCase())) {
                productList.add(product);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        TextView textCategory, textName, textCalories;
        ImageView imageFavorite;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textCategory = itemView.findViewById(R.id.text_category);
            textName = itemView.findViewById(R.id.text_name);
            textCalories = itemView.findViewById(R.id.text_calories);
            imageFavorite = itemView.findViewById(R.id.image_favorite);

            // Установка слушателя кликов
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // Получение позиции элемента
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                // Вызов обработчика клика с передачей продукта
                onClickListener.onItemClick(productList.get(position));
            }
        }
    }
}
