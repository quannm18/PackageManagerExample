package com.example.packagemanagerexample.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.packagemanagerexample.R
import com.example.packagemanagerexample.model.AppModel
import com.example.packagemanagerexample.viewmodel.IClickUninstallListener

class SimpleAdapter() : RecyclerView.Adapter<SimpleAdapter.SimpleViewHolder>() {
    private lateinit var mList : List<AppModel>
    private lateinit var iClickUninstallListener: IClickUninstallListener
    class SimpleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgAvtRow: ImageView by lazy { itemView.findViewById<ImageView>(R.id.imgAvtRow) }
        val tvNameRow: TextView by lazy { itemView.findViewById<TextView>(R.id.tvNameRow) }
        val btnDelete: Button by lazy { itemView.findViewById<Button>(R.id.btnDelete) }

        fun bind(mApp: AppModel){
            imgAvtRow.setImageDrawable(mApp.logo)
            tvNameRow.setText(mApp.name)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        return SimpleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false))
    }

    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
        holder.bind(mList[position])

        holder.btnDelete.setOnClickListener {
            Toast.makeText(it.context,"Delete", Toast.LENGTH_SHORT).show()
            iClickUninstallListener.delete(holder.position)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }
    fun initMyList( mListS : List<AppModel>, iClickUninstallListener: IClickUninstallListener){
        this.mList = mListS
        this.iClickUninstallListener= iClickUninstallListener
    }
}