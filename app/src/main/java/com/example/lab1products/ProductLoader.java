package com.example.lab1products;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProductLoader {
    private static final String BASE_URL = "https://iubsykixbvukrvsqsvgj.supabase.co/rest/v1/product";
    private static final String API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Iml1YnN5a2l4YnZ1a3J2c3FzdmdqIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTAwMDk0MzIsImV4cCI6MjAyNTU4NTQzMn0.4IZztmOGfNZerpvc_8HaWu1sFpIw3IZ2abadCQjbq94";

    public interface OnProductsLoadedListener {
        void onProductsLoaded(List<Product> productList);
    }

    public interface OnProductLoadedListener {
        void onProductLoaded(Product product);
    }

    public static void loadProducts(OnProductsLoadedListener listener) {
        new Thread(() -> {
            try {
                URL url = new URL(BASE_URL);

                // Открываем соединение
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("apikey", API_KEY);
                connection.setRequestProperty("Authorization", "Bearer " + API_KEY);

                // Читаем ответ
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                Gson gson = new Gson();
                Type productListType = new TypeToken<List<Product>>(){}.getType();
                List<Product> productList = gson.fromJson(response.toString(), productListType);

                // Закрываем соединение
                connection.disconnect();

                // Сообщаем слушателю о загруженных продуктах
                if (listener != null) {
                    listener.onProductsLoaded(productList);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void loadFavorites
            (OnProductsLoadedListener listener) {
        new Thread(() -> {
            try {
                URL url = new URL(BASE_URL + "?favorite=is.true");

                // Открываем соединение
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("apikey", API_KEY);
                connection.setRequestProperty("Authorization", "Bearer " + API_KEY);

                // Читаем ответ
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                Gson gson = new Gson();
                Type productListType = new TypeToken<List<Product>>(){}.getType();
                List<Product> productList = gson.fromJson(response.toString(), productListType);

                // Закрываем соединение
                connection.disconnect();

                // Сообщаем слушателю о загруженных продуктах
                if (listener != null) {
                    listener.onProductsLoaded(productList);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void loadAndSortProducts(OnProductsLoadedListener listener) {
        loadProducts(productList -> {
            // Сортировка списка по product_id
            productList.sort(Comparator.comparingInt(Product::getProduct_id));

            // Сообщаем слушателю о загруженных и отсортированных продуктах
            if (listener != null) {
                listener.onProductsLoaded(productList);
            }
        });
    }

    public static void updateProduct(Product product) {
        new Thread(() -> {
            try {
                URL url = new URL(BASE_URL + "?product_id=eq." + product.getProduct_id());

                // Открываем соединение
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("PATCH");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("apikey", API_KEY);
                connection.setRequestProperty("Authorization", "Bearer " + API_KEY);
                connection.setDoOutput(true);

                // Параметры для обновления
                JSONObject productObject = new JSONObject();
                productObject.put("favorite", product.isFavorite());

                // Отправляем параметры
                OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
                out.write(productObject.toString());
                out.close();

                // Получаем ответ
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    //Log.d("Responce", String.valueOf(responseCode));
                } else {
                    //Log.d("Responce", String.valueOf(responseCode));
                }

                // Закрываем соединение
                connection.disconnect();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void loadProduct(int id, OnProductLoadedListener listener) {
        new Thread(() -> {
            try {
                URL url = new URL(BASE_URL + "?product_id=eq." + id);

                // Открываем соединение
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("apikey", API_KEY);
                connection.setRequestProperty("Authorization", "Bearer " + API_KEY);

                // Читаем ответ
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                Gson gson = new Gson();
                List<Product> productList = Arrays.asList(gson.fromJson(response.toString(), Product[].class));

                // Закрываем соединение
                connection.disconnect();

                // Сообщаем слушателю о загруженных продуктах
                if (listener != null) {
                    listener.onProductLoaded(productList.get(0));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}