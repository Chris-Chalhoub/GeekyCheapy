package com.example.geekycheapy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DetailP extends AppCompatActivity {

    TextView txtName, txtPrice, txtDesc;
    ImageView imgPhoto;
    Button btnBuy;
    Button btnReview;
    Button btnReadReviews;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_p);

        txtName = findViewById(R.id.txtName);
        txtPrice = findViewById(R.id.txtPrice);
        txtDesc = findViewById(R.id.txtDesc);
        imgPhoto = findViewById(R.id.imgPhoto);
        btnBuy=(Button) findViewById(R.id.buy);
        btnReview=(Button) findViewById(R.id.review);
        btnReadReviews=(Button) findViewById(R.id.readReviews);

        Bundle bundle = getIntent().getExtras();
        btnReview.setEnabled(false);
        txtName.setText(bundle.getString("names"));
        txtPrice.setText(bundle.getString("prices"));
        txtDesc.setText(bundle.getString("desc"));

        String userID= bundle.getString("userid");
        String productName=bundle.getString("names");

        String mDrawableName = bundle.getString("images");
        int resID = getResources().getIdentifier(mDrawableName , "drawable", getPackageName());
        imgPhoto.setImageResource(resID);

        ArrayList<Order> orders=new ArrayList<>();

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Orders");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                orders.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Order order = snapshot.getValue(Order.class);
                    orders.add(order);
                    String retrievedProductName=order.getProductName();
                    String retrievedUserID=order.getUserID();
                    if(productName.equals(retrievedProductName) && userID.equals(retrievedUserID)){
                        btnReview.setEnabled(true);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DetailP.this, "Fail to load data..", Toast.LENGTH_SHORT).show();
            }
        });
        String retrievedProductName="";
        String retrievedUserID="";

        for(int i=0; i<orders.size(); i++) {
            Order order = orders.get(i);
            retrievedProductName=order.getProductName();
            retrievedUserID=order.getUserID();
            if(productName.equals(retrievedProductName) && userID.equals(retrievedUserID)){
                btnReview.setEnabled(true);
                break;
            }
        }
        btnReview.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(DetailP.this, WriteReview.class);
                intent.putExtra("productname", productName);
                intent.putExtra("userid", userID);
                intent.putExtra("useremail", bundle.getString("useremail"));
                startActivity(intent);

            }
        });
        btnReadReviews.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(DetailP.this, ReadReviews.class);
                intent.putExtra("productname", productName);
                intent.putExtra("userid", userID);
                intent.putExtra("useremail", bundle.getString("useremail"));
                startActivity(intent);

            }
        });
        btnBuy.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                DatabaseReference databaseOrders = FirebaseDatabase.getInstance().getReference("Orders");
                String id = databaseOrders.push().getKey();

                Order order = new Order(userID, productName);

                databaseOrders.child(id).setValue(order).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(DetailP.this,"You bought this item successfully!", Toast.LENGTH_LONG).show();
                            btnReview.setEnabled(true);
                        }else{
                            Toast.makeText(DetailP.this,"Item not bought please try again!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}
