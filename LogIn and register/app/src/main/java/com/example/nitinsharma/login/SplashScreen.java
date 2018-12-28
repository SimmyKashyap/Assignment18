package com.example.nitinsharma.login;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        Drawable background = getResources().getDrawable(R.drawable.bgg);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(android.R.color.transparent));
        window.setNavigationBarColor(getResources().getColor(android.R.color.transparent));
        window.setBackgroundDrawable(background);

        setContentView(R.layout.activity_splash_screen);
        int secondsDelayed = 3;
        new Handler().postDelayed(new Runnable() {
            public void run() {
              /*  String tutorialKey = "SOME_KEY";
                Boolean firstTime = getPreferences(MODE_PRIVATE).getBoolean(tutorialKey, true);
                if (firstTime) {
                  // here you do what you want to do - an activity tutorial in my case
                    getPreferences(MODE_PRIVATE).edit().putBoolean(tutorialKey, false).apply();
                }else{
                startActivity(new Intent(SplashScreen.this,MainActivity.class));*/
                Intent i=new Intent(SplashScreen.this,LogIn.class);
                startActivity(i);
                finish();
            }
        }, secondsDelayed * 1000);
    }
}
