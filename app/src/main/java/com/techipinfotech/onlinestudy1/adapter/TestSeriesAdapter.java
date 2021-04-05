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
import com.techipinfotech.onlinestudy1.model.TestSeriesItem;
import com.techipinfotech.onlinestudy1.test.TestHomeFragmentDirections;
import com.techipinfotech.onlinestudy1.test.TestResultFragmentDirections;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TestSeriesAdapter extends RecyclerView.Adapter<TestSeriesAdapter.TestSeriesViewHolder> {

    List<TestSeriesItem> testSeriesItems;
    Context context;
    int switcher;

    public TestSeriesAdapter(@Nullable Context context, @Nullable List<TestSeriesItem> testSeriesItems, int switcher) {

        this.testSeriesItems = testSeriesItems;
        this.context = context;
        this.switcher = switcher;
        setHasStableIds(true);
    }


    @NonNull
    @Override
    public TestSeriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_test_series, parent, false);
        return new TestSeriesViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull TestSeriesViewHolder holder, int position) {
        TestSeriesItem item = testSeriesItems.get(position);
        holder.test_name.setText(item.getTestName());

        holder.test_series_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action = null;
                if (switcher == 1)
                    action = TestHomeFragmentDirections.actionNavTestToTestSeriesFragment(item);
                else if (switcher == 2)
                    action = TestResultFragmentDirections.actionNavResultToTestSeriesFragment(item);

                Navigation.findNavController(v).navigate(action);

            }
        });
        String url = API.TEST.toString() + item.getTestImage();
        Glide
                .with(context)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.ic_triangle)
                .into(holder.test_image);
    }

    @Override
    public int getItemCount() {
        return testSeriesItems.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    class TestSeriesViewHolder extends RecyclerView.ViewHolder {
        TextView test_name;
        ImageView test_image;
        CardView test_series_item;

        public TestSeriesViewHolder(@NonNull View itemView) {
            super(itemView);
            test_name = itemView.findViewById(R.id.test_name);
            test_image = itemView.findViewById(R.id.test_image);
            test_series_item = itemView.findViewById(R.id.test_series_item);
        }
    }
}
