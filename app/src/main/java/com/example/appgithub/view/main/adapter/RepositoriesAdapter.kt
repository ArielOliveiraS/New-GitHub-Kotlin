package com.example.appgithub.view.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appgithub.R
import com.example.appgithub.model.Item
import com.example.appgithub.view.main.interfaces.ClickViewContract
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_recycler_view.view.*

class RepositoriesAdapter (var repositoryList: MutableList<Item>, val listener: ClickViewContract) :
    RecyclerView.Adapter<RepositoriesAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycler_view, parent, false);
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return repositoryList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repository = repositoryList[position]
        holder.onBind(repository)

        holder.itemView.setOnClickListener {
            listener.onClick(repository)
        }
    }

    fun updateList(newList: MutableList<Item>) {
        if (repositoryList.isEmpty()){
            repositoryList = newList
        }else{
            repositoryList.addAll(newList)
        }
        notifyDataSetChanged();
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(item: Item) {
            itemView.repositoryNameView.text = item.name
            itemView.repositoryFullNameView.text = item.full_name
            itemView.forkNumberView.text = item.forks.toString()
            itemView.starsNumberView.text = item.stargazers_count.toString()
            itemView.userNameView.text = item.owner.login
            Picasso.get().load(item.owner.avatar_url).error(R.drawable.ic_avatar).into(itemView.userAvatarView)
        }
    }
}