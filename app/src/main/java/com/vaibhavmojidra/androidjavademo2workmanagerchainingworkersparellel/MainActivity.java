package com.vaibhavmojidra.androidjavademo2workmanagerchainingworkersparellel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.view.View;

import com.vaibhavmojidra.androidjavademo2workmanagerchainingworkersparellel.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);

        OneTimeWorkRequest oneTimeWorkRequestForWorker1=new OneTimeWorkRequest.Builder(MyWorker1.class).build();
        OneTimeWorkRequest oneTimeWorkRequestForWorker2=new OneTimeWorkRequest.Builder(MyWorker2.class).build();

        WorkManager workManager=WorkManager.getInstance(this);

        workManager.getWorkInfoByIdLiveData(oneTimeWorkRequestForWorker1.getId()).observe(this, workInfo -> {
            binding.work1Textview.setText(workInfo.getState().name());
        });

        workManager.getWorkInfoByIdLiveData(oneTimeWorkRequestForWorker2.getId()).observe(this, workInfo -> {
            binding.work2Textview.setText(workInfo.getState().name());
        });

        List<OneTimeWorkRequest> oneTimeWorkRequestList=new ArrayList<>();
        oneTimeWorkRequestList.add(oneTimeWorkRequestForWorker1);
        oneTimeWorkRequestList.add(oneTimeWorkRequestForWorker2);

        binding.startWorksButton.setOnClickListener(view -> {
            workManager.enqueue(oneTimeWorkRequestList);
        });
    }
}