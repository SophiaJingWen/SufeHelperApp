package com.example.sufehelperapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import org.litepal.crud.DataSupport;

import java.util.List;

public class Task_HomeActivity extends AppCompatActivity {

    private user user;
    /*
    private static final String url = "jdbc:mysql://101.94.5.73:3306/sufehelper";
    private static final String user ="test123";
    private static final String pass = "1234";*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //接收user
        user = (user) getIntent().getSerializableExtra("user_now");
        Log.d("Task_HomeActivity:",user.getMyName());

        BottomNavigationView bottomNavigationItemView = (BottomNavigationView) findViewById(R.id.btn_navigation);
        bottomNavigationItemView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_task:
                        break;
                    case R.id.item_explore:
                        Intent intent2 = new Intent(Task_HomeActivity.this, ExploreActivity.class);
                        intent2.putExtra("user_now", user);
                        startActivity(intent2);
                        break;
                    case R.id.item_my:
                        Intent intent3 = new Intent(Task_HomeActivity.this, My_HomeActivity.class);
                        intent3.putExtra("user_now", user);
                        startActivity(intent3);
                        break;
                }
                return true;
            }
        });

        /*

        List<user> userList = DataSupport.findAll(user.class);
        Log.d("task home","after switching page");
        for(user user:userList){
            Log.d("name",user.getMyName());
            Log.d("msg list size",String.valueOf(user.getMsgTaskList().size()));
        }*/


        Button db_test = (Button) findViewById(R.id.button_db_test);
        db_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Task_HomeActivity.this, DBTESTActivity.class);
                intent.putExtra("user_now", user);
                startActivity(intent);
            }
        });

        Button b1 = (Button) findViewById(R.id.btn_errand);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Task_HomeActivity.this,Task_ErrandActivity.class);
                intent1.putExtra("user_now", user);
                startActivity(intent1);
            }
        });
        Button b2 = (Button) findViewById(R.id.btn_skill);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(Task_HomeActivity.this, Task_SkillActivity.class);
                intent2.putExtra("user_now", user);
                startActivity(intent2);
            }
        });
        Button b3 = (Button) findViewById(R.id.btn_counsel);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(Task_HomeActivity.this, Task_CounselActivity.class);
                intent3.putExtra("user_now", user);
                startActivity(intent3);
            }
        });
        Button b4 = (Button) findViewById(R.id.launch_button);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(Task_HomeActivity.this, Task_LaunchActivity.class);
                intent4.putExtra("user_now", user);
                startActivity(intent4);


            }
        });

    }


    @Override
    public void onBackPressed(){

    }



}
