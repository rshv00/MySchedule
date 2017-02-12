package com.shevroman.android.myschedule;

/**
 * Created by Рома on 04.02.2017.
 */

public class Lesson {

    private String name;
    private DayOfWeek dayOfWeek;
    private int lessonNumber;
    private String teacher;
    private String location;
    private Week week;
    private String groupName;
    private int year;
    private Semester semester;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getLessonNumber() {
        return lessonNumber;
    }

    public void setLessonNumber(int lessonNumber) {
        this.lessonNumber = lessonNumber;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Week getWeek() {
        return week;
    }

    public void setWeek(Week week) {
        this.week = week;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "name='" + name + '\'' +
                ", dayOfWeek=" + dayOfWeek +
                ", lessonNumber=" + lessonNumber +
                ", teacher='" + teacher + '\'' +
                ", location='" + location + '\'' +
                ", week=" + week +
                ", groupName='" + groupName + '\'' +
                ", year=" + year +
                ", semester=" + semester +
                '}';
    }

    public enum Week {All, Numerator, Denominator}

    public enum DayOfWeek {Monday, Tuesday, Wednesday, Thursday, Friday}

    public enum Semester {Autumn, Spring}


}
