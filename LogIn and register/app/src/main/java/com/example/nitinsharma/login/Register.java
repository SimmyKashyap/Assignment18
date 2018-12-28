package com.example.nitinsharma.login;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    EditText username,password,confirmPassword,number,email;
    String name,emaill,p,cp;
    long mobileNo;
    Button signUp;
    int check;
    SQLiteDatabase database;
    RegisteredUsers registeredUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
        setContentView(R.layout.activity_register);

        username=findViewById(R.id.userName);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        confirmPassword=findViewById(R.id.confirmpassword);
        number=findViewById(R.id.mobileno);
        signUp=findViewById(R.id.signup);

        registeredUsers=new RegisteredUsers(getApplicationContext(),"Users",null,1);
        database=registeredUsers.getReadableDatabase();
        database=registeredUsers.getWritableDatabase();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
                checkData();
                if(check==2){
                    Toast.makeText(Register.this, "Sign Up Successfully", Toast.LENGTH_SHORT).show();
                    ContentValues values = new ContentValues();
                    values.put("Name", name);
                    values.put("Email", emaill);
                    values.put("Password", p);
                    values.put("Mobile",mobileNo);
                    database.insert("Users", null, values);
                    Intent i=new Intent(Register.this,LogIn.class);
                    startActivity(i);
                    finish();

                }

            }
        });



    }
    public void getData(){
        name=username.getText().toString().trim();
        emaill=email.getText().toString().trim();
        p=password.getText().toString().trim();
        cp=confirmPassword.getText().toString().trim();
        mobileNo=number.getText().toString().trim().isEmpty()?100:Long.parseLong(number.getText().toString().trim());

    }

    public void checkData(){
        if(name.isEmpty()){
            username.setError("Enter Your name!!");
            check=0;
        }else if(emaill.isEmpty()){
            email.setError("Enter Your Email!!");

        }else if (!isValidEmailAddress(emaill)) {
            email.setError("invalid email");
            check = 0;

        }
        else if(p.isEmpty()){
            password.setError("Enter the password!!");
            check=0;
        }else if(cp.isEmpty()){
            confirmPassword.setError("Re-enter the password!!");
            check=0;
        }else if(!p.equals(cp)){
            confirmPassword.setError("Password doesn't match");
            check=0;
        }
        else if(mobileNo==100){
            number.setError("Enter the Mobile No!!");
            check=0;
        }else if(mobileNo>9999999999l){
            number.setError("Enter Valid Mobile No!!");
            check=0;
        }
        else if(mobileNo<1000000000l){
            number.setError("Enter Valid Mobile No!!");
            check=0;
        }
        else{
            check=2;
        }


    }
    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(Register.this,LogIn.class);
        startActivity(i);
        finish();
    }
}
