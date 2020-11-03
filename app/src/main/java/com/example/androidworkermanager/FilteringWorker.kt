package com.example.androidworkermanager

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class FilteringWorker(context: Context, parameters: WorkerParameters) :
    Worker(context, parameters) {

    private val TAG = "FilteringWorker"


    override fun doWork(): Result {


        for (i in 0..5000) {
            Log.e(TAG, "doWork: Uploading   $i")
        }
        val time = SimpleDateFormat("dd/mm/yyyy hh:mm:ss")
        val currentData = time.format(Date())

        return Result.success()

    }


}