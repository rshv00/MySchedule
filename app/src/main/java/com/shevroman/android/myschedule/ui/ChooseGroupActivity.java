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
import android.widget.Toast;

import com.shevroman.android.myschedule.Lesson;
import com.shevroman.android.myschedule.R;
import com.shevroman.android.myschedule.ScheduleAsyncTask;
import com.shevroman.android.myschedule.ScheduleRepository;
import com.shevroman.android.myschedule.databinding.ActivityChooseGroupBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChooseGroupActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout swipeRefreshLayout;
    public static String csvR;
    private ActivityChooseGroupBinding binding;
    private ScheduleRepository scheduleRepository = new ScheduleRepository();
    boolean swipeRefresh = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ScheduleAsyncTask asyncTask = new ScheduleAsyncTask(getApplicationContext());
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_choose_group);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new ScheduleAsyncTask(getApplicationContext()).execute();
                finish();
                startActivity(getIntent());
                swipeRefresh = true;
                swipeRefreshLayout.setRefreshing(false);
                if (!csvR.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Оновлено",Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (swipeRefresh) {
            asyncTask.execute();

        }

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
        // Associate searchable configuration with the SearchView
        /*SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));*/
        return true;
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
    public ActivityChooseGroupBinding getBinding(){
        return binding;
    }
}