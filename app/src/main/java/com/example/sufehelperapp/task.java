package com.example.sufehelperapp;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.SystemClock;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.sql.Array;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import com.example.sufehelperapp.TimeUtils;


public class task extends DataSupport implements Serializable{

    //任务状态
    private boolean isValid;    //TODO: 合并为ifAccepted（任务发布页、筛选页）。
    private boolean ifAccepted;   //任务是否已被接受
    private boolean ifDefault;  //TODO: 是否违约（我的违约）。

    //任务进度
    private int progress;     //任务进度(1-5)

    //任务时间
    private String launchtime; //任务发布时间（发布页） 。
    private String finishtime; //TODO: 任务结束时间（我的任务）
    private String ddlDate;   //任务截止日期（发布页）
    private String ddlTime;   //任务截止时刻（发布页）
    private String ddl;  //任务截至时间（发布页）

    //private user invited //任务邀请人
    //private boolean invitationAccepted

    //任务相关人（发布页/接收页）
    private user launcher; //任务发起人（发布页）
    private String launcherName;
    private int launcherImageId;
    private String launcherPhoneNumber;
    private user helper; //任务接收人（接收页）
    private String helperName;

    //任务基本信息（发布页）
    private String taskType; //任务种类
    private String subtaskType; //任务子种类
    private double payment;  //任务报酬
    private String area;     //任务校区
    private String location;   //任务位置
    private String description;  //任务描述

    //任务评分
    private float score;    //任务评分（我的任务-评价页）

    //任务时间范围
    private boolean within;
    private boolean within0;
    private boolean within1;
    private boolean within2;
    private boolean within3;
    private boolean within4;
    private boolean within5;



    //默认构造函数

    public task(){
        this.launchtime = TimeUtils.getNowTime();
        this.isValid = true;
        this.ifDefault = false;
        this.ifAccepted = false; //新建任务时，默认接收状态为false：未被接收
        this.progress = 1; //默认进度为1：已发布
        this.within = true;
        this.within0 = true;
        this.within1 = false;
        this.within2 = false;
        this.within3 = false;
        this.within4 = false;
        this.within5 = false;
    }


    // 临时构造函数

    public task(String launcherName, int launcherImageId, String launcherPhoneNumber, String subtaskType,
                String location, String ddlDate, String ddlTime, double payment, String description){

        this.launcher = launcher;

        this.launcherName = launcherName;
        this.launcherImageId = launcherImageId;
        this.launcherPhoneNumber = launcherPhoneNumber;
        this.subtaskType = subtaskType;
        this.location = location;
        this.ddlDate = ddlDate;
        this.ddlTime = ddlTime;
        this.payment = payment;
        this.description = description;

        this.ifAccepted = false; //新建任务时，默认接收状态为false：未被接收
        this.progress = 1; //默认进度为1：已发布
        this.score = -1; //默认评分为-1：无评分

    }



    //函数：任务种类

    public void setTaskType(String subtaskType){
        if(subtaskType == "占座" || subtaskType =="拿快递" ||subtaskType =="买饭" ||subtaskType == "买东西"|| subtaskType =="拼单")
        {taskType = "跑腿";}
        else if(subtaskType == "电子产品修理" || subtaskType =="家具器件组装" ||subtaskType =="学习作业辅导" ||
                subtaskType == "技能培训"|| subtaskType =="找同好")
        {taskType = "技能";}
        else{taskType = "咨询";}
    }


    //函数：时间段

    public void checkWithin(){
        String ddl = this.getDdl();
        within1 = TimeUtils.isDateWithinThreeHour(ddl);
        within2 = TimeUtils.isDateWithinOneDay(ddl);
        within3 = TimeUtils.isDateWithinThreeDay(ddl);
        within4 = TimeUtils.isDateWithinOneWeek(ddl);
        within5 = TimeUtils.isDateWithinOneMonth(ddl);
    }


    public void ifWithin(int position){
        if(position==0){
            within = true;
        }
        if(position==1){
            if(within1=true) within = true;
            else within = false;
        }
        if(position==2){
            if(within2=true) within = true;
            else within = false;
        }
        if(position==3){
            if(within3=true) within = true;
            else within = false;
        }
        if(position==4){
            if(within4=true) within = true;
            else within = false;
        }
        if(position==5){
            if(within5=true) within = true;
            else within = false;
        }
    }



    //函数：检查任务状态

    public boolean getIfDefault(){
        return ifDefault;
    }

    public void setIfDefault(boolean a){
        this.ifDefault = a;
    }

    public void checkIsValid(){
        if(TimeUtils.isDateOneBigger(TimeUtils.getNowTime(),ddl)){
            this.isValid = false;
        }
    }

    public void setIsValid(boolean a){
        this.isValid = a;
    }

    public boolean ifAccepted() {
        return ifAccepted;
    }

    public void setIfAccepted(boolean ifAccepted) {
        this.ifAccepted = ifAccepted;
    }


    //TODO: 是否过期Alarm
    /*
    public class IfValid extends Service {
        @Override
        public int onStartCommand (Intent intent, int flags, int startId){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    public boolean IfDefault = false;
                }
            }).start();
            AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
            int taskTime;
            long triggerAtTime = SystemClock.elapsedRealtime() + taskTime;
            Intent i = new intent(this, IfValid.class);
            PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
            manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
            return super.onStartCommand(intent, flags, startId);
        }
    }*/



    //函数：时间

    public String getLaunchtime() {
        return launchtime;
    }

    public void setLaunchtime() {
        this.launchtime = TimeUtils.getNowTime();
    }

    public String getFinishtime(){return finishtime;}

    public void setFinishtime(){
        this.finishtime = TimeUtils.getNowTime();
    }

    public String getDdl(){return ddl;}

    public void setDdl(){
        this.ddl = ddlDate + " " + ddlTime; //ddl:2018/12/31 17:00
    }

    public String getDdlDate() {
        return ddlDate;
    }

    public void setDdlDate(String ddlDate) {this.ddlDate = ddlDate;}

    public String getDdlTime() {
        return ddlTime;
    }

    public void setDdlTime(String ddlTime) {this.ddlTime = ddlTime;}


    //函数：用户

    public user getLauncher(){
        return launcher;
    }

    public user getHelper(){
        return helper;
    }

    public void setLauncher(user launcher){
        this.launcher =  launcher;
    }

    public void setHelper(user helper){
        this.helper = helper;
    }

    public String getLauncherName(){
        return launcherName;
    }

    public void setLauncherName(String launcherName){this.launcherName = launcherName;}

    public void setHelperName(String helperName){this.helperName = helperName;}

    public int getLauncherImageId() {
        return launcherImageId;
    }

    public String getHelperName(){
        return helperName;
    }

    public void setLauncherImageId(int launcherImageId) {
        this.launcherImageId = launcherImageId;
    }

    public String getLauncherPhoneNumber() {
        return launcherPhoneNumber;
    }

    public void setLauncherPhoneNumber(String launcherPhoneNumber) {
        this.launcherPhoneNumber = launcherPhoneNumber;
    }



    //函数：任务基本信息

    public String getTaskType() {
        return taskType;
    }

    public String getSubtaskType() {
        return subtaskType;
    }

    public void setSubtaskType(String subtask) {
        this.subtaskType = subtask;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    public String getArea(){
        return area;
    }

    public void setArea(String area){
        this.area = area;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }



    //函数：任务进度

    public int getProgress() {return progress;}

    public void setProgress(int process) {
        this.progress = progress;
    }



    //函数：任务评分

    public float getScore() {
        return score;
    }

    public void setScore(float score) {this.score = score;}




}





