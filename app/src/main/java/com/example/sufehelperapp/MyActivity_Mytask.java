package com.example.sufehelperapp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;
import java.util.List;

import com.example.sufehelperapp.fragmentAdapter;
import com.example.sufehelperapp.viewAdapter;

import org.litepal.crud.DataSupport;

public class MyActivity_Mytask extends AppCompatActivity {

    private user user;
    private Bundle bundle;

    private TabLayout tab_title;
    private ViewPager vp_pager;

    private List<String> list_title;    //tab名称列表
    private List<View> listViews;
    private List<Fragment> list_fragment;

    private View currenttaskView;  //定义当前任务界面
    private View historicaltaskView;  //定义历史任务界面

    private viewAdapter vAdapter; //定义以view为切换的Adapter
    private fragmentAdapter fAdapter;  //定义以fragment为切换的Adapter

    private MyActivity_mytask_currenttask cFragment;
    private MyActivity_mytask_historicaltask hFragment;
    private ImageButton imgbtn1;

    private int[] tabImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_mytask);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
        }

        //接受user
        user = (user) getIntent().getSerializableExtra("user_now");
        Log.d("Mytask",user.getMyName());

        //bundle用于传当前user
        bundle = new Bundle();
        bundle.putSerializable("user_now",user);//将nameinfo填充入句柄


        Button button1 = (Button) findViewById(R.id.title_back);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyActivity_Mytask.this, My_HomeActivity.class);
                intent.putExtra("user_now", user);
                startActivity(intent);
            }
        });
        Button button2 = (Button) findViewById(R.id.title_edit);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyActivity_Mytask.this, MyActivity_Historical_Task.class);
                intent.putExtra("user_now", user);
                startActivity(intent);
            }
        });
        BottomNavigationView bottomNavigationItemView = (BottomNavigationView) findViewById(R.id.btn_navigation);
        bottomNavigationItemView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_task:
                        Intent intent1 = new Intent(MyActivity_Mytask.this,MainActivity.class);
                        intent1.putExtra("user_now", user);
                        break;
                    case R.id.item_explore:
                        Intent intent2 = new Intent(MyActivity_Mytask.this, ExploreActivity.class);
                        intent2.putExtra("user_now", user);
                        startActivity(intent2);
                        break;
                    case R.id.item_my:
                        Intent intent3 = new Intent (MyActivity_Mytask.this, My_HomeActivity.class);
                        intent3.putExtra("user_now", user);
                        break;
                }
                return true;
            }
        });
        /**Button button2 = (Button) findViewById(R.id.launcher_image);
         button2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        Intent intent = new Intent(MyActivity_Mytask.this, MyActivity_mytask_personalhome.class);
        startActivity(intent);
        }
        });*/
        /**imgbtn1 = (ImageButton) findViewById(R.id.launcher_image);
         imgbtn1.setOnClickListener(new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
        Intent intent = new Intent(MyActivity_Mytask.this, MyActivity_mytask_personalhome.class);
        startActivity(intent);
        }
        });*/

        initControls();

        //viewChange();
        fragmentChange();

    }
    /**
     * 初始化控件
     */
    private void initControls() {
        tab_title = (TabLayout) findViewById(R.id.tab_title);
        vp_pager = (ViewPager) findViewById(R.id.vp_pager);

        //为tabLayout上的图标赋值
        tabImg = new int[]{R.drawable.ic_explore,R.drawable.ic_explore};
    }
    /**
     * 采用在viewpager中切换view的方式，并添加了icon的功能
     */


    private void fragmentChange() {
        list_fragment = new ArrayList<>();

        cFragment = new MyActivity_mytask_currenttask();
        cFragment.setArguments(bundle); //传user入fragments
        hFragment = new MyActivity_mytask_historicaltask();
        hFragment.setArguments(bundle); //传user入fragments

        list_fragment.add(cFragment);
        list_fragment.add(hFragment);

        list_title = new ArrayList<>();
        list_title.add("接受的任务");
        list_title.add("发布的任务");

        fAdapter = new fragmentAdapter(getSupportFragmentManager(),list_fragment,list_title);
        vp_pager.setAdapter(fAdapter);
        tab_title.setupWithViewPager(vp_pager);
    }
}
