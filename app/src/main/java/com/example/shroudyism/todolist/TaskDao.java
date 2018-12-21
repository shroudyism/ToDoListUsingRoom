package com.example.shroudyism.todolist;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM task ORDER BY id")
    List<Task> loadAllTasks();

    @Insert
    void insertTask(Task task);

    @Delete
    void deleteTask(Task task);

}