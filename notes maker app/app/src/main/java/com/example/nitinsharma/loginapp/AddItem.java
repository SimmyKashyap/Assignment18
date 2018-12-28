package com.example.nitinsharma.loginapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.nitinsharma.ToDoItems;

public class AddItem extends AppCompatActivity {
    EditText title,description;
    Button done;
    String titleContent,descriptionContent;
    ImageButton back;
    int check;
    ToDoItems toDoItems;
    SQLiteDatabase sqLiteDatabase;
    int position;
    boolean update;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        Drawable background = getResources().getDrawable(R.drawable.bg);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(android.R.color.transparent));
        window.setNavigationBarColor(getResources().getColor(android.R.color.transparent));
        window.setBackgroundDrawable(background);

        setContentView(R.layout.activity_add_item);


        toDoItems=new ToDoItems(this,"Items",null,1);
        sqLiteDatabase=toDoItems.getWritableDatabase();
        sqLiteDatabase=toDoItems.getReadableDatabase();

        title=findViewById(R.id.title);
        description=findViewById(R.id.des);



        done=findViewById(R.id.done);
        back=findViewById(R.id.back);
        Intent i = getIntent();
        if (i.hasExtra("title") && i.hasExtra("description")) {
            Log.v("intent", "hit");
            title.setText(i.getStringExtra("title").trim());
            description.setText(i.getStringExtra("description").trim());

            position = i.getIntExtra("position", 0);
            update = true;

        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddItem.super.onBackPressed();
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
                checkData();
                if(check==2){
                    if (update) {
                        ContentValues values = new ContentValues();
                        values.put("Title", titleContent);
                        values.put("Description", descriptionContent);
                        sqLiteDatabase.update("Items", values, "Column_Id=" + position, null);
                        Log.v("update", "" + position);

                    }
                    else{
                    ContentValues values = new ContentValues();
                    values.put("Title", titleContent);
                    values.put("Description", descriptionContent);
                    values.put("Check_Box","false");
                    sqLiteDatabase.insert("Items", null, values);

                }
                if(update){
                    Intent i=new Intent();
                    i.putExtra("Title",titleContent);
                    i.putExtra("Description",descriptionContent);
                    setResult(101,i);
                    finish();

                }
                else{
                    Intent i=new Intent();
                    i.putExtra("Title",titleContent);
                    setResult(RESULT_OK,i);
                    finish();

                }



                }

            }
        });

    }
    public void getData(){
        titleContent=title.getText().toString().trim();
        descriptionContent=description.getText().toString().trim();


    }

    public void checkData(){
        if(titleContent.isEmpty()){
            title.setError("Pls Enter Title!!");
            check=0;
        }
        else if(descriptionContent.isEmpty()){
            description.setError("Pls Enter Description!!");
            check=0;
        }
        else{
            check=2;
        }

    }
}
