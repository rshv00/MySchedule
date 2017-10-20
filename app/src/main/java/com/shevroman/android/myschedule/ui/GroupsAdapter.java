package com.shevroman.android.myschedule.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shevroman.android.myschedule.R;

import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by taras on 11.02.17.
 */

public class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.ViewHolder> {
    private final Activity activity;
    private final List<String> groups;

    public GroupsAdapter(Activity activity, List<String> groups) {
        this.activity = activity;
        this.groups = groups;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_choose_group_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.groupNameText.setText(groups.get(position));
        viewHolder.groupName = groups.get(position);
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView groupNameText;
        String groupName;

        public ViewHolder(View itemView) {
            super(itemView);
            groupNameText = (TextView) itemView.findViewById(R.id.choose_group_item);
            groupNameText.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // 2
            Intent returnIntent = new Intent();
            returnIntent.putExtra("result", groupName);
            activity.setResult(RESULT_OK, returnIntent);
            activity.finish();
        }
    }
}