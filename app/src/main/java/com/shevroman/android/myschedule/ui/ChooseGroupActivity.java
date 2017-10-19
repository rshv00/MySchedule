package com.shevroman.android.myschedule.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.shevroman.android.myschedule.Lesson;
import com.shevroman.android.myschedule.R;
import com.shevroman.android.myschedule.ScheduleAsyncTask;
import com.shevroman.android.myschedule.ScheduleRepository;
import com.shevroman.android.myschedule.databinding.ActivityChooseGroupBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChooseGroupActivity extends AppCompatActivity {
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public static String csvR;
    private ActivityChooseGroupBinding binding;
    private ScheduleRepository scheduleRepository = new ScheduleRepository();
    final String LOG_TAG = getClass().getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_choose_group);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ScheduleAsyncTask asyncTask = new ScheduleAsyncTask();
        asyncTask.execute();
        new AsyncTask<Void, Void, List<String>>() {
            @Override
            protected List<String> doInBackground(Void... voids) {
                try {
                    ArrayList<String> allGroups = new ArrayList<>(scheduleRepository.getAllGroups(2017, Lesson.Semester.Autumn));
                    Collections.sort(allGroups);
                    return allGroups;
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.items_choose_group, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.another_group:
                Intent intent = new Intent(this, ChooseGroupActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.about:
                Intent intent1 = new Intent(this, AboutActivity.class);
                startActivity(intent1);
        }

        return true;
    }
}