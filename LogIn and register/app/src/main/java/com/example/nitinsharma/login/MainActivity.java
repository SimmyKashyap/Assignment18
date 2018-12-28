package com.example.nitinsharma.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    TextView name,password;
    TextView navName,navEmail;
    ImageButton back,navButton;
    LinearLayout logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        Drawable background = getResources().getDrawable(R.drawable.mainbgg);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(android.R.color.transparent));
        window.setNavigationBarColor(getResources().getColor(android.R.color.transparent));
        window.setBackgroundDrawable(background);

        setContentView(R.layout.activity_main);

        Intent i=getIntent();

        if(i.hasExtra("name")&&i.hasExtra("password")&&i.hasExtra("email")){
            String name=i.getStringExtra("name").toString().trim();
            String email=i.getStringExtra("email").toString().trim();
            String password=i.getStringExtra("password").toString().trim();
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("name",name);
            editor.putString("email",email);
            editor.putString("password",password);
            editor.apply();
        }

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String sName = preferences.getString("name", "");
        String sPassword = preferences.getString("password", "");
        String sEmail = preferences.getString("email", "");


        navButton = findViewById(R.id.navButton);
        name=findViewById(R.id.name);
        password=findViewById(R.id.password);
        navName=findViewById(R.id.userName);
        navEmail=findViewById(R.id.userEmail);
        logout=findViewById(R.id.logOut);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Are You sure to logout?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent k=new Intent(MainActivity.this,LogIn.class);
                        k.putExtra("flag","true");
                        startActivity(k);
                        finish();
                    }


                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
            }
        });


        drawerLayout = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.app_name,R.string.bottom_sheet_behavior);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        name.setText("Welcome "+sName +"!!");
        navName.setText(sName);
        navEmail.setText(sEmail);
        password.setText("Your Password is "+sPassword+".");



        navButton = findViewById(R.id.navButton);
        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("bfj", "fjg");
                drawerLayout.openDrawer(Gravity.START
                );
            }
        });
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setMessage("Are You sure to exit this app?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               finish();
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
