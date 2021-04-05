package com.techipinfotech.onlinestudy1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.techipinfotech.onlinestudy1.ui.ContentFragmentDirections;
import com.techipinfotech.onlinestudy1.R;
import com.techipinfotech.onlinestudy1.model.ContentItem;

public class ContentsAdapter extends RecyclerView.Adapter<ContentsAdapter.ContentsViewHolder> {
    Context context;
    List<ContentItem> contents;

    public ContentsAdapter(Context context, List<ContentItem> contents) {
        this.context = context;
        this.contents = contents;
    }

    @NonNull
    @Override
    public ContentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_content, parent, false);
        return new ContentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContentsViewHolder holder, int position) {
        ContentItem content = contents.get(position);

        holder.title.setText(content.getTitle());
        holder.material_id.setText(content.getMaterialId());
        NavDirections action1, action2;
        action1 = ContentFragmentDirections.actionContentFragmentToPlay(content.getUrl(), content.getMaterialId());

        action2 = ContentFragmentDirections.actionContentFragmentToWebViewActivity(content.getUrl(),content.getMaterialId());

        holder.content_item.setOnClickListener(v -> {
            if (content.getType().equals("V")) {
                Navigation.findNavController(v).navigate(action1);
            } else if (content.getType().equals("P")) {
                Navigation.findNavController(v).navigate(action2);
            }
        });

        if (content.getType().equals("V")) {
            holder.image_type.setImageResource(R.drawable.ic_video);
        } else if (content.getType().equals("P")) {
            holder.image_type.setImageResource(R.drawable.ic_pdf);
        }


    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    class ContentsViewHolder extends RecyclerView.ViewHolder {
        ImageView image_type;
        TextView title;
        TextView material_id;
        ConstraintLayout content_item;

        public ContentsViewHolder(@NonNull View itemView) {
            super(itemView);
            image_type = itemView.findViewById(R.id.image_type);
            title = itemView.findViewById(R.id.title);
            material_id = itemView.findViewById(R.id.material_id);
            content_item = itemView.findViewById(R.id.content_item);
        }
    }
}
