package com.shevroman.android.myschedule.ui;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.shevroman.android.myschedule.Lesson;
import com.shevroman.android.myschedule.R;
import com.shevroman.android.myschedule.ScheduleRepository;
import com.shevroman.android.myschedule.databinding.ActivityChooseGroupBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChooseGroupActivity extends AppCompatActivity {
    private ActivityChooseGroupBinding binding;
    private ScheduleRepository scheduleRepository = new ScheduleRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_choose_group);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        new AsyncTask<Void, Void, List<String>>() {
            @Override
            protected List<String> doInBackground(Void... voids) {
                // hh h
                try {
                    return new ArrayList<>(scheduleRepository.getAllGroups(2017, Lesson.Semester.Spring));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return Collections.emptyList();
            }

            @Override
            protected void onPostExecute(List<String> groups) {
                binding.recyclerView.setAdapter(new GroupsAdapter(ChooseGroupActivity.this, groups));
            }
        }.execute();



    }


}
