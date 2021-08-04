package co.com.ceiba.mobile.pruebadeingreso.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import co.com.ceiba.mobile.pruebadeingreso.utilities.Utilities
import java.lang.Exception

class PostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    // obtener listado de post del usuario
    private fun getPostByUserId(){
        val dialog = Utilities().progressDialog(this)
        dialog.show()

        // validar si existen los post registrados en en base de datos, si existen mostrar,
        // sino entonces realizar peticion

        try{


        } catch (e: Exception){
            e.printStackTrace()
            dialog.dismiss()
        }
    }
}