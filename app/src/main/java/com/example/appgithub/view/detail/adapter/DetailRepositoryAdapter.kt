package com.example.appgithub.view.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appgithub.R
import com.example.appgithub.model.Repository
import kotlinx.android.synthetic.main.item_detail.view.*

class DetailRepositoryAdapter(var repositoryList: MutableList<Repository>) :
    RecyclerView.Adapter<DetailRepositoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_detail, parent, false);
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return repositoryList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repository = repositoryList[position]
        holder.onBind(repository)
    }

    fun updateList(newList: MutableList<Repository>) {
        if (repositoryList.isEmpty()) {
            repositoryList = newList
        } else {
            repositoryList.addAll(newList)
        }
        notifyDataSetChanged();
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(item: Repository) {
            itemView.repoNameView.text = item.name
        }
    }
}