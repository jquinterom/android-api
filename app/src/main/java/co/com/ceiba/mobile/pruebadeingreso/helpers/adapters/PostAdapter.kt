package co.com.ceiba.mobile.pruebadeingreso.helpers.adapters

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import co.com.ceiba.mobile.pruebadeingreso.R
import co.com.ceiba.mobile.pruebadeingreso.helpers.BaseViewHolder
import co.com.ceiba.mobile.pruebadeingreso.models.Post
import java.lang.IllegalArgumentException

class PostAdapter(val context: Context, val listPosts : List<Post>) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return PostsViewHolder(LayoutInflater.from(context).inflate(R.layout.post_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is PostsViewHolder ->holder.bind(listPosts[position], position)
            else -> throw IllegalArgumentException("No ha pasado el view holder")
        }
    }

    override fun getItemCount(): Int = listPosts.size


    inner class PostsViewHolder(itemView: View) : BaseViewHolder<Post>(itemView){
        override fun bind(item: Post, position: Int) {
            val itemTitle : TextView = itemView.findViewById(R.id.title)
            val itemBody : TextView = itemView.findViewById(R.id.body)
        }

    }
}