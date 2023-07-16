package com.mytodo.todo.di

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Room
import com.mytodo.todo.repository.Repository
import com.mytodo.todo.room.TodoDAO
import com.mytodo.todo.room.TodoDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DeleteModule() {


    @Provides
    fun dao(todoDB: TodoDB): TodoDAO {
        return todoDB.todoDao()
    }

    @Provides
    fun provideTodoDB(context : Application): TodoDB {
        return Room.databaseBuilder(
            context.applicationContext, TodoDB::class.java,
            "todo_db"
        ).build()
    }


    @Provides
    fun deleteByIdDependency(todoDB: TodoDB): Repository {
        return Repository(todoDB)
    }
}