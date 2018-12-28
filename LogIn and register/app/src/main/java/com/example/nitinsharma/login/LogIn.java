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
import android.widget.Toast;

public class LogIn extends AppCompatActivity {
    EditText email, password;
    String emaill, p;
    Button forgotPassword, logIn, register;
    RegisteredUsers registeredUsers;
    SQLiteDatabase database;
    int flag = 100;
    int check;
    Boolean firstTime;
    String tutorialKey = "SOME_KEY";

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

        setContentView(R.layout.activity_log_in);
        Intent i=getIntent();
        if(i.hasExtra("flag")){
            Log.v("logout","logout");
            getPreferences(MODE_PRIVATE).edit().putBoolean(tutorialKey, true).apply();

        }

        firstTime = getPreferences(MODE_PRIVATE).getBoolean(tutorialKey, true);


        if (firstTime) {
            Log.v("hit","true");
            // here you do what you want to do - an activity tutorial in my case
        } else {
            Log.v("hit","false");
            startActivity(new Intent(LogIn.this, MainActivity.class));
          finish();
        }

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        forgotPassword = findViewById(R.id.forgotpassword);
        logIn = findViewById(R.id.login);
        register = findViewById(R.id.register);

        registeredUsers = new RegisteredUsers(getApplicationContext(), "Users", null, 1);
        database = registeredUsers.getReadableDatabase();
        database = registeredUsers.getWritableDatabase();

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LogIn.this,ForgotPassword.class);
                startActivity(i);
                finish();

            }
        });
        logIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getData();
                checkData();
                if (check == 2) {

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
                                String passwordd = cursor1.getString(index1);
                                String userName=cursor1.getString(name);
                                Log.v("cursor1", "enter " + passwordd);
                                if (passwordd.equals(p)) {


                                    if (firstTime) {
                                        // here you do what you want to do - an activity tutorial in my case
                                        Intent i=new Intent(LogIn.this,MainActivity.class);
                                        i.putExtra("name",userName);
                                        i.putExtra("email",title);
                                        i.putExtra("password",passwordd);
                                        startActivity(i);
                                        Toast.makeText(LogIn.this, "Successfully Logged In", Toast.LENGTH_SHORT).show();
                                        getPreferences(MODE_PRIVATE).edit().putBoolean(tutorialKey, false).apply();
                                        finish();
                                    }
                                }else{
                                        password.setError("Incorrect password!!");
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
        register.setOnClickListener(new View.OnClickListener()

            {
                @Override
                public void onClick (View v){
                Intent i = new Intent(LogIn.this, Register.class);
                startActivity(i);
                finish();
            }
            });
        }

    public void getData() {
        emaill = email.getText().toString().trim();
        p = password.getText().toString().trim();
    }

    public void checkData() {
        if (emaill.isEmpty()) {
            email.setError("Enter the email!!");
            check = 0;
        } else if (p.isEmpty()) {
            password.setError("Enter the password!!");
            check = 0;
        } else {
            check = 2;
        }


    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LogIn.this);

        builder.setMessage("Are You sure to exit this app?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                LogIn.super.onBackPressed();
            }


        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
}
