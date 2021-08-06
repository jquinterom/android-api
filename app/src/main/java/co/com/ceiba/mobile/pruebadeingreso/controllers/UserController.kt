package co.com.ceiba.mobile.pruebadeingreso.controllers

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import co.com.ceiba.mobile.pruebadeingreso.R
import co.com.ceiba.mobile.pruebadeingreso.database.dbManager
import co.com.ceiba.mobile.pruebadeingreso.helpers.MySingleton
import co.com.ceiba.mobile.pruebadeingreso.helpers.adapters.UserAdapter
import co.com.ceiba.mobile.pruebadeingreso.models.User
import co.com.ceiba.mobile.pruebadeingreso.rest.Endpoints
import co.com.ceiba.mobile.pruebadeingreso.utilities.Utilities
import co.com.ceiba.mobile.pruebadeingreso.view.MainActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson

class UserController {
    constructor()

    constructor(context: Context) {
        ctx = context
        manager = dbManager(context)
    }

    private var manager: dbManager? = null
    private var mInstance: UserController? = null
    private var ctx: Context? = null
    private var users: Array<User> = emptyArray()


    @Synchronized
    fun getInstance(context: Context?): UserController? {
        if (mInstance == null) {
            mInstance = context?.let { UserController(it) }
        }
        return mInstance
    }

    // Registrar usuario
    private fun registerUser(user: User?): Boolean {
        return manager!!.registerUser(user!!)
    }

    // obtener usuarios
    fun getAllUsersDB(): Array<User> {
        return manager!!.getUsers()
    }

    // obtener usuario por id
    fun getUserById(userId: Int): User {
        return manager!!.getUserById(userId)
    }


    // obtener lista de usuarios
    fun getAllUsers(main: MainActivity, recyclerView: RecyclerView) {
        val dialog = Utilities().progressDialog(main)
        dialog.show()

        // Validar si existen los usuarios en la base de datos, sino, hacer la peticion y registrarlos
        val usersDB = getAllUsersDB()
        if (usersDB.isEmpty()) { // Noe existe informacion
            // No existe informacion
            try {
                val url = Endpoints().URL_BASE + Endpoints().GET_USERS
                val stringRequest = StringRequest(
                    Request.Method.GET, url,
                    { response ->
                        val gson = Gson()
                        users = gson.fromJson(response, Array<User>::class.java)
                        var registered = true

                        for (user in users) {
                            val usr = User(user.id, user.name, user.email, user.phone, user.website)
                            // registrando usuario
                            if (registered) {
                                registered = UserController().getInstance(main)!!.registerUser(usr)
                            }
                        }

                        // cargando el recyclerview
                        recyclerView.adapter = UserAdapter(
                            main,
                            users.toList() as ArrayList<User>, main
                        )

                        // Validar si ha ocurrido un error
                        if (!registered) {
                            Utilities().longToast(main, main.getString(R.string.generic_error))!!
                                .show()
                        }

                        dialog.dismiss()
                    },
                    { error ->
                        error.printStackTrace()
                        dialog.dismiss()
                        Utilities().longToast(main, main.getString(R.string.generic_error))!!.show()
                    })
                MySingleton.getInstance(main).addToRequestQueue(stringRequest)
            } catch (e: Exception) {
                e.printStackTrace()
                dialog.dismiss()
                Utilities().longToast(main, main.getString(R.string.generic_error))!!.show()
            }
        } else {
            // Carga local de usuarios
            recyclerView.adapter = UserAdapter(main, usersDB.toList() as ArrayList<User>, main)
            dialog.dismiss()
        }
    }

    // Filtro para los usuarios
    @SuppressLint("InflateParams")
    fun filter(
        users: Array<User>,
        main: MainActivity,
        recyclerView: RecyclerView,
        ch: CharSequence?
    ) {
        if (ch.toString().isNotEmpty()) {
            val newUsers: ArrayList<User> = arrayListOf()
            users.forEach {
                if (it.name.lowercase().contains(ch.toString().lowercase())) {
                    newUsers.add(it)
                }
            }
            recyclerView.adapter = UserAdapter(main, newUsers, main)
        } else {
            recyclerView.adapter = UserAdapter(main, users.toList() as ArrayList<User>, main)
        }
    }
}