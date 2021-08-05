package co.com.ceiba.mobile.pruebadeingreso.helpers

import android.content.Context
import android.util.Log
import co.com.ceiba.mobile.pruebadeingreso.rest.Endpoints
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import junit.framework.TestCase

class MySingletonTest : TestCase() {

    public override fun setUp() {
        super.setUp()
    }
    private val context : Context? = null

    fun testGetImageLoader() {}

    fun testGetRequestQueue() {}

    // Test para verificar la conexión con el API, con lo cual se cerciora de que hay conexion via HTTP al API
    fun testAddToRequestQueue() {
        val url = Endpoints().URL_BASE + Endpoints().GET_USERS
        val stringRequest = StringRequest(Request.Method.GET, url,
            { response ->
                Log.d("Response", response.toString())
            },
            { error ->
                Log.d("Error petition", error.toString())
            })

        if (context != null) {
            MySingleton.getInstance(context).addToRequestQueue(stringRequest);
        }
    }
}