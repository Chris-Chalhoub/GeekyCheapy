package com.example.geekycheapy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class WriteReview extends AppCompatActivity {
    EditText reviewTxt;
    Button addReviewBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        setContentView(R.layout.activity_write_review);

        reviewTxt=(EditText)findViewById(R.id.editTextTextMultiLine);
        addReviewBtn=(Button) findViewById(R.id.btnAddReview);

        addReviewBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                DatabaseReference databaseReviews = FirebaseDatabase.getInstance().getReference("Reviews");
                String id = databaseReviews.push().getKey();

                Review review=new Review(reviewTxt.getText().toString().trim(),bundle.getString("userid"),bundle.getString("productname"),bundle.getString("useremail"));

                databaseReviews.child(id).setValue(review).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(WriteReview.this,"You reviewed this item successfully!", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(WriteReview.this,"Review not added please try again!", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });

    }
}