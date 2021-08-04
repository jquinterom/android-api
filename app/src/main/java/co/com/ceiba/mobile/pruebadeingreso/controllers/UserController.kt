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

    // Registrar comentario
    fun registerUser(user: User.UserInfo?): Boolean {
        return manager!!.registerUser(user!!)
    }

    // obtener usuarios
    fun getAllUsers() : Array<User.UserInfo> {
        return manager!!.getUsers()
    }

}