package co.com.ceiba.mobile.pruebadeingreso.controllers

import android.content.Context
import co.com.ceiba.mobile.pruebadeingreso.database.dbManager
import co.com.ceiba.mobile.pruebadeingreso.models.User

class UserController {
    constructor()

    constructor(context: Context){
        ctx = context
        manager = dbManager(context)
    }

    private var manager: dbManager? = null
    private var mInstance: UserController? = null
    private var ctx: Context? = null


    @Synchronized
    fun getInstance(context: Context?): UserController? {
        if (mInstance == null) {
         mInstance = context?.let { UserController(it) }
        }
        return mInstance
    }

    // Registrar usuario
    fun registerUser(user: User?): Boolean {
        return manager!!.registerUser(user!!)
    }

    // obtener usuarios
    fun getAllUsers() : Array<User> {
        return manager!!.getUsers()
    }

    // obtener usuario por id
    fun getUserById(userId: Int) : User{
        return manager!!.getUserById(userId)
    }

}