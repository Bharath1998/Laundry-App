package com.example.bharathraghunath.a2blaundry;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.database.DatabaseUtils;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Order_1 extends AppCompatActivity {

    EditText address;
    TextView date,time;
    Calendar DDate;
    int year,day,month;
    int hour,min;
    Button next;
    DatabaseReference mDatabase;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_1);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference();



        address = (EditText)findViewById(R.id.editText5);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Toolbar tbar = (Toolbar)findViewById(R.id.toolbar_1);
        setSupportActionBar(tbar);
        next=(Button)findViewById(R.id.button2);
        date = (TextView)findViewById(R.id.ddate);
        time = (TextView)findViewById(R.id.textView7);
        DDate = Calendar.getInstance();
        day = DDate.get(Calendar.DAY_OF_MONTH);
        month = DDate.get(Calendar.MONTH);
        year = DDate.get(Calendar.YEAR);
        month= month+1;
        date.setText(day+"/"+month+"/"+year);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog DPD = new DatePickerDialog(Order_1.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                         i1 = i1+1;
                         date.setText(i2+"/"+i1+"/"+i);
                    }
                },year,month,day);
                DPD.show();
            }
        });


        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                hour= c.get(Calendar.HOUR_OF_DAY);
                min = c.get(Calendar.MINUTE);
                TimePickerDialog TPD = new TimePickerDialog(Order_1.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                            time.setText(i+" : "+i1);
                    }
                },hour,min,false);
                TPD.show();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String add = address.getText().toString().trim();
                String dd = date.getText().toString().trim();
                String t = time.getText().toString().trim();

                DatabaseReference usersRef = ref.child("customers");
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser() ;
                String user = currentUser.getUid();

                usersRef.child(user).child("Address").setValue(add);
                usersRef.child(user).child("Delivery_Date").setValue(dd);
                usersRef.child(user).child("Time").setValue(t);
                Toast.makeText(getApplicationContext(),"Saved data",Toast.LENGTH_SHORT).show();

                finish();
                //made change here
                Intent i  = new Intent(Order_1.this,Order_2.class);
                startActivity(i);
            }
        });

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menulogout:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this,MainActivity.class));

        }
        return true;
    }
}
