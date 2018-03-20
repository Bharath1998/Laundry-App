package com.example.bharathraghunath.a2blaundry;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class signup extends AppCompatActivity implements View.OnClickListener {

    EditText uname,passwd;
    TextView logintx;
    FirebaseAuth mAuth;
    Button signup;
    ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);
        uname  = (EditText)findViewById(R.id.editText);
        passwd  = (EditText)findViewById(R.id.editText2);
        pb=(ProgressBar)findViewById(R.id.progressBar2);
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.signupb).setOnClickListener(this);
        findViewById(R.id.logintx).setOnClickListener(this);

    }

    private  void  registerUser(){
        String email = uname.getText().toString().trim();
        String pass = passwd.getText().toString().trim();

        if(email.isEmpty()){
            uname.setError("Email is required..");
            uname.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            uname.setError("Please enter valid email");
            uname.requestFocus();
            return;
        }

        if(pass.isEmpty()){
            passwd.setError("passwd is reqd");
            passwd.requestFocus();
            return;
        }
        if(pass.length()<6){
            passwd.setError("Min length of pass 6");
            passwd.requestFocus();
            return;
        }
        pb.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                pb.setVisibility(View.GONE);
                if (task.isSuccessful()){
                    finish();
                    Intent intent = new Intent(signup.this,LaundryMain.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }
                else{
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(getApplicationContext(),"User already reggistered",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case (R.id.signupb):
                registerUser();
                break;
            case R.id.logintx:
                finish();
                startActivity(new Intent(this,MainActivity.class));
                break;

        }
    }
}
