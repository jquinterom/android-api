package co.com.ceiba.mobile.pruebadeingreso.controllers

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import co.com.ceiba.mobile.pruebadeingreso.R
import co.com.ceiba.mobile.pruebadeingreso.database.dbManager
import co.com.ceiba.mobile.pruebadeingreso.helpers.MySingleton
import co.com.ceiba.mobile.pruebadeingreso.helpers.adapters.PostAdapter
import co.com.ceiba.mobile.pruebadeingreso.models.Post
import co.com.ceiba.mobile.pruebadeingreso.rest.Endpoints
import co.com.ceiba.mobile.pruebadeingreso.utilities.Utilities
import co.com.ceiba.mobile.pruebadeingreso.view.PostActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson

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
    private fun getPostByUserIdDB(userId : Int) : Array<Post> {
        return manager!!.getPosts(userId)
    }

    // obtener listado de post del usuario
    fun getPostByUserId(userId: Int, main : PostActivity, recyclerView : RecyclerView) {
        val dialog = Utilities().progressDialog(main)
        dialog.show()

        // validar si existen los post registrados en en base de datos, si existen mostrar
        val postsDB = getPostByUserIdDB(userId)
        if (postsDB.isEmpty()) {
            // No existen datos, realizar peticion
            try {
                val url = Endpoints().URL_BASE + Endpoints().GET_POST_USER + "userId=" + userId
                val stringRequest = StringRequest(
                    Request.Method.GET, url,
                    { response ->
                        var registered = true
                        val gson = Gson()
                        val posts = gson.fromJson(response, Array<Post>::class.java)
                        for (post in posts) {
                            val pst = Post(post.userId, post.id, post.title, post.body)
                            if (registered) {
                                registered = PostController().getInstance(main)!!.registerPost(pst)
                            }
                        }

                        // cargando el recyclerview
                        recyclerView.adapter = PostAdapter(main, posts.toList())

                        // Validar si ha ocurrido un error
                        if (!registered) {
                            Utilities().longToast(main, main.getString(R.string.generic_error))!!.show()
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
            // cargando el recyclerview
            recyclerView.adapter = PostAdapter(main, postsDB.toList())
            dialog.dismiss()
        }
    }

}