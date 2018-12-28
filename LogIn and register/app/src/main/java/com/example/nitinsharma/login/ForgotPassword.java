package com.example.nitinsharma.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class ForgotPassword extends AppCompatActivity {
    EditText email,mobileNo;
    Button getPassword;
    String emaill;
    long mobile;
    int check;
    SQLiteDatabase database;
    int flag=100;
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
        setContentView(R.layout.activity_forgot_password);

        email=findViewById(R.id.email);
        mobileNo=findViewById(R.id.mobileNo);

        registeredUsers = new RegisteredUsers(getApplicationContext(), "Users", null, 1);
        database = registeredUsers.getReadableDatabase();
        database = registeredUsers.getWritableDatabase();





        getPassword=findViewById(R.id.rPassword);
        getPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
                checkData();
                if(check==2){
                    Cursor cursor = database.rawQuery("SELECT * FROM Users;", null);
                    while (cursor.moveToNext()) {
                        int index = cursor.getColumnIndex("Email");
                        int index2 = cursor.getColumnIndex("Column_Id");
                        String title = cursor.getString(index);
                        int column = cursor.getInt(index2);
                        Log.v("column", "" + column);
                        if (title.equals(emaill)) {
                            flag = 101;
                            Cursor cursor1 = database.rawQuery("SELECT * FROM Users Where Column_Id=" + column + ";", null);
                            while (cursor1.moveToNext()) {
                                int index1 = cursor1.getColumnIndex("Password");
                                int name=cursor1.getColumnIndex("Name");
                                int cMobile=cursor1.getColumnIndex("Mobile");
                                String passwordd = cursor1.getString(index1);
                                String userName=cursor1.getString(name);
                                long cMobileNo=Long.parseLong(cursor1.getString(cMobile ));
                                Log.v("cursor1", "enter " + passwordd);
                                if (cMobileNo==mobile) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ForgotPassword.this);
                                    builder.setMessage("Hey "+userName+","+"Your Password is " + passwordd +".");
                                    builder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent k=new Intent(ForgotPassword.this,LogIn.class);
                                            startActivity(k);
                                            finish();

                                        }


                                    });
                                    builder.show();

                                        // here you do what you want to do - an activity tutorial in my case


                                }else{
                                    mobileNo.setError("Incorrect Mobile No!!");
                                    break;

                                }
                            }

                        }else{
                            flag = 100;
                        }

                    }
                    if (flag == 100) {
                        email.setError("User Email not exist!!");
                    }
                }


            }
        });


    }
    public void getData(){
        emaill=email.getText().toString().trim();
        mobile=mobileNo.getText().toString().trim().isEmpty()?100l:Long.parseLong(mobileNo.getText().toString().trim());


    }
    public  void checkData(){
        if(emaill.isEmpty()){
            email.setError("Enter Your email!!");
            check=0;
        }
        else if(mobile==100l){
            mobileNo.setError("Enter Mobile No!!");
            check=0;
        }
        else{
            check=2;
        }
    }
    @Override
    public void onBackPressed() {
        Intent i=new Intent(ForgotPassword.this,LogIn.class);
        startActivity(i);
        finish();
    }

}
