package co.com.ceiba.mobile.pruebadeingreso.view

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.com.ceiba.mobile.pruebadeingreso.R
import co.com.ceiba.mobile.pruebadeingreso.controllers.PostController
import co.com.ceiba.mobile.pruebadeingreso.controllers.UserController
import co.com.ceiba.mobile.pruebadeingreso.utilities.Utilities

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
            loadUser(userId)
            PostController().getInstance(this)?.getPostByUserId(userId, this, recyclerView)
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

    // Mostrar informacion del usuario
    private fun loadUser(userId: Int) {
        try {
            val user = UserController().getInstance(this)?.getUserById(userId)
            tvName.text = user!!.name
            tvPhone.text = user.phone
            tvEmail.text = user.email
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }
}