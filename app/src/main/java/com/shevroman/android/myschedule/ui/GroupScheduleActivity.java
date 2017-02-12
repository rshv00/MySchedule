package com.example.android.myschedule.ui;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;

import com.example.android.myschedule.Lesson;
import com.example.android.myschedule.R;
import com.example.android.myschedule.ScheduleRepository;
import com.example.android.myschedule.databinding.ActivityGroupScheduleBinding;

import java.io.IOException;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import static com.example.android.myschedule.Lesson.Week.Denominator;
import static com.example.android.myschedule.Lesson.Week.Numerator;

public class GroupScheduleActivity extends AppCompatActivity {
    private String groupName;
    private ActivityGroupScheduleBinding binding;
    private ScheduleRepository scheduleRepository = new ScheduleRepository();

    static void show(Activity activity, String groupName) {
        Intent intent = new Intent(activity, GroupScheduleActivity.class);
        intent.putExtra("groupName", groupName);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_group_schedule);
        groupName = getIntent().getStringExtra("groupName");

        Calendar now = Calendar.getInstance();
        final int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) + 1;
        final Lesson.Semester semester = (month < 8) ? Lesson.Semester.Spring : Lesson.Semester.Autumn;

        new AsyncTask<Void, Void, List<Lesson>>() {
            @Override
            protected List<Lesson> doInBackground(Void... params) {
                try {
                    return scheduleRepository.getGroupLessons(groupName, year, semester);
                } catch (IOException e) {
                    e.printStackTrace();
                    return Collections.emptyList();
                }
            }

            @Override
            protected void onPostExecute(List<Lesson> lessons) {
                showScheduleOnUI(lessons);
            }
        }.execute();
    }

    private void showScheduleOnUI(List<Lesson> lessons) {
        StringBuilder sb = new StringBuilder();
        Lesson.DayOfWeek prevDayOfWeek = null;
        for (Lesson lesson : lessons) {
            if (prevDayOfWeek != lesson.getDayOfWeek()) {
                switch (lesson.getDayOfWeek()) {
                    case Monday:
                        sb.append("<b>Понеділок</b>");
                        break;
                    case Tuesday:
                        sb.append("<br><b>Вівторок</b>");
                        break;
                    case Wednesday:
                        sb.append("<br><b>Середа</b>");
                        break;
                    case Thursday:
                        sb.append("<br><b>Четвер</b>");
                        break;
                    case Friday:
                        sb.append("<br><b>П'ятниця</b>");
                        break;
                }
                prevDayOfWeek = lesson.getDayOfWeek();
                sb.append("<br>");
            }
            sb.append(lesson.getLessonNumber())
                    .append(". ");
            if (lesson.getWeek()==Denominator) {
                sb.append("<small>знам</small> ");
            }else if (lesson.getWeek()==Numerator){
                sb.append("<small>чис</small> ");
            }
            sb.append(lesson.getName())
                    .append("  <font color=\"black\">")
                    .append(lesson.getLocation())
                    .append("</font>  <i>")
                    .append(lesson.getTeacher())
                    .append("</i><br>");
        }
        binding.groupScheduleContent.setText(Html.fromHtml(sb.toString()));
    }
}