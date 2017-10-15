package com.shevroman.android.myschedule;

import android.app.Activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import au.com.bytecode.opencsv.CSVReader;

/**
 * Created by Рома on 04.02.2017.
 */

public class ScheduleRepository extends Activity {


    public Set<String> getAllGroups(int year, Lesson.Semester semester) throws IOException {
        Set<String> groups = new HashSet<>();
        List<Lesson> lessons = readAllLessons();
        for (Lesson lesson : lessons) {
            if (year == lesson.getYear() && semester == lesson.getSemester()) {
                groups.add(lesson.getGroupName());
            }
        }
        return groups;
    }


    public List<Lesson> getGroupLessons(String nameOfGroup, int year, Lesson.Semester semester
    ) throws IOException {
        List<Lesson> lessons = new ArrayList<>();
        List<Lesson> allLessons = readAllLessons();
        for (Lesson lesson : allLessons) {
            if (nameOfGroup.equals(lesson.getGroupName())
                    && year == lesson.getYear()
                    && semester == lesson.getSemester()) {
                lessons.add(lesson);
            }
        }
        return lessons;
    }

    public Set<String> getAllTeachers(int year, Lesson.Semester semester) throws IOException {
        Set<String> teachers = new HashSet<>();
        List<Lesson> lessons = readAllLessons();
        for (Lesson lesson : lessons) {
            if (year == lesson.getYear() && semester == lesson.getSemester()) {
                teachers.add(lesson.getTeacher());
            }
        }
        return teachers;
    }

    public List<Lesson> getTeacherLessons(String teacher, int year, Lesson.Semester semester, Lesson.DayOfWeek dayOfWeek) throws IOException {
        List<Lesson> lessons = new ArrayList<>();
        List<Lesson> allLessons = readAllLessons();
        for (Lesson lesson : allLessons) {
            if (teacher.equals(lesson.getTeacher())
                    && year == lesson.getYear()
                    && semester == lesson.getSemester()
                    && dayOfWeek == lesson.getDayOfWeek()) {
                lessons.add(lesson);
            }
        }
        return lessons;
    }

    private List<Lesson> readAllLessons() throws IOException {

        CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(new File(getFilesDir(), "schedule.csv"))));
        List<Lesson> lessons = new ArrayList<>();
        //lessonName,dayOfWeek,lessonNumber,teacher,location,week,groupName,year,semester
        String[] nextLine;
        boolean headers = true;
        while ((nextLine = reader.readNext()) != null) {
            if (headers) {
                headers = false;
                continue;
            }
            Lesson lesson = new Lesson();
            lesson.setName(nextLine[0]);
            String dayOfWeek = nextLine[1];
            if ("пн".equals(dayOfWeek)) {
                lesson.setDayOfWeek(Lesson.DayOfWeek.Monday);
            } else if ("вт".equals(dayOfWeek)) {
                lesson.setDayOfWeek(Lesson.DayOfWeek.Tuesday);
            } else if ("ср".equals(dayOfWeek)) {
                lesson.setDayOfWeek(Lesson.DayOfWeek.Wednesday);
            } else if ("чт".equals(dayOfWeek)) {
                lesson.setDayOfWeek(Lesson.DayOfWeek.Thursday);
            } else if ("пт".equals(dayOfWeek)) {
                lesson.setDayOfWeek(Lesson.DayOfWeek.Friday);
            }
            lesson.setLessonNumber(Integer.valueOf(nextLine[2]));
            lesson.setTeacher(nextLine[3]);
            lesson.setLocation(nextLine[4]);
            String week = nextLine[5];
            if ("всі".equals(week)) {
                lesson.setWeek(Lesson.Week.All);
            } else if ("чисельник".equals(week)) {
                lesson.setWeek(Lesson.Week.Numerator);
            } else if ("знаменник".equals(week)) {
                lesson.setWeek(Lesson.Week.Denominator);
            }
            lesson.setGroupName(nextLine[6]);
            lesson.setYear(Integer.parseInt(nextLine[7]));
            String semester = nextLine[8];
            if ("весна".equals(semester)) {
                lesson.setSemester(Lesson.Semester.Spring);
            } else if ("осінь".equals(semester)) {
                lesson.setSemester(Lesson.Semester.Autumn);
            }
            lessons.add(lesson);
        }
        return lessons;
    }
}
