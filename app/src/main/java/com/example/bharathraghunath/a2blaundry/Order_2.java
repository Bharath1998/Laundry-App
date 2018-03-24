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

    TextView t1,t2,t3,t28;
    NumberPicker n1,n2,n3;
    EditText e1;
    Button b1;
    int q1,q2,q3;

    RadioGroup rg;
    int flag,flag2;

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
        t28 = (TextView)findViewById(R.id.textView28);
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
                int t = q1+q2+q3+flag;

                String total =Integer.toString(t);

                DatabaseReference usersRef = ref.child("customers");
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser() ;
                String user = currentUser.getUid();

                usersRef.child(user).child("Delivery_Mode").setValue(DeliveryMode);
                usersRef.child(user).child("Total_Weight").setValue(tw);
                usersRef.child(user).child("Quantity").setValue(ps);
                usersRef.child(user).child("Dry Clean").setValue(dc);
                usersRef.child(user).child("Total Price").setValue(total);


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
        switch (radioId){
            case R.id.option1:
                t28.setText("Extra Cost : " + 0);
                flag=0;
                break;
            case R.id.option2:
                t28.setText("Extra Cost : " + 20);
                flag=20;
                break;
            case R.id.option3:
                t28.setText("Extra Cost : " + 40);
                flag=40;
                break;
        }
        e1.setText("" + radioButton.getText());

    }


    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
            flag2=0;
        if(numberPicker.getId()==R.id.numberPicker5){

            flag2 = flag2 + i1*50;
            t1.setText(i1+"x "+50+"= Rs"+(i1*50));
            q1=flag2;

        }

        if(numberPicker.getId()==R.id.numberPicker6){
            flag2 = flag2+  i1*15;
            t2.setText(i1+"x "+15+"=Rs"+(i1*15));
            q2=flag2;

        }
        if(numberPicker.getId()==R.id.numberPicker7){

            flag2 = flag2 + i1*40;
            t3.setText(i1+"x "+40+"=Rs"+(i1*40));
            q3=flag2;


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
