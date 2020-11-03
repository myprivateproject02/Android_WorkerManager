package com.example.androidworkermanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.work.*
import com.example.androidworkermanager.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.upload.setOnClickListener { view ->

            setPeriodicWorkRequest()
//            OneTimeWorkRequest()

        }

    }


    private fun OneTimeWorkRequest() {
        val workManager = WorkManager.getInstance(this)
        val data = Data.Builder().putInt("Key_Int", 125).build()

        val filteringRequest = OneTimeWorkRequest.Builder(FilteringWorker::class.java).build()
        val downloadingWorker = OneTimeWorkRequest.Builder(DownloadingWorker::class.java).build()

        val constraint = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val uploadRequest = OneTimeWorkRequest.Builder(UploadWorker::class.java)
            .setConstraints(constraint)
            .setInputData(data)
            .build()


        val parellelWorkRequest = mutableListOf<OneTimeWorkRequest>()
        parellelWorkRequest.add(downloadingWorker)
        parellelWorkRequest.add(filteringRequest)

        workManager
            .beginWith(parellelWorkRequest)
            .then(uploadRequest)
            .enqueue()
        workManager.getWorkInfoByIdLiveData(uploadRequest.id)
            .observe(this, Observer {
                binding.text.text = it.state.name
                if (it.state.isFinished) {
                    val data = it.outputData
                    val message = data.getString(UploadWorker.KEY_WORK)
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                }
            })


    }

    private fun setPeriodicWorkRequest() {
        val periodicWorkRequest = PeriodicWorkRequest.Builder(DownloadingWorker::class.java, 16, TimeUnit.MINUTES).build()
        val workManager = WorkManager.getInstance(this)
        workManager.enqueue(periodicWorkRequest)
        workManager.getWorkInfoByIdLiveData(periodicWorkRequest.id)
            .observe(this, Observer {
                if (it.state.isFinished) {
                    val data = it.outputData
                    val message = data.getString(UploadWorker.KEY_WORK)
                    binding.text.text = message.toString()
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                }
            })
    }
}