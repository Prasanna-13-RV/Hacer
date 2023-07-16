package com.mytodo.todo.di

import android.app.Application
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.mytodo.todo.repository.Repository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@HiltWorker
class DeleteDi @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    val repository: Repository
) : Worker(appContext, workerParams) {

    override fun doWork(): Result {

//        repository.deleteItemsOlderThan(1)
//        Toast.makeText(applicationContext,"HelloEveryone", Toast.LENGTH_SHORT).show()
        Log.d("HelloEveryone", "HelloEveryone")
        return Result.success()
    }

}