package com.example.newsapi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements CustomAdapter.ItemClickListener{
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    String apiUrl = "https://newsdata.io/api/1/news?apikey=pub_47351ea418dd2b3ac1f129c5183dcec83356&country=in";

//    Database db = new Database();

    ArrayList<Model> list = new ArrayList<Model>();

    CustomAdapter adapter;

    private int pageNumber = 1, perPage = 10;

    String title;
    String date;
    String imageUrl;
    String link;

    String num = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));

//        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color=\"black\">" +getString(R.string.app_name) + "</font>"));

        recyclerView = findViewById(R.id.recyclerView);
        swipeRefreshLayout = findViewById(R.id.swipeLayout);

        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(MainActivity.this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, apiUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("results");

                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject obj = jsonArray.getJSONObject(i);
                        title = obj.getString("title");
                        imageUrl = obj.getString("image_url");
                        date = obj.getString("source_id")+"  |  "+ obj.getString("pubDate");
                        link = obj.getString("link");
                        list.add(new Model(title, date, imageUrl,link, num));

//                        db.add(new Model(title, date, imageUrl, link)).addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void unused) {
////                                Toast.makeText(MainActivity2.this, "Data is Inserted", Toast.LENGTH_SHORT).show();
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Toast.makeText(MainActivity2.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        });

                    }


                    adapter = new CustomAdapter(list, MainActivity.this, MainActivity.this);
                    recyclerView.setAdapter(adapter);

                    recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));



                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this, "NO", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Something Went Wrong!!", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);



        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, apiUrl, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");

                            for (int i=0; i<jsonArray.length(); i++){
                                JSONObject obj = jsonArray.getJSONObject(i);
                                title = obj.getString("title");
                                imageUrl = obj.getString("image_url");
                                date = obj.getString("source_id")+"  |  "+ obj.getString("pubDate");
                                link = obj.getString("link");

                                if(!title.equalsIgnoreCase(list.get(i).getTitle()) ){
                                    list.add(i, new Model(title, date, imageUrl,link, num));

//                                    db.add(new Model(title, date, imageUrl, link)).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                        @Override
//                                        public void onSuccess(Void unused) {
////                                            Toast.makeText(MainActivity2.this, "Data is Inserted", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }).addOnFailureListener(new OnFailureListener() {
//                                        @Override
//                                        public void onFailure(@NonNull Exception e) {
//                                            Toast.makeText(MainActivity2.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                                        }
//                                    });

                                }

                            }


                            adapter = new CustomAdapter(list, MainActivity.this, MainActivity.this);
                            recyclerView.setAdapter(adapter);

                            adapter.notifyDataSetChanged();

                            swipeRefreshLayout.setRefreshing(false);

                            Toast.makeText(MainActivity.this, "Refreshed", Toast.LENGTH_SHORT).show();


                        } catch (JSONException e) {
                            Toast.makeText(MainActivity.this, "NO", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Something Went Wrong!!", Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });

                requestQueue.add(jsonObjectRequest);
            }
        });
    }

    @Override
    public void onItemCLick(int position) {
        Intent intent = new Intent(MainActivity.this, WebPageActivity.class);
        intent.putExtra("link", list.get(position).getLink());
        startActivity(intent);
    }

}