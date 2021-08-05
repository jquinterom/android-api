package co.com.ceiba.mobile.pruebadeingreso.helpers.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.com.ceiba.mobile.pruebadeingreso.R
import co.com.ceiba.mobile.pruebadeingreso.helpers.BaseViewHolder
import co.com.ceiba.mobile.pruebadeingreso.models.User

class UserAdapter(
    val context: Context,
    private var userList: ArrayList<User>,
    private val itemClickListener: OnUserClickListener
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var userFilterList: ArrayList<User> = ArrayList()

    init {
        userFilterList = userList
    }

    interface OnUserClickListener {
        fun onButtonClick(id: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return UsersViewHolder(
            LayoutInflater.from(context).inflate(R.layout.user_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is UsersViewHolder -> holder.bind(userList[position], position)
            else -> throw IllegalArgumentException("No ha pasado el view holder")
        }
    }

    override fun getItemCount(): Int = userList.size

    inner class UsersViewHolder(itemView: View) : BaseViewHolder<User>(itemView) {
        override fun bind(item: User, position: Int) {
            val itemName: TextView = itemView.findViewById(R.id.name)
            val itemPhone: TextView = itemView.findViewById(R.id.phone)
            val itemEmail: TextView = itemView.findViewById(R.id.email)
            val itemButton: Button = itemView.findViewById(R.id.btn_view_post)
            itemName.text = item.name
            itemPhone.text = item.phone
            itemEmail.text = item.email
            itemButton.setOnClickListener { itemClickListener.onButtonClick(item.id) }
        }
    }
}