package com.example.nitinsharma.loginapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.nitinsharma.ToDoItems;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ImageButton add;
    RecyclerView recyclerView;
    ToDoAdapter adapter;
    ArrayList<ItemsBean> arrayList;
    SQLiteDatabase sqLiteDatabase;
    ToDoItems toDoItems;
    LinearLayout linearLayout;
    int update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        Drawable background = getResources().getDrawable(R.drawable.bg);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(android.R.color.transparent));
        window.setNavigationBarColor(getResources().getColor(android.R.color.transparent));
        window.setBackgroundDrawable(background);

        setContentView(R.layout.activity_main);

        toDoItems = new ToDoItems(this, "items", null, 1);
        sqLiteDatabase = toDoItems.getWritableDatabase();
        sqLiteDatabase = toDoItems.getReadableDatabase();

        arrayList = new ArrayList<>();
        adapter = new ToDoAdapter(this, arrayList);
        linearLayout=findViewById(R.id.note);



       /* arrayList.add(new ItemsBean("Three Copy", "For School", "false"));
        arrayList.add(new ItemsBean("Dress", "For Daughter", "false"));*/

        add = findViewById(R.id.add);
        recyclerView = findViewById(R.id.recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter.itemClickListener(new ToDoAdapter.itemClick() {
            @Override
            public void click(int pos) {
                update = pos;

                ItemsBean data = arrayList.get(pos);
                String title = data.getTitle();
                String des = data.getDescription();

                Intent i = new Intent(MainActivity.this, AddItem.class);
                i.putExtra("title", title);
                i.putExtra("description", des);
                i.putExtra("position", update + 1);
                startActivityForResult(i, update);

            }
        });
        Boolean firstTime = getPreferences(MODE_PRIVATE).getBoolean("abc", true);
        if (firstTime) {

            // here you do what you want to do - an activity tutorial in my case
            TapTargetView.showFor(this,                 // `this` is an Activity
                    TapTarget.forView(findViewById(R.id.add), "Add Your First Note", "Tap this button to start adding notes")
                            // All options below are optional
                            .outerCircleColor(R.color.iconcolor)      // Specify a color for the outer circle
                            .outerCircleAlpha(0.96f)            // Specify the alpha amount for the outer circle
                            .targetCircleColor(R.color.iconcolor)   // Specify a color for the target circle
                            .titleTextSize(20)                  // Specify the size (in sp) of the title text
                            .titleTextColor(R.color.white)      // Specify the color of the title text
                            .descriptionTextSize(16)            // Specify the size (in sp) of the description text
                            .descriptionTextColor(R.color.white)  // Specify the color of the description text
                            // Specify a color for both the title and description text
                            .textTypeface(Typeface.SANS_SERIF)  // Specify a typeface for the text
                            .dimColor(R.color.black)            // If set, will dim behind the view with 30% opacity of the given color
                            .drawShadow(true)                   // Whether to draw a drop shadow or not
                            .cancelable(false)                  // Whether tapping outside the outer circle dismisses the view
                            .tintTarget(true)                   // Whether to tint the target view's color
                            .transparentTarget(true)           // Specify whether the target is transparent (displays the content underneath)
                            // Specify a custom drawable to draw as the target
                            .targetRadius(60),                  // Specify the target radius (in dp)
                    new TapTargetView.Listener() {          // The listener can listen for regular clicks, long clicks or cancels
                        @Override
                        public void onTargetClick(TapTargetView view) {
                            super.onTargetClick(view);      // This call is optional

                        }
                    });

            getPreferences(MODE_PRIVATE).edit().putBoolean("abc", false).apply();
        } else {
        }


        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Items;", null);


        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex("Title");
            int indexx = cursor.getColumnIndex("Description");
            int index2 = cursor.getColumnIndex("Check_Box");

            String title = cursor.getString(index);
            String description = cursor.getString(indexx);
            String checkBox = cursor.getString(index2);

            arrayList.add(new ItemsBean(title, description, checkBox));
        }
        recyclerView.setAdapter(adapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, AddItem.class);
                startActivityForResult(i, 1);

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1 && resultCode == RESULT_OK) {


            String title1 = data.getStringExtra("Title");


            Log.v("title", title1 + "");
            Log.v("titledw", "vsjdk");

            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Items where Title='" + title1 + "';", null);


            while (cursor.moveToNext()) {
                int index = cursor.getColumnIndex("Title");
                int indexx = cursor.getColumnIndex("Description");
                int index2 = cursor.getColumnIndex("Check_Box");


                String title = cursor.getString(index);
                String description = cursor.getString(indexx);
                String checkBox = cursor.getString(index2);

                arrayList.add(new ItemsBean(title, description, checkBox));
            }
            recyclerView.setAdapter(adapter);
            Toast.makeText(this, "Note Added Successfully", Toast.LENGTH_SHORT).show();
        }

        if (requestCode == update && resultCode == 101) {
            Toast.makeText(this, "Note Updated successfully", Toast.LENGTH_SHORT).show();
            String title1 = data.getStringExtra("Title");
            String des = data.getStringExtra("Description");

            ItemsBean data1 = arrayList.get(update);
            data1.setTitle(title1);
            data1.setDescription(des);

            adapter.notifyItemChanged(update);
            recyclerView.invalidate();

        }
    }
    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (arrayList.size() == 0) {
            linearLayout.setVisibility(View.VISIBLE);
        } else {
            linearLayout.setVisibility(View.GONE);
        }
    }
}
