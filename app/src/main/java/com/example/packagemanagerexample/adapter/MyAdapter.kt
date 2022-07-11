package com.example.packagemanagerexample.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.packagemanagerexample.R
import com.example.packagemanagerexample.model.AppModel

class MyAdapter : PagingDataAdapter<AppModel, MyAdapter.MyViewHolder>(DiffUtilCallback()) {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgAvtRow: ImageView by lazy { itemView.findViewById<ImageView>(R.id.imgAvtRow) }
        private val tvNameRow: TextView by lazy { itemView.findViewById<TextView>(R.id.tvNameRow) }
        private val btnDelete: Button by lazy { itemView.findViewById<Button>(R.id.btnDelete) }

        fun bind(mApp: AppModel){
            imgAvtRow.setImageDrawable(mApp.logo)
            tvNameRow.setText(mApp.name)

            btnDelete.setOnClickListener {
                Toast.makeText(it.context,"Delete",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row,parent,false))
    }

    class DiffUtilCallback : DiffUtil.ItemCallback<AppModel>() {
        override fun areItemsTheSame(oldItem: AppModel, newItem: AppModel): Boolean {
            return oldItem.packageName == newItem.packageName
        }

        override fun areContentsTheSame(oldItem: AppModel, newItem: AppModel): Boolean {
            return oldItem.packageName == newItem.packageName &&
                    oldItem.name == newItem.name
        }
    }
}