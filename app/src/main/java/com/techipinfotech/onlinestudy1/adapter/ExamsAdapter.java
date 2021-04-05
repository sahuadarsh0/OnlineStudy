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

import com.bumptech.glide.Glide;
import com.techipinfotech.onlinestudy1.API;
import com.techipinfotech.onlinestudy1.R;
import com.techipinfotech.onlinestudy1.model.ExamsItem;
import com.techipinfotech.onlinestudy1.test.TestSeriesFragmentDirections;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ExamsAdapter extends RecyclerView.Adapter<ExamsAdapter.ExamsViewHolder> {

    List<ExamsItem> examsItems;
    Context context;

    public ExamsAdapter(@Nullable Context context, @Nullable List<ExamsItem> examsItems) {

        this.examsItems = examsItems;
        this.context = context;
        setHasStableIds(true);
    }


    @NonNull
    @Override
    public ExamsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exam, parent, false);
        return new ExamsViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ExamsViewHolder holder, int position) {
        ExamsItem item = examsItems.get(position);
        holder.exam_name.setText(item.getExamName());
        holder.duration.setText(item.getDuration() + " min");


        holder.exam_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action = null;
                if (item.getResult()!=null && item.getResult().size() > 0 )
                    action = TestSeriesFragmentDirections.actionTestSeriesFragmentToTestResultDashboardFragment(item.getResult().get(0));
                else
                    action = TestSeriesFragmentDirections.actionTestSeriesFragmentToExamActivity(item);

                Navigation.findNavController(v).navigate(action);


            }
        });
        String url = API.EXAM.toString() + item.getExamImage();
        Glide
                .with(context)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.ic_triangle)
                .into(holder.exam_image);
    }

    @Override
    public int getItemCount() {
        return examsItems.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    class ExamsViewHolder extends RecyclerView.ViewHolder {
        TextView exam_name, duration;
        ImageView exam_image;
        CardView exam_item;

        public ExamsViewHolder(@NonNull View itemView) {
            super(itemView);
            exam_name = itemView.findViewById(R.id.exam_name);
            duration = itemView.findViewById(R.id.duration);
            exam_image = itemView.findViewById(R.id.exam_image);
            exam_item = itemView.findViewById(R.id.exam_item);
        }
    }
}
