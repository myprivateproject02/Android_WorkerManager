package com.example.androidworkermanager

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.text.SimpleDateFormat
import java.util.*

class DownloadingWorker(context: Context, parameters: WorkerParameters) :
    Worker(context, parameters) {

    private val TAG = "DownloadingWorker"
    override fun doWork(): Result {


        for (i in 0..100) {
            Log.e(TAG, "doWork: Uploading   $i")
        }
        val time = SimpleDateFormat("dd/mm/yyyy hh:mm:ss")
        val currentData = time.format(Date())
        Log.e(TAG, "doWork: "+currentData )
        val outputata = Data.Builder().putString(UploadWorker.KEY_WORK, currentData).build()
        return Result.success(outputata)

    }
}