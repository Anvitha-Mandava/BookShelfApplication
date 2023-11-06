package com.example.bookshelf.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bookshelf.data.entities.AnnotationEntity
import com.example.myapplication.databinding.ItemAnnotationBinding

class AnnotationsAdapter :
    ListAdapter<AnnotationEntity, AnnotationsAdapter.AnnotationViewHolder>(AnnotationDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnnotationViewHolder {
        val binding =
            ItemAnnotationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnnotationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AnnotationViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class AnnotationViewHolder(val binding: ItemAnnotationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(annotation: AnnotationEntity) {
            binding.textAnnotationContent.text = annotation.content
        }
    }

    class AnnotationDiffCallback : DiffUtil.ItemCallback<AnnotationEntity>() {
        override fun areItemsTheSame(
            oldItem: AnnotationEntity, newItem: AnnotationEntity
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: AnnotationEntity, newItem: AnnotationEntity
        ): Boolean {
            return oldItem == newItem
        }
    }
}
