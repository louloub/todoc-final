package com.cleanup.todoc.data.dao;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cleanup.todoc.data.model.Task;

import java.util.List;

@Dao
public
interface TaskDao {

    @Query("SELECT * FROM Task")
    LiveData<List<Task>> getListTaskLiveData();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertTask(Task task);

    // TODO LOULOUB AJOUTER LA SUPPRESSION
    /*@Query("DELETE FROM Task")
    void deleteTask(Task task);*/

    @Query("DELETE FROM Task WHERE id = :taskId")
    void deleteTask(long taskId);
}