package com.example.sufehelperapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.List;

public class MyActivity_Edit_NickName extends AppCompatActivity {

    private user user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_edit_nick_name);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
        }

        //接受user
        user = (user) getIntent().getSerializableExtra("user_now");
        Log.d("MyActivity_Setup_Edit",user.getMyName());


        Button button1 = (Button) findViewById(R.id.title_back);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyActivity_Edit_NickName.this, MyActivity_Setup_Edit.class);
                intent.putExtra("user_now", user);
                startActivity(intent);
            }
        });

        final TextView nameView = (TextView) findViewById(R.id.edit_text_changeNickname);
        Button button2 = (Button) findViewById(R.id.button_conserve_nickname);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newName = nameView.getText().toString();

                List<user> userWithName = DataSupport.where("myName = ?", newName).find(user.class);
                if (!userWithName.isEmpty()) {
                    Toast.makeText(MyActivity_Edit_NickName.this, "该用户名已被注册！", Toast.LENGTH_SHORT).show();
                } else {

                    //TODO: 设置当前用户的myName为newName
                    user.setMyName(newName);
                    user.updateAll("phonenumber = ?", user.getPhonenumber());

                    AlertDialog.Builder dialog = new AlertDialog.Builder(MyActivity_Edit_NickName.this);
                    dialog.setTitle("提示");
                    dialog.setMessage("昵称修改成功！");
                    //dialog.setCancelable(false);
                    dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            Intent intent = new Intent(MyActivity_Edit_NickName.this, MyActivity_Setup_Edit.class);
                            intent.putExtra("user_now", user);
                            startActivity(intent);
                        }
                    });
                    dialog.show();

                }
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(MyActivity_Edit_NickName.this, MyActivity_Setup_Edit.class);
        intent.putExtra("user_now",user);
        startActivity(intent);
        finish();
    }
}
