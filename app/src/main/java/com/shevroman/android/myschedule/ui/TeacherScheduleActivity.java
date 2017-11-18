package com.shevroman.android.myschedule.ui;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;

import com.shevroman.android.myschedule.App;
import com.shevroman.android.myschedule.Lesson;
import com.shevroman.android.myschedule.R;
import com.shevroman.android.myschedule.ScheduleRepository;
import com.shevroman.android.myschedule.databinding.ActivityTeacherScheduleBinding;

import java.io.IOException;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class TeacherScheduleActivity extends AppCompatActivity {
    private static final String PARAM_TEACHER = "teacher";

    private ActivityTeacherScheduleBinding binding;
    private String teacher;
    private ScheduleRepository scheduleRepository = App.getInstance().getScheduleRepository();


    public static Intent newIntent(Activity activity, String teacher){
        Intent intent = new Intent(activity, TeacherScheduleActivity.class);
        intent.putExtra(PARAM_TEACHER, teacher);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        teacher = getIntent().getStringExtra(PARAM_TEACHER);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_teacher_schedule);
        binding.teacherScheduleContent.setText("This is a text " + teacher);
//        new ScheduleAsyncTask(getApplicationContext()).execute();
//        showSchedule();
    }

    private void showSchedule() {
        Calendar now = Calendar.getInstance();
        final int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) + 1;
        final Lesson.Semester semester = (month > 8) ? Lesson.Semester.Spring : Lesson.Semester.Autumn;
        int numDayOfWeek = now.get(Calendar.DAY_OF_WEEK);
        Lesson.DayOfWeek dayOfWeek = null;
        switch (numDayOfWeek) {
            case Calendar.MONDAY:
                dayOfWeek = Lesson.DayOfWeek.Monday;
                break;
            case Calendar.TUESDAY:
                dayOfWeek = Lesson.DayOfWeek.Tuesday;
                break;
            case Calendar.WEDNESDAY:
                dayOfWeek = Lesson.DayOfWeek.Wednesday;
                break;
            case Calendar.THURSDAY:
                dayOfWeek = Lesson.DayOfWeek.Thursday;
                break;
            case Calendar.FRIDAY:
                dayOfWeek = Lesson.DayOfWeek.Friday;
                break;
            default:
                break;

        }

        final Lesson.DayOfWeek finalDayOfWeek = dayOfWeek;
        new AsyncTask<Void, Void, List<Lesson>>() {
            @Override
            protected void onPostExecute(List<Lesson> lessons) {
                showScheduleOnUI(lessons);
            }

            @Override
            protected List<Lesson> doInBackground(Void... params) {
                try {
                    return scheduleRepository.getTeacherLessons(teacher, year, semester, finalDayOfWeek);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return Collections.emptyList();
            }
        };
    }

    private void showScheduleOnUI(List<Lesson> lessons) {
        StringBuilder sb = new StringBuilder();
//        Lesson.DayOfWeek prevDayOfWeek = null;
//        for (Lesson lesson : lessons) {
//            if (prevDayOfWeek != lesson.getDayOfWeek()) {
//                switch (lesson.getDayOfWeek()) {
//                    case Monday:
//                        sb.append("<b>Понеділок</b>");
//                        break;
//                    case Tuesday:
//                        sb.append("<br><b>Вівторок</b>");
//                        break;
//                    case Wednesday:
//                        sb.append("<br><b>Середа</b>");
//                        break;
//                    case Thursday:
//                        sb.append("<br><b>Четвер</b>");
//                        break;
//                    case Friday:
//                        sb.append("<br><b>П'ятниця</b>");
//                        break;
//                }
//                prevDayOfWeek = lesson.getDayOfWeek();
//                sb.append("<br>");
//            }
//            sb.append(lesson.getLessonNumber())
//                    .append(". ");
//            if (lesson.getWeek() == Denominator) {
//                sb.append("<small>знам</small> ");
//            } else if (lesson.getWeek() == Numerator) {
//                sb.append("<small>чис</small> ");
//            }
//            sb.append(lesson.getName())
//                    .append("  <font color=\"black\">")
//                    .append(lesson.getLocation())
//                    .append("</font>  <i>")
//                    .append(lesson.getTeacher())
//                    .append("</i><br>");
//        }
        binding.teacherScheduleContent.setText(Html.fromHtml(sb.toString()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                teacher = data.getStringExtra("t_result");
                Log.v("teacherName: ", teacher);
//                showSchedule();
            }
        }
    }
}
