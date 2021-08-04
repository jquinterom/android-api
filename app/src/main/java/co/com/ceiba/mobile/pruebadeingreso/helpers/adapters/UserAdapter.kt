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
import co.com.ceiba.mobile.pruebadeingreso.models.User
import java.lang.IllegalArgumentException

class RecyclerAdapter(val context: Context, val listUsers : List<User>, private val itemClickListener: OnUserClickListener)
    : RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnUserClickListener{
        fun onButtonClick()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return UsersViewHolder(LayoutInflater.from(context).inflate(R.layout.user_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is UsersViewHolder ->holder.bind(listUsers[position], position)
            else -> throw IllegalArgumentException("No ha pasado el view holder")
        }
    }

    override fun getItemCount(): Int = listUsers.size

    inner class UsersViewHolder(itemView: View) : BaseViewHolder<User>(itemView){
        override fun bind(item: User, position: Int) {
            val itemName : TextView = itemView.findViewById(R.id.name)
            val itemPhone : TextView = itemView.findViewById(R.id.phone)
            itemName.text = item.name
            itemPhone.text = item.phone

            //itemBtn.setOnClickListener()
        }
    }

    inner class PostsViewHolder(itemView: View) : BaseViewHolder<Post>(itemView){
        override fun bind(item: Post, position: Int) {
            val itemTitle : TextView = itemView.findViewById(R.id.title)
            val itemBody : TextView = itemView.findViewById(R.id.body)
        }

    }
}