package com.example.bharathraghunath.a2blaundry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class order_3 extends AppCompatActivity {

    private ArrayList<String> Data;
    private DatabaseReference dref;
    private FirebaseAuth mAuth;
    private FirebaseDatabase Fbase;
    private String userId;
    EditText e1,e2,e3;
    ListView LV;
    Button b4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_3);
        dref = FirebaseDatabase.getInstance().getReference().child("customers");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid().toString();
        DatabaseReference dref_c = dref.child(userId);

        e1=(EditText)findViewById(R.id.editText3);
        e2=(EditText)findViewById(R.id.editText4);
        e3=(EditText)findViewById(R.id.editText6);

        b4 = (Button)findViewById(R.id.button4);

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dref.child(userId).removeValue();


                Intent i = new Intent(getApplicationContext(),LaundryMain.class);
                startActivity(i);
            }
        });
        dref_c.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showdata(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        dref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

                String key = dataSnapshot.getKey();

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void showdata(DataSnapshot dataSnapshot) {

            e1.setText(dataSnapshot.child("Address").getValue(String.class));
            e2.setText(dataSnapshot.child("Delivery_Date").getValue(String.class));
            e3.setText(dataSnapshot.child("Time").getValue(String.class));


    }



}
