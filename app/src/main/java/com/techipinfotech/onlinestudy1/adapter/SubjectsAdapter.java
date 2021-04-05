package com.techipinfotech.onlinestudy1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.Nullable;

import java.util.List;


import com.bumptech.glide.Glide;
import com.techipinfotech.onlinestudy1.API;
import com.techipinfotech.onlinestudy1.ui.HomeFragmentDirections;
import com.techipinfotech.onlinestudy1.R;
import com.techipinfotech.onlinestudy1.model.SubjectsItem;
import com.techipinfotech.onlinestudy1.ui.SubjectsFragmentDirections;

public class SubjectsAdapter extends RecyclerView.Adapter<SubjectsAdapter.SubjectsViewHolder> {
    Context context;
    List<SubjectsItem> subjects;

    public SubjectsAdapter(@Nullable Context context, @Nullable List<SubjectsItem> subjects) {

        this.context = context;
        this.subjects = subjects;
        setHasStableIds(true);
    }


    @NonNull
    @Override
    public SubjectsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subject, parent, false);
        return new SubjectsViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull SubjectsViewHolder holder, int position) {
        SubjectsItem subject = subjects.get(position);
        holder.subject_name.setText(subject.getSubjectName());
        holder.subject_id.setText(subject.getSubjectId());

        String url = API.SUBJECT.toString() + subject.getSubjectImage();
        Glide
                .with(context)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.ic_triangle)
                .into(holder.subject_image);

        holder.subject_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action = SubjectsFragmentDirections.actionSubjectsFragmentToChaptersFragment(subject);
                Navigation.findNavController(v).navigate(action);

            }
        });
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    class SubjectsViewHolder extends RecyclerView.ViewHolder {
        ImageView subject_image;
        TextView subject_name;
        TextView subject_id;
        CardView subject_item;

        public SubjectsViewHolder(@NonNull View itemView) {
            super(itemView);
            subject_image = itemView.findViewById(R.id.subject_image);
            subject_name = itemView.findViewById(R.id.subject_name);
            subject_id = itemView.findViewById(R.id.subject_id);
            subject_item = itemView.findViewById(R.id.subject_item);
        }
    }
}
