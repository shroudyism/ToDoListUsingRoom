package com.example.shroudyism.todolist;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity (tableName = "task")
public class Task {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String title;
    public String desc;

    public Task(int id,String title,String desc){
        this.id=id;
        this.title=title;
        this.desc=desc;
    }

    @Ignore
    public Task(String title, String desc) {
        this.title = title;
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}