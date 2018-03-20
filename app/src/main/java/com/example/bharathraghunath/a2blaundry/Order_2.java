package com.example.bharathraghunath.a2blaundry;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Order_2 extends AppCompatActivity implements NumberPicker.OnValueChangeListener {

    TextView t1,t2,t3;
    NumberPicker n1,n2,n3;
    EditText e1;
    Button b1;
    RadioGroup rg;
    RadioButton r1,r2,r3,radioButton;
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_2);
        //this is for selecting the type of services
        rg = (RadioGroup)findViewById(R.id.radioGroup5);
        t1 = (TextView)findViewById(R.id.textView10);
        t2 = (TextView)findViewById(R.id.textView11);
        t3 = (TextView)findViewById(R.id.textView13);
        b1 = (Button)findViewById(R.id.button3);
        e1 = (EditText)findViewById(R.id.editText8);
        r1= (RadioButton)findViewById(R.id.option1);
        r2= (RadioButton)findViewById(R.id.option2);
        r3= (RadioButton)findViewById(R.id.option3);

        n1 =(NumberPicker)findViewById(R.id.numberPicker5);
        n2 =(NumberPicker)findViewById(R.id.numberPicker6);
        n3 =(NumberPicker)findViewById(R.id.numberPicker7);
        n1.setMaxValue(10);
        n1.setMinValue(1);
        n2.setWrapSelectorWheel(true);
        n2.setMaxValue(20);
        n2.setMinValue(1);
        n2.setWrapSelectorWheel(false);
        n3.setMaxValue(20);
        n3.setMinValue(1);
        n3.setWrapSelectorWheel(false);
        Toolbar tbar = (Toolbar)findViewById(R.id.toolbar_2);
        setSupportActionBar(tbar);
        n1.setOnValueChangedListener(this);
        n2.setOnValueChangedListener(this);
        n3.setOnValueChangedListener(this);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String DeliveryMode = e1.getText().toString().trim();
                String tw = t1.getText().toString().trim();
                String ps = t2.getText().toString().trim();
                String dc = t3.getText().toString().trim();

                DatabaseReference usersRef = ref.child("customers");
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser() ;
                String user = currentUser.getUid();

                usersRef.child(user).child("Delivery_Mode").setValue(DeliveryMode);
                usersRef.child(user).child("Total_Weight").setValue(tw);
                usersRef.child(user).child("Quantity").setValue(ps);
                usersRef.child(user).child("Dry Clean").setValue(dc);

                Toast.makeText(getApplicationContext(),"Saved data",Toast.LENGTH_SHORT).show();

                finish();

                Intent f = new Intent(getApplicationContext(),order_3.class);
                startActivity(f);
            }
        });

    }
    public void checkButton(View v)
    {
        int radioId = rg.getCheckedRadioButtonId();

        radioButton = (RadioButton)findViewById(radioId);
        e1.setText("Your choice: " + radioButton.getText());

    }


    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i1) {

        if(numberPicker.getId()==R.id.numberPicker5){
            t1.setText(" "+ i1);
        }

        if(numberPicker.getId()==R.id.numberPicker6){
            t2.setText(" "+ i1);
        }
        if(numberPicker.getId()==R.id.numberPicker7){
            t3.setText(" "+ i1);
        }

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
