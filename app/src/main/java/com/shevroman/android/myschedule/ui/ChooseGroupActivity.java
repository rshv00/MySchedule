package com.shevroman.android.myschedule.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.shevroman.android.myschedule.App;
import com.shevroman.android.myschedule.Lesson;
import com.shevroman.android.myschedule.R;
import com.shevroman.android.myschedule.ScheduleAsyncTask;
import com.shevroman.android.myschedule.ScheduleRepository;
import com.shevroman.android.myschedule.adapters.GroupsAdapter;
import com.shevroman.android.myschedule.databinding.ActivityChooseGroupBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class ChooseGroupActivity extends AppCompatActivity
        implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout swipeRefreshLayout;
    private ActivityChooseGroupBinding binding;
    private ScheduleRepository scheduleRepository = App.getInstance().getScheduleRepository();
    private List<String> groups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_choose_group);
        binding.chooseGroupRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.chooseGroupRecyclerView.removeAllViews();
                binding.chooseGroupRecyclerView.setLayoutManager(new LinearLayoutManager(
                        ChooseGroupActivity.this));
                new ScheduleAsyncTask().execute();
                new ChooseGroupAsyncTask().execute();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        new ScheduleAsyncTask().execute();

        new ChooseGroupAsyncTask().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.items_choose_group, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.about:
                Intent intent1 = new Intent(this, AboutActivity.class);
                startActivity(intent1);
                break;
            case R.id.break_call:
                Intent i2 = new Intent(this, BreaksActivity.class);
                startActivity(i2);
                break;
            case R.id.choose_teacher:
                Intent i = new Intent(this, ChooseTeacherActivity.class);
                startActivity(i);
                break;
        }

        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onRefresh() {


    }


    private class ChooseGroupAsyncTask extends AsyncTask<Void, Void, List<String>> {
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);
        final Lesson.Semester semester = (month < 8) ? Lesson.Semester.Spring : Lesson.Semester.Autumn;

        @Override
        protected List<String> doInBackground(Void... voids) {
            try {
                ArrayList<String> allGroups = new ArrayList<>(scheduleRepository.getAllGroups(year, semester));
                Collections.sort(allGroups);
                groups = allGroups;
                return allGroups;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return Collections.emptyList();
        }

        @Override
        protected void onPostExecute(List<String> groups) {
            binding.chooseGroupRecyclerView.setAdapter(new GroupsAdapter(ChooseGroupActivity.this, groups));
        }
    }
}