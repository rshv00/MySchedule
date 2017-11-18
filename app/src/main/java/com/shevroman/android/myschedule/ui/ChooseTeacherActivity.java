package com.shevroman.android.myschedule.ui;

import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.shevroman.android.myschedule.App;
import com.shevroman.android.myschedule.Lesson;
import com.shevroman.android.myschedule.R;
import com.shevroman.android.myschedule.ScheduleAsyncTask;
import com.shevroman.android.myschedule.ScheduleRepository;
import com.shevroman.android.myschedule.adapters.TeachersAdapter;
import com.shevroman.android.myschedule.databinding.ActivityChooseTeacherBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class ChooseTeacherActivity extends AppCompatActivity {
    private ActivityChooseTeacherBinding binding;
    ScheduleRepository scheduleRepository = App.getInstance().getScheduleRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_choose_teacher);
        binding.chooseTeacherRecyclerView.setLayoutManager(new LinearLayoutManager(ChooseTeacherActivity.this));
        new ScheduleAsyncTask().execute();
        new TeachersAsyncTask().execute();
    }

    private class TeachersAsyncTask extends AsyncTask<Void, Void, List<String>> {
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);
        final Lesson.Semester semester = (month < 8) ? Lesson.Semester.Spring : Lesson.Semester.Autumn;


        @Override
        protected List<String> doInBackground(Void... params) {
            try {
                List<String> teachers = new ArrayList<>(scheduleRepository.getAllTeachers(year, semester));
                Collections.sort(teachers);
                return teachers;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return Collections.emptyList();
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            binding.chooseTeacherRecyclerView.setAdapter(new TeachersAdapter(ChooseTeacherActivity.this, strings));
        }
    }
}

