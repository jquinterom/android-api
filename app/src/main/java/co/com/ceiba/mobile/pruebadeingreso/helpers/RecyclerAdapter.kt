package co.com.ceiba.mobile.pruebadeingreso.helpers

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import co.com.ceiba.mobile.pruebadeingreso.R
import co.com.ceiba.mobile.pruebadeingreso.models.User
import java.lang.IllegalArgumentException


class RecyclerAdapter(val context: Context, val listUsers : List<User>) : RecyclerView.Adapter<BaseViewHolder<*>>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return UsersViewHolder(LayoutInflater.from(context).inflate(R.layout.user_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is UsersViewHolder->holder.bind(listUsers[position], position)
            else -> throw IllegalArgumentException("No ha pasado el view holder")
        }
    }

    override fun getItemCount(): Int = listUsers.size

    inner class UsersViewHolder(itemView: View) : BaseViewHolder<User>(itemView){
        override fun bind(item: User, position: Int) {
            val itemName : TextView = itemView.findViewById(R.id.name)
            itemName.text = item.name
        }
    }
}