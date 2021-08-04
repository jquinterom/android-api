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
    lateinit var editText: EditText
    // endregion
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initElements()

        // obteniendo lista de usuarios
        getAllUsers()
    }

    // inicializar elmentos
    private fun  initElements(){
        editText = findViewById(R.id.editTextSearch)
        recyclerView = findViewById(R.id.recyclerViewSearchResults)
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    // obtener lista de usuarios
    private fun getAllUsers() {
        val dialog = Utilities().progressDialog(this)
        dialog.show()

        // Validar si existen los usuarios en la base de datos, sino, hacer la peticion y registrarlos
        val usersDB = UserController().getInstance(this)?.getAllUsers()
        if(usersDB?.size == 0 ){ // Noe existe informacion
            // No existe informacion
            try {
                val url = Endpoints().URL_BASE + Endpoints().GET_USERS
                val stringRequest = StringRequest(
                    Request.Method.GET, url,
                    { response ->
                        val gson = Gson()
                        val users = gson.fromJson(response, Array<User>::class.java)
                        var registered = true


                        // Validar el registro de usuario ya que no funciona

                        for(user in users){
                            val usr = User(user.id, user.name, user.email, user.phone, user.website)
                            // registrando usuario
                            if(registered) {
                                registered = UserController().getInstance(this)!!.registerUser(usr)
                            }
                        }

                        // cargando el recyclerview
                        recyclerView.adapter = UserAdapter(this, users.toList(), this)

                        // Validar si ha ocurrido un error
                        if(!registered){
                            Utilities().longToast(this, getString(R.string.generic_error))!!.show()
                        }

                        // agregar usuarios a adaptador de lista
                        dialog.dismiss()
                    },
                    { error ->
                        Log.e("error", error.toString())
                        dialog.dismiss()
                    })
                MySingleton.getInstance(this).addToRequestQueue(stringRequest)
            } catch (e : Exception){
                e.printStackTrace()
                dialog.dismiss()
            }
        } else {
            // cargando el recyclerview *************+++
            // recyclerView.adapter = usersDB?.let { UserAdapter(this, it.toList()) }
            dialog.dismiss()
        }
    }

    override fun onButtonClick(id: Int) {
        val intent = Intent(this, PostActivity::class.java).apply {
            putExtra("USER_ID", id)
        }
        startActivity(intent)
    }

}