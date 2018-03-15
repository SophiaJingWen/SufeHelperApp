package com.example.sufehelperapp;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyActivity_History_Recieved extends Fragment implements View.OnClickListener {

    private user user;
    private Bundle bundle;
    private List<task> taskList = new ArrayList<>();

    @Nullable
    private TaskAdapter adapter;



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my_history_recieved, container, false);

        bundle = getArguments();
        user = (user) bundle.getSerializable("user_now");
        Log.d("History_Recieved",user.getMyName());

        task.updateAllTaskStatus();
        task.updateAllStatusText();

        //TODO:获得接收者为当前用户，ifShutDown = true 的所有任务
        taskList = DataSupport.where("helperName = ? and ifShutDown = ?",user.getMyName(),"1")
                .find(task.class);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.history_recieved_recycler);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),1);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TaskAdapter(taskList,user,3);
        recyclerView.setAdapter(adapter);

        return view;
    }
  
    @Override
    public void onClick(View view) {

    }

}
