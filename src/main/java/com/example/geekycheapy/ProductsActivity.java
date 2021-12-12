package com.example.geekycheapy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class ProductsActivity extends AppCompatActivity {
    Button btnLogout;
    GridView productsGridView;


    FirebaseUser user;
    DatabaseReference reference;
    String userID;
    Product product;
    ArrayList<Product> productArrayList;
    ProgressBar progressBar;

    public void onClickChangeActivity(Product  product) {
        Intent intent = new Intent(ProductsActivity.this, DetailP.class);
        user= FirebaseAuth.getInstance().getCurrentUser();
        userID=user.getUid();
        String userEmail=user.getEmail();

        intent.putExtra("names", product.getName());
        intent.putExtra("prices", product.getPrice());
        intent.putExtra("images", product.getImage());
        intent.putExtra("desc", product.getDesc());
        intent.putExtra("userid",userID);
        intent.putExtra("useremail",userEmail);
        startActivity(intent);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        progressBar=(ProgressBar) findViewById((R.id.progressBarProducts));
        progressBar.setVisibility(View.VISIBLE);
        btnLogout=(Button)findViewById(R.id.logoutbtn);

        productsGridView = findViewById(R.id.products);
        productArrayList = new ArrayList<>();
        reference= FirebaseDatabase.getInstance().getReference().child("Products");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                productArrayList.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Product product = snapshot.getValue(Product.class);
                    productArrayList.add(product);
                }
                AdapterP adapter = new AdapterP(ProductsActivity.this, productArrayList);
                productsGridView.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProductsActivity.this, "Fail to load data..", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ProductsActivity.this, LoginActivity.class));
            }
        });



    }
}