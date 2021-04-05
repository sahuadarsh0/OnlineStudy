package com.techipinfotech.onlinestudy1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


import com.techipinfotech.onlinestudy1.R;
import com.techipinfotech.onlinestudy1.ui.TopicsFragmentDirections;
import com.techipinfotech.onlinestudy1.model.TopicsItem;

public class TopicsAdapter extends RecyclerView.Adapter<TopicsAdapter.TopicsViewHolder> {

    private Context context;
    private List<TopicsItem> topics;

    public TopicsAdapter(Context context, List<TopicsItem> topics) {
        this.context = context;
        this.topics = topics;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public TopicsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic, parent, false);

        return new TopicsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TopicsViewHolder holder, int position) {

        TopicsItem topic = topics.get(position);

        holder.topic_id.setText(topic.getTopicId());
        holder.topic_name.setText(topic.getTopicName());

        holder.topic_item.setOnClickListener(v -> {

                NavDirections action = TopicsFragmentDirections.actionTopicsFragmentToContentFragment(topic);
                Navigation.findNavController(v).navigate(action);

        });
    }

    @Override
    public int getItemCount() {
        return topics.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    class TopicsViewHolder extends RecyclerView.ViewHolder {
        TextView topic_name, topic_id;
        ConstraintLayout topic_item;


        public TopicsViewHolder(@NonNull View itemView) {
            super(itemView);
            topic_name = itemView.findViewById(R.id.topic_name);
            topic_id = itemView.findViewById(R.id.topic_id);
            topic_item = itemView.findViewById(R.id.topic_item);
        }
    }
}