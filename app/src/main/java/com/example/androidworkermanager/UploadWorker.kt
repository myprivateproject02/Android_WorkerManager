package com.example.androidworkermanager

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class UploadWorker(context: Context, parameters: WorkerParameters) : Worker(context, parameters) {

    private val TAG = "UploadWorker"

    companion object{
        const val KEY_WORK = "key_work"
    }


    override fun doWork(): Result {

        try {
            val count = inputData.getInt("Key_Int", 0)
            for (i in 0..count) {
                Log.e(TAG, "doWork: Uploading   $i")
            }
            val time = SimpleDateFormat("dd/mm/yyyy hh:mm:ss")
            val currentData = time.format(Date())
            val outputata = Data.Builder().putString(KEY_WORK, currentData).build()
            return Result.success(outputata)
        } catch (e: Exception) {
            return Result.failure()
        }

    }


}