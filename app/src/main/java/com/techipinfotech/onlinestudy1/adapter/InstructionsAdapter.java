package com.techipinfotech.onlinestudy1.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.techipinfotech.onlinestudy1.R;
import com.techipinfotech.onlinestudy1.model.ExamInstructionsItem;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class InstructionsAdapter extends RecyclerView.Adapter<InstructionsAdapter.InstructionsViewHolder> {

    List<ExamInstructionsItem> examInstructionsItem;

    public InstructionsAdapter(@Nullable List<ExamInstructionsItem> examInstructionsItem) {

        this.examInstructionsItem = examInstructionsItem;
        setHasStableIds(true);
    }


    @NonNull
    @Override
    public InstructionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_instruction, parent, false);
        return new InstructionsViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull InstructionsViewHolder holder, int position) {
        ExamInstructionsItem item = examInstructionsItem.get(position);
        holder.instruction.setText(item.getExamInstruction());
        holder.instruction_no.setText("#" + (position+1));

        holder.instruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return examInstructionsItem.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    class InstructionsViewHolder extends RecyclerView.ViewHolder {
        TextView instruction, instruction_no;

        public InstructionsViewHolder(@NonNull View itemView) {
            super(itemView);
            instruction = itemView.findViewById(R.id.instruction);
            instruction_no = itemView.findViewById(R.id.instruction_no);
        }
    }
}
