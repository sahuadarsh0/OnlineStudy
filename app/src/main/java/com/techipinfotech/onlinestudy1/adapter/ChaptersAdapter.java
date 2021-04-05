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

import org.jetbrains.annotations.Nullable;

import java.util.List;


import com.techipinfotech.onlinestudy1.ui.ChaptersFragmentDirections;
import com.techipinfotech.onlinestudy1.R;
import com.techipinfotech.onlinestudy1.model.ChaptersItem;

public class ChaptersAdapter extends RecyclerView.Adapter<ChaptersAdapter.ChaptersViewHolder> {
    Context context;
    List<ChaptersItem> chapters;

    public ChaptersAdapter(@Nullable Context context, @Nullable List<ChaptersItem> chapters) {

        this.context = context;
        this.chapters = chapters;
        setHasStableIds(true);
    }


    @NonNull
    @Override
    public ChaptersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chapter, parent, false);
        return new ChaptersViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ChaptersViewHolder holder, int position) {
        ChaptersItem chapter = chapters.get(position);
        holder.chapter_name.setText(chapter.getChapterName());
        holder.chapter_id.setText(chapter.getChapterId());

        holder.chapter_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action = ChaptersFragmentDirections.actionChaptersFragmentToTopicsFragment(chapter);
                Navigation.findNavController(v).navigate(action);


            }
        });

    }

    @Override
    public int getItemCount() {
        return chapters.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    class ChaptersViewHolder extends RecyclerView.ViewHolder {
        TextView chapter_name;
        TextView chapter_id;
        ConstraintLayout chapter_item;

        public ChaptersViewHolder(@NonNull View itemView) {
            super(itemView);
            chapter_name = itemView.findViewById(R.id.chapter_name);
            chapter_id = itemView.findViewById(R.id.chapter_id);
            chapter_item = itemView.findViewById(R.id.chapter_item);
        }
    }
}
