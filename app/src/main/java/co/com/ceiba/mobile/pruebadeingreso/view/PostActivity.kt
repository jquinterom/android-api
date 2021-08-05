package co.com.ceiba.mobile.pruebadeingreso.view

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.com.ceiba.mobile.pruebadeingreso.R
import co.com.ceiba.mobile.pruebadeingreso.controllers.PostController
import co.com.ceiba.mobile.pruebadeingreso.helpers.MySingleton
import co.com.ceiba.mobile.pruebadeingreso.helpers.adapters.PostAdapter
import co.com.ceiba.mobile.pruebadeingreso.models.Post
import co.com.ceiba.mobile.pruebadeingreso.rest.Endpoints
import co.com.ceiba.mobile.pruebadeingreso.utilities.Utilities
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson

class PostActivity : AppCompatActivity() {
    lateinit var tvName: TextView
    lateinit var tvPhone: TextView
    lateinit var tvEmail: TextView
    lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        initElements()
        val userId = intent.getIntExtra("USER_ID", -1)
        if (userId == -1) {
            // no ha llegado un id correcto
            Utilities().longToast(this, getString(R.string.generic_error))?.show()
        } else {
            getPostByUserId(userId)
        }

    }

    // inicializar elmentos
    private fun initElements() {
        tvName = findViewById(R.id.name)
        tvPhone = findViewById(R.id.phone)
        tvEmail = findViewById(R.id.email)

        recyclerView = findViewById(R.id.recyclerViewPostsResults)
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    // obtener listado de post del usuario
    private fun getPostByUserId(userId: Int) {
        val dialog = Utilities().progressDialog(this)
        dialog.show()

        // validar si existen los post registrados en en base de datos, si existen mostrar
        val postsDB = PostController().getInstance(this)?.getPostByUserId(userId)
        if (postsDB?.size == 0) {
            // No existen datos, realizar peticion
            try {
                val url = Endpoints().URL_BASE + Endpoints().GET_POST_USER + "userid=" + userId
                val stringRequest = StringRequest(
                    Request.Method.GET, url,
                    { response ->
                        var registered = true
                        val gson = Gson()
                        val posts = gson.fromJson(response, Array<Post>::class.java)
                        for (post in posts) {
                            val pst = Post(post.userId, post.id, post.title, post.body)
                            if (registered) {
                                registered = PostController().getInstance(this)!!.registerPost(pst)
                            }

                        }

                        // cargando el recyclerview
                        recyclerView.adapter = PostAdapter(this, posts.toList())

                        // Validar si ha ocurrido un error
                        if (!registered) {
                            Utilities().longToast(this, getString(R.string.generic_error))!!.show()
                        }

                        dialog.dismiss()
                    },
                    { error ->
                        Log.e("error", error.toString())
                        dialog.dismiss()
                    })
                MySingleton.getInstance(this).addToRequestQueue(stringRequest)
            } catch (e: Exception) {
                e.printStackTrace()
                dialog.dismiss()
            }
        } else {
            // cargando el recyclerview
            recyclerView.adapter = PostAdapter(this, postsDB!!.toList())
            dialog.dismiss()
        }
    }
}