package co.com.ceiba.mobile.pruebadeingreso.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.widget.EditText
import co.com.ceiba.mobile.pruebadeingreso.R
import co.com.ceiba.mobile.pruebadeingreso.controllers.UserController
import co.com.ceiba.mobile.pruebadeingreso.helpers.MySingleton
import co.com.ceiba.mobile.pruebadeingreso.helpers.adapters.UserAdapter
import co.com.ceiba.mobile.pruebadeingreso.models.User
import co.com.ceiba.mobile.pruebadeingreso.rest.Endpoints
import co.com.ceiba.mobile.pruebadeingreso.utilities.Utilities
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import java.lang.Exception

class MainActivity : AppCompatActivity(), UserAdapter.OnUserClickListener {

    // region importaciones
    lateinit var recyclerView: RecyclerView
    private lateinit var editTextSearch: EditText
    // endregion
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initElements()

        // obteniendo lista de usuarios
        UserController().getInstance(this)?.getAllUsers(this, recyclerView)
    }

    // inicializar elmentos
    private fun  initElements(){
        editTextSearch = findViewById(R.id.editTextSearch)
        recyclerView = findViewById(R.id.recyclerViewSearchResults)
        recyclerView.layoutManager = LinearLayoutManager(this)
    }


    override fun onButtonClick(id: Int) {
        val intent = Intent(this, PostActivity::class.java).apply {
            putExtra("USER_ID", id)
        }
        startActivity(intent)
    }

    private fun filter(){

    }

}