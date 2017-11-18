package com.shevroman.android.myschedule.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shevroman.android.myschedule.R;
import com.shevroman.android.myschedule.ui.TeacherScheduleActivity;

import java.util.List;

public class TeachersAdapter extends RecyclerView.Adapter<TeachersAdapter.ViewHolder> {
    private final Activity activity;
    private final List<String> teachers;

    public TeachersAdapter(Activity activity, List<String> teachers) {
        this.activity = activity;
        this.teachers = teachers;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_choose_teacher_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.teacherNameText.setText(teachers.get(position));
        viewHolder.teacherName = teachers.get(position);
    }

    @Override
    public int getItemCount() {
        return teachers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView teacherNameText;
        String teacherName;

       public ViewHolder(View itemView) {
            super(itemView);
            teacherNameText = (TextView) itemView.findViewById(R.id.choose_teacher_item);
            teacherNameText.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            activity.startActivity(TeacherScheduleActivity.newIntent(activity, "my teacher"));
            activity.finish();
        }
    }
}