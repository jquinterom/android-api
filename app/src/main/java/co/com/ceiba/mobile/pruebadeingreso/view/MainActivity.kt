package co.com.ceiba.mobile.pruebadeingreso.view

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.EditText
import co.com.ceiba.mobile.pruebadeingreso.R
import co.com.ceiba.mobile.pruebadeingreso.helpers.MySingleton
import co.com.ceiba.mobile.pruebadeingreso.models.User
import co.com.ceiba.mobile.pruebadeingreso.rest.Endpoints
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    // region importaciones
    lateinit var recyclerView: RecyclerView
    lateinit var editText: EditText
    // endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initElements()

        // obteniendo lista de usuarios
        getAllUsers(this)
    }

    // inicializar elmentos
    private fun  initElements(){
        recyclerView = findViewById(R.id.recyclerViewSearchResults)

        editText = findViewById(R.id.editTextSearch)
    }

    // obtener lista de usuarios
    private fun getAllUsers(context: Context) {
        try {
            val url = Endpoints.URL_BASE + Endpoints.GET_USERS
            val stringRequest = StringRequest(
                Request.Method.GET, url,
                { response ->
                    val gson = Gson()
                    val users = gson.fromJson(response, Array<User.UserInfo>::class.java)

                    // agregar usuarios a adaptador de lista
                },
                { error ->
                    Log.e("error", error.toString())
                })
            MySingleton.getInstance(context).addToRequestQueue(stringRequest)
        } catch (e : Exception){
            e.printStackTrace()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("bienvenido", "bienvenido")
    }

    override fun onResume() {
        super.onResume()
        // Log.d("despues de espera", "despues de espera")
    }

}