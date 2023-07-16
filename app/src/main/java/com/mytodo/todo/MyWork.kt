package com.mytodo.todo

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWork(
    context: Context,
    workParams: WorkerParameters
) : Worker(context, workParams) {


    override fun doWork(): Result {



        Log.d("MyWorker", "Message Received")
        return Result.success()
    }


}