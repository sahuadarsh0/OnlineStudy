package com.techipinfotech.onlinestudy1.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.techipinfotech.onlinestudy1.R
import com.techipinfotech.onlinestudy1.databinding.ItemContentBinding
import com.techipinfotech.onlinestudy1.model.Demo

class DemoAdapter(private val onItemClicked: (Demo) -> Unit) : ListAdapter<Demo, DemoAdapter
.DemoViewHolder>(DIFFUTIL_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DemoViewHolder =
        DemoViewHolder(
            ItemContentBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )


    companion object {
        private val DIFFUTIL_CALLBACK = object : DiffUtil.ItemCallback<Demo>() {
            override fun areItemsTheSame(oldItem: Demo, newItem: Demo): Boolean =
                oldItem.demoId == newItem.demoId


            override fun areContentsTheSame(oldItem: Demo, newItem: Demo): Boolean =
                oldItem == newItem

        }
    }

    override fun onBindViewHolder(holder: DemoViewHolder, position: Int) =
        holder.bind(getItem(position), onItemClicked)

    inner class DemoViewHolder(private val binding: ItemContentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(demo: Demo, onItemClicked: (Demo) -> Unit) {
            binding.imageType.setImageResource(R.drawable.ic_video)
            binding.title.text = demo.videoTitle

            binding.root.setOnClickListener { onItemClicked(demo) }
        }
    }
}