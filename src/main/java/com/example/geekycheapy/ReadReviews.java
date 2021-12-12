package com.example.geekycheapy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReadReviews extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_reviews);
        ListView reviewsListView= (ListView) findViewById(R.id.reviewsListView);
        Bundle bundle = getIntent().getExtras();
        Context s=this;

        List<String> reviews =new ArrayList<String>();

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Reviews");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                reviews.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Review review = snapshot.getValue(Review.class);

                    if(review.getProductName().equals(bundle.getString("productname"))){
                        reviews.add(review.getUserEmail() +": \n"+ review.getReviewText());
                    }
                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(s, android.R.layout.simple_list_item_1,reviews);
                    reviewsListView.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ReadReviews.this, "Fail to load data..", Toast.LENGTH_SHORT).show();
            }
        });

    }
}