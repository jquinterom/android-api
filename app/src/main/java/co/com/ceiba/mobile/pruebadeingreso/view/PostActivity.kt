package co.com.ceiba.mobile.pruebadeingreso.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import co.com.ceiba.mobile.pruebadeingreso.R
import co.com.ceiba.mobile.pruebadeingreso.helpers.MySingleton
import co.com.ceiba.mobile.pruebadeingreso.models.User
import co.com.ceiba.mobile.pruebadeingreso.rest.Endpoints
import co.com.ceiba.mobile.pruebadeingreso.utilities.Utilities
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import java.lang.Exception

class PostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        getPostByUserId()

        Utilities().longToast(this, intent.getIntExtra("USER_ID", 0).toString())?.show()
    }

    // obtener listado de post del usuario
    private fun getPostByUserId(){
        val dialog = Utilities().progressDialog(this)
        dialog.show()

        // validar si existen los post registrados en en base de datos, si existen mostrar,
        // sino entonces realizar peticion

        try{
            val url = Endpoints().URL_BASE + Endpoints().GET_POST_USER + "userid=1"
            val stringRequest = StringRequest(
                    Request.Method.GET, url,
            { response ->
                val gson = Gson()
                val posts = gson.fromJson(response, Array<User>::class.java)

                Log.d("posts", posts.size.toString())
                // agregar usuarios a adaptador de lista
                dialog.dismiss()
            },
            { error ->
                Log.e("error", error.toString())
                dialog.dismiss()
            })
            MySingleton.getInstance(this).addToRequestQueue(stringRequest)
        } catch (e: Exception){
            e.printStackTrace()
            dialog.dismiss()
        }
    }
}