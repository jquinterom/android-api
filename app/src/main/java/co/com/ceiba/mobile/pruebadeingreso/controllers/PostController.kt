package co.com.ceiba.mobile.pruebadeingreso.controllers

import android.content.Context
import co.com.ceiba.mobile.pruebadeingreso.database.dbManager
import co.com.ceiba.mobile.pruebadeingreso.models.Post

class PostController {
    constructor()

    constructor(context: Context){
        ctx = context
        manager = dbManager(context)
    }

    private var manager: dbManager? = null
    private var mInstance: PostController? = null
    private var ctx: Context? = null


    @Synchronized
    fun getInstance(context: Context?): PostController? {
        if (mInstance == null) {
            mInstance = context?.let { PostController(it) }
        }
        return mInstance
    }

    // Registrar post
    fun registerPost(post: Post?): Boolean {
        return manager!!.registerPost(post!!)
    }

    // obtener posts
    fun getPostByUserId(userId : Int) : Array<Post> {
        return manager!!.getPosts(userId)
    }
}